package cz.menu.service;

import cz.menu.entity.Menus;
import cz.menu.repository.MenusRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenusService implements IMenusService {

    private final MenusRepository menusRepository;

    public void saveMenusForm(Menus menus) {
        log.info("Ulozene data: {}", menus.toString());
        Menus savedEntity = menusRepository.save(menus);
        log.info(savedEntity.toString());
    }

}
