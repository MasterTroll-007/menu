package cz.menu.service;

import cz.menu.dto.MenuDto;
import cz.menu.entity.BMR;
import cz.menu.entity.Client;
import cz.menu.entity.Menu;
import cz.menu.model.Sex;
import cz.menu.repository.ClientRepository;
import cz.menu.repository.MenuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;
    private final ClientRepository clientRepository;

    public Menu saveMenuForm(Model model, MenuDto menuDto) {
        Menu menuEntity = menuEntityMapper(menuDto);
        Menu savedEntity = menuRepository.save(menuEntity);
        model.addAttribute("model", model);
        model.addAttribute("menu", menuDto);

        log.info("Ulozene data: {}", savedEntity);

        return savedEntity;
    }

    public void calculateBMR(Model model, Long menuId) throws Exception {
        Optional<Menu> menu = menuRepository.findById(menuId);
        Optional<Client> client = clientRepository.findById(menu.get().getClient().getId());
        if (client.isEmpty()) {
            throw new Exception();
        }

        BMR bmr = getBmr(menu.get(), client.get());
        MenuDto menuDto = menuDtoMapper(menu.get());


        model.addAttribute("model", model);
        model.addAttribute("menu", menuDto);
        model.addAttribute("bmr", bmr);
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
        bmr.setKJ(kj);
        double proteins = bmr.getKJ() / 17 * 0.25;
        double carbs = bmr.getKJ() / 17 * 0.45;
        double fats = bmr.getKJ() / 38 * 0.30;
        // P/C/F = 25/45/30
        bmr.setFats(fats);
        bmr.setCarbohydrates(carbs);
        bmr.setProteins(proteins);
        return bmr;
    }

    private Menu menuEntityMapper(MenuDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Menu.class);
    }
    private MenuDto menuDtoMapper(Menu entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, MenuDto.class);
    }

}
