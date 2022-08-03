package cz.menu.service;

import cz.menu.dto.MenuDto;
import cz.menu.entity.Menu;
import cz.repository.MenuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;

    @Override
    public Page<MenuDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Menu> menus = menuRepository.findAll(pageable);
        List<MenuDto> menusDto = new ArrayList<>();
        menus.forEach(menu ->
                menusDto.add(new MenuDto(menu)));

        return new PageImpl<>(menusDto, pageable, menus.getTotalElements());
    }

    @Override
    public void mealPlan(Model model, Long menuId) {
        Menu menuEntity = menuRepository.getReferenceById(menuId);

    }

    private Menu menuEntityMapper(MenuDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Menu.class);
    }
}
