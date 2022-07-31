package cz.menu.control;

import cz.menu.dto.BMRDto;
import cz.menu.dto.MenuDto;
import cz.menu.exception.MenuException;
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
    private static final String MEAL_PLAN = "menu/mealPlan";
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
        Long menuId = menuService.saveMenuForm(model, menuDto).getId();
        return REDIRECT_BMR_FORM + menuId;
    }

    @GetMapping(value = "/{menuId}")
    public String bmrForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute MenuDto menuDto)
            throws MenuException {
        menuService.calculateBMR(model, menuId);
        return BMR_FORM;
    }

    @PostMapping(value = "/{menuId}")
    public String saveBmrForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute BMRDto bmrDto)
            throws MenuException {
        menuService.saveBmr(model, bmrDto, menuId);
        return REDIRECT_MEAL_PLAN + menuId;
    }

    @GetMapping(value = "/mealPlan/{menuId}")
    public String mealPlanForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute MenuDto menuDto) {
        return MEAL_PLAN;
    }

    @PostMapping(value = "/mealPlan/{menuId}")
    public String saveMealPlan(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute MenuDto menuDto) {
        return MEAL_PLAN;
    }

}
