package cz.menu.service;

import cz.menu.entity.Menu;
import cz.menu.repository.MenusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService implements IMenusService {

    private final MenusRepository menusRepository;

    public void saveMenuForm(Menu menus) {
        log.info("Ulozene data: {}", menus.toString());
        Menu savedEntity = menusRepository.save(menus);
        log.info(savedEntity.toString());
    }

}
