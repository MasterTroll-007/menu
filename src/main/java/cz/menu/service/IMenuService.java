package cz.menu.service;

import cz.menu.dto.BMRDto;
import cz.menu.dto.MenuDto;
import cz.menu.entity.Menu;
import cz.menu.exception.MenuException;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface IMenuService {

    Menu saveMenuForm(Model model, MenuDto menuDto);

    void saveBmr(Model model, BMRDto bmrDto, Long menuId) throws MenuException;
    Page<MenuDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir);
    void calculateBMR(Model model, Long menuId) throws MenuException;

    void mealPlan(Model model, Long menuId);

}
