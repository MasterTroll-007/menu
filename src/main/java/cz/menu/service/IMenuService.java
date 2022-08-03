package cz.menu.service;

import cz.menu.dto.MenuDto;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface IMenuService {

    Page<MenuDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir);

    void mealPlan(Model model, Long menuId);

}
