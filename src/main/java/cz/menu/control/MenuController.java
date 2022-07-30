package cz.menu.control;

import cz.menu.dto.MenuDto;
import cz.menu.entity.Menu;
import cz.menu.service.IMenusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MenuController {

    private final IMenusService menusService;

    private static final String MENU_FORM = "menu/form";

    @GetMapping(value = "/menu")
    public String newMenuForm(Model model, @ModelAttribute MenuDto menuDto) {
        model.addAttribute("model", model);
        return MENU_FORM;
    }

    @PostMapping(value = "/menu")
    public String saveMenuForm(@ModelAttribute MenuDto menuDto) {
        menusService.saveMenuForm(menuDto);
        return MENU_FORM;
    }

}
