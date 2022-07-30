package cz.menu.service;

import cz.menu.dto.MenuDto;
import cz.menu.entity.Menu;
import cz.menu.repository.MenusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements IMenusService {

    private final MenusRepository menusRepository;

    public void saveMenuForm(MenuDto menuDto) {
        Menu menuEntity = menuMapper(menuDto);
        Menu savedEntity = menusRepository.save(menuEntity);
        log.info("Ulozene data: {}", savedEntity);
    }

    private Menu menuMapper (MenuDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Menu.class);
    }

}
