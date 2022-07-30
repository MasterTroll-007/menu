package cz.menu.control;

import cz.menu.dto.BMRDto;
import cz.menu.dto.MenuDto;
import cz.menu.entity.BMR;
import cz.menu.entity.Menu;
import cz.menu.service.IMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {

    private final IMenuService menuService;

    private static final String MENU_FORM = "menu/form";
    private static final String BMR_FORM = "menu/bmrForm";
    private static final String REDIRECT_BMR_FORM = "redirect:/menu/";
    private static final String REDIRECT_MEAL_PLAN = "redirect:/menu/mealPlan/";

    @GetMapping
    public String newMenuForm(Model model, @ModelAttribute MenuDto menuDto) {
        model.addAttribute("model", model);
        model.addAttribute("menu", menuDto);
        return MENU_FORM;
    }

    @PostMapping
    public String saveMenuForm(Model model, @ModelAttribute MenuDto menuDto) {
        Menu menu = menuService.saveMenuForm(model, menuDto);
        return REDIRECT_BMR_FORM + menu.getId();
    }

    @GetMapping(value = "/{menuId}")
    public String bmrForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute MenuDto menuDto) throws Exception {
        menuService.calculateBMR(model, menuId);
        return BMR_FORM;
    }

    @PostMapping(value = "/{menuId}")
    public String saveBmrForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute BMRDto bmrDto) {
        menuService.saveBmr(model, bmrDto, menuId);
        return REDIRECT_MEAL_PLAN  + menuId;
    }


}
