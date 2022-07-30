package cz.menu.service;

import cz.menu.dto.BMRDto;
import cz.menu.dto.MenuDto;
import cz.menu.entity.BMR;
import cz.menu.entity.Menu;
import org.springframework.ui.Model;

public interface IMenuService {

    Menu saveMenuForm(Model model, MenuDto menuDto);

    void saveBmr(Model model, BMRDto bmrDto, Long menuId);

    void calculateBMR(Model model, Long menuId) throws Exception;


}
