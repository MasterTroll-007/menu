package cz.menu.control;

import cz.menu.dto.MenuDto;
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

    @GetMapping(value = "/{id}")
    public String bmrForm(@PathVariable("id") Long id, Model model, @ModelAttribute MenuDto menuDto) throws Exception {
        menuService.calculateBMR(model, id);
        return BMR_FORM;
    }


}
