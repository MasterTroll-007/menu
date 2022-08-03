package cz.client.control;

import cz.client.dto.ClientDto;
import cz.client.exception.ClientException;
import cz.client.service.IClientService;
import cz.client.dto.BMRDto;
import cz.menu.exception.MenuException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/clients")
public class ClientController {

    private final IClientService clientService;

    private static final String MENU_FORM = "client/add";
    private static final String MENU_LIST = "client/list";
    private static final String BMR_FORM = "client/bmrForm";
    private static final String MEAL_PLAN = "client/mealPlan";
    private static final String REDIRECT_BMR_FORM = "redirect:/clients/";
    private static final String REDIRECT_MEAL_PLAN = "redirect:/mealPlan/";

    @GetMapping("/new")
    public String newClientForm(Model model, @ModelAttribute ClientDto clientDto) {
        model.addAttribute("model", model);
        model.addAttribute("client", clientDto);
        return MENU_FORM;
    }

    @GetMapping
    public String getList(Model model, @RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                          @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
                          @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        Page<ClientDto> clientPage = clientService.findPaginated(currentPage, size, sortField, sortDir);

        model.addAttribute("clientPage", clientPage);

        int totalPages = clientPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("totalItems", clientPage.getTotalElements());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", clientPage.getTotalPages());
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("sortField", sortField);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        }

        return MENU_LIST;
    }

    @PostMapping("/new")
    public String saveMenuForm(Model model, @ModelAttribute ClientDto clientDto) {
        Long clientId = clientService.saveClientForm(model, clientDto).getId();
        return REDIRECT_BMR_FORM + clientId;
    }

    @GetMapping(value = "/{id}")
    public String bmrForm(@PathVariable("id") Long clientId, Model model, @ModelAttribute ClientDto clientDto)
            throws MenuException, ClientException {
        clientService.calculateBMR(model, clientId);
        return BMR_FORM;
    }

    @PostMapping(value = "/{id}")
    public String saveBmrForm(@PathVariable("id") Long clientId, Model model, @ModelAttribute BMRDto bmrDto)
            throws MenuException, ClientException {
        clientService.saveBmr(model, bmrDto, clientId);
        return REDIRECT_MEAL_PLAN + clientId;
    }

    @GetMapping(value = "/mealPlan/{clientId}")
    public String mealPlanForm(@PathVariable("clientId") Long clientId, Model model, @ModelAttribute ClientDto clientDto) {
        return MEAL_PLAN;
    }

    @PostMapping(value = "/mealPlan/{clientId}")
    public String saveMealPlan(@PathVariable("clientId") Long clientId, Model model, @ModelAttribute ClientDto clientDto) {
        return MEAL_PLAN;
    }

}
