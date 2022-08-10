package cz.ingredient.control;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import cz.ingredient.service.IIngredientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/ingredients")
public class IngredientControl {

    private static final String INGREDIENT_DETAIL = "ingredients/edit";
    private static final String NEW_INGREDIENT = "ingredients/add";
    private static final String INGREDIENT_LIST = "ingredients/list";
    private static final String REDIRECT_INGREDIENT_LIST = "redirect:/ingredients";

    private final IIngredientService ingredientService;


    @GetMapping("/edit/{id}")
    public String updateIngredientForm(Model model, @PathVariable Long id) throws IngredientException {
        IngredientDto ingredientDto = ingredientService.getIngredientForm(id);
        model.addAttribute("ingredient", ingredientDto);
        model.addAttribute("model", model);

        return INGREDIENT_DETAIL;
    }

    @PostMapping("/edit/{id}")
    public String updateIngredient(@PathVariable Long id, @ModelAttribute IngredientDto ingredientDto) throws IngredientException {
        ingredientService.updateIngredientForm(ingredientDto, id);
        return REDIRECT_INGREDIENT_LIST;
    }

    @GetMapping
    public String getList(Model model, @RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                          @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
                          @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        Page<IngredientDto> ingredientPage = ingredientService.findPaginated(currentPage, size, sortField, sortDir);

        model.addAttribute("ingredientPage", ingredientPage);

        int totalPages = ingredientPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("totalItems", ingredientPage.getTotalElements());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", ingredientPage.getTotalPages());
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("sortField", sortField);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        }

        return INGREDIENT_LIST;
    }

    @ResponseBody
    @GetMapping(path = "/find")
    public List<Ingredient> list(String term) throws IngredientException {
        return ingredientService.getIngredients(term);
    }

    @ResponseBody
    @GetMapping(path = "/find/{id}")
    public Ingredient findById(@PathVariable Long id) throws IngredientException {
        return ingredientService.getById(id);
    }

    @PostMapping("/new")
    public String addIngredient(@ModelAttribute(name = "ingredient") IngredientDto ingredientDto) {
        ingredientService.addIngredient(ingredientDto);
        return REDIRECT_INGREDIENT_LIST;
    }

    @GetMapping("/new")
    public String newIngredientForm(Model model, @ModelAttribute IngredientDto ingredientDto) {
        model.addAttribute("model", model);
        model.addAttribute("ingredient", ingredientDto);
        return NEW_INGREDIENT;
    }

    @GetMapping("/delete")
    public String deleteIngredient(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "page") Long page,
                                   @RequestParam(name = "size") Long size,
                                   @RequestParam(name = "sortField") String sortField,
                                   @RequestParam(name = "sortDir") String sortDir) {
        ingredientService.deleteIngredient(id);
        return "redirect:/ingredients?size=" + size + "&page=" + page + "&sortField=" + sortField + "&sortDir=" + sortDir;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception e) {
        log.error(e.getMessage());

        return new ResponseEntity<>("Unexpected exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
