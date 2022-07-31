package cz.menu.service;

import cz.menu.dto.BMRDto;
import cz.menu.dto.MenuDto;
import cz.menu.entity.BMR;
import cz.client.entity.Client;
import cz.menu.entity.Menu;
import cz.menu.exception.MenuException;
import cz.menu.model.Sex;
import cz.repository.BmrRepository;
import cz.repository.MenuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public Menu saveMenuForm(Model model, MenuDto menuDto) {
        Menu menuEntity = menuEntityMapper(menuDto);
        Menu savedEntity = menuRepository.save(menuEntity);
        model.addAttribute("model", model);
        model.addAttribute("menu", menuDto);

        log.info("Ulozene data: {}", savedEntity);

        return savedEntity;
    }

    @Override
    @Transactional
    public void saveBmr(Model model, BMRDto bmrDto, Long menuId) throws MenuException {
        Menu menuEntity = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuException("Jídelníček nenalezen."));

        BMR bmrEntity = bmrEntityMapper(bmrDto);

        if (menuEntity.getClient().getBmr() == null) {
            menuEntity.getClient().setBmr(new BMR());
        } else {
            bmrEntity.setId(menuEntity.getClient().getBmr().getId());
        }

        menuEntity.getClient().getBmr().setProteins(bmrEntity.getProteins());
        menuEntity.getClient().getBmr().setCarbohydrates(bmrEntity.getCarbohydrates());
        menuEntity.getClient().getBmr().setFats(bmrEntity.getFats());
        menuEntity.getClient().getBmr().setFibres(bmrEntity.getFibres());
        menuEntity.getClient().getBmr().setKJ(bmrEntity.getKJ());

        Menu save = menuRepository.save(menuEntity);
        log.info(save.getClient().getBmr().toString());
    }

    @Override
    public void calculateBMR(Model model, Long menuId) throws MenuException {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new MenuException("Jídelníček nenalezen."));

        Client client = menu.getClient();
        BMR bmr = getBmr(menu, client);

        model.addAttribute("model", model);
        model.addAttribute("sex", client.getSex());
        model.addAttribute("fullname", client.getFullName());
        model.addAttribute("bmr", bmr);
    }

    @Override
    public void mealPlan(Model model, Long menuId) {
        Menu menuEntity = menuRepository.getReferenceById(menuId);

    }

    private BMR getBmr(Menu menu, Client client) {
        BMR bmr = new BMR();
        final int age = client.getAge();
        final int height = client.getHeight();
        final int weight = client.getWeight();
        double kj;

        if(Sex.FEMALE.equals(menu.getClient().getSex())) {
            kj = ((10 * weight) + (6.25 * height) - (5 * age) - 161) * 4.184;
        } else {
            kj = ((10 * weight) + (6.25 * height) - (5 * age) + 5) * 4.184;
        }
        bmr.setKJ(Precision.round(kj,1));
        // P/C/F = 25%/45%/30%
        double proteins = Precision.round(bmr.getKJ() / 17 * 0.25, 1);
        double carbs = Precision.round(bmr.getKJ() / 17 * 0.45, 1);
        double fats = Precision.round(bmr.getKJ() / 38 * 0.30, 1);
        bmr.setFats(fats);
        bmr.setCarbohydrates(carbs);
        bmr.setProteins(proteins);
        return bmr;
    }

    private Menu menuEntityMapper(MenuDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Menu.class);
    }

    private BMR bmrEntityMapper(BMRDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, BMR.class);
    }

    private MenuDto menuDtoMapper(Menu entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, MenuDto.class);
    }

}
