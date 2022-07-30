package cz.menu.service;

import cz.menu.dto.MenuDto;
import cz.menu.entity.Menu;
import org.springframework.ui.Model;

public interface IMenuService {

    Menu saveMenuForm(Model model, MenuDto menuDto);

    void calculateBMR(Model model, Long menuId) throws Exception;


}
