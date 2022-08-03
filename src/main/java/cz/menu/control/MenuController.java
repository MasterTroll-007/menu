package cz.menu.control;

import cz.ingredient.dto.IngredientDto;
import cz.menu.dto.BMRDto;
import cz.menu.dto.MenuDto;
import cz.menu.exception.MenuException;
import cz.menu.service.IMenuService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/menu")
public class MenuController {

    private final IMenuService menuService;

    private static final String MENU_FORM = "menu/form";
    private static final String MENU_LIST = "menu/list";
    private static final String BMR_FORM = "menu/bmrForm";
    private static final String MEAL_PLAN = "menu/mealPlan";
    private static final String REDIRECT_BMR_FORM = "redirect:/menu/";
    private static final String REDIRECT_MEAL_PLAN = "redirect:/menu/mealPlan/";

    @GetMapping("/new")
    public String newMenuForm(Model model, @ModelAttribute MenuDto menuDto) {
        model.addAttribute("model", model);
        model.addAttribute("menu", menuDto);
        return MENU_FORM;
    }

    @GetMapping
    public String getList(Model model, @RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                          @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
                          @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        Page<MenuDto> menuPage = menuService.findPaginated(currentPage, size, sortField, sortDir);

        model.addAttribute("ingredientPage", menuPage);

        int totalPages = menuPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("totalItems", menuPage.getTotalElements());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", menuPage.getTotalPages());
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("sortField", sortField);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        }

        return MENU_LIST;
    }

    @PostMapping("/new")
    public String saveMenuForm(Model model, @ModelAttribute MenuDto menuDto) {
        Long menuId = menuService.saveMenuForm(model, menuDto).getId();
        return REDIRECT_BMR_FORM + menuId;
    }

    @GetMapping(value = "/new/{menuId}")
    public String bmrForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute MenuDto menuDto)
            throws MenuException {
        menuService.calculateBMR(model, menuId);
        return BMR_FORM;
    }

    @PostMapping(value = "/new/{menuId}")
    public String saveBmrForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute BMRDto bmrDto)
            throws MenuException {
        menuService.saveBmr(model, bmrDto, menuId);
        return REDIRECT_MEAL_PLAN + menuId;
    }

    @GetMapping(value = "/new/mealPlan/{menuId}")
    public String mealPlanForm(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute MenuDto menuDto) {
        return MEAL_PLAN;
    }

    @PostMapping(value = "/new/mealPlan/{menuId}")
    public String saveMealPlan(@PathVariable("menuId") Long menuId, Model model, @ModelAttribute MenuDto menuDto) {
        return MEAL_PLAN;
    }

}
