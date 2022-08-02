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

    private static final String INGREDIENT_DETAIL = "ingredients/detail";
    private static final String INGREDIENT_LIST = "ingredients/list";

    private final IIngredientService ingredientService;

/*    @GetMapping
    public String getFarmersPage() {
        return INGREDIENT_LIST;
    }*/

    @GetMapping("/{id}/edit")
    public String updateIngredientForm(Model model, @PathVariable Long id) throws IngredientException {
        IngredientDto ingredientDto = ingredientService.updateIngredientForm(model, id);
        model.addAttribute("ingredient", ingredientDto);
        model.addAttribute("model", model);

        return INGREDIENT_DETAIL;
    }

    @GetMapping
    public String getList(Model model, @RequestParam(name= "page", defaultValue = "1") Integer currentPage,
                          @RequestParam(name="size", defaultValue = "10") Integer size,
                          @RequestParam(name="sortField", required=false, defaultValue = "id") String sortField,
                          @RequestParam(name="sortDir", required = false, defaultValue = "asc") String sortDir) {
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
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        }

        return INGREDIENT_LIST;
    }

    @PostMapping
    @ResponseBody
    public IngredientDto addIngredient(@RequestAttribute IngredientDto ingredientDto) {
        Ingredient updatedEntity = ingredientService.addIngredient(ingredientDto);
        return new IngredientDto(updatedEntity);
    }

    @PostMapping("/{id}/edit")
    public IngredientDto updateIngredient(@PathVariable Long id, Model model) throws IngredientException {
        return ingredientService.updateIngredientForm(model, id);
    }

    @GetMapping("/delete/{id}/{pageNo}")
    public String deleteIngredient(@PathVariable Long id, @PathVariable Long pageNo) {
        ingredientService.deleteIngredient(id);
        return "redirect:/ingredients?page=" + pageNo;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception e) {
        log.error(e.getMessage());

        return new ResponseEntity<>("Unexpected exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
