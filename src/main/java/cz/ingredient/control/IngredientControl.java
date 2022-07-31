package cz.ingredient.control;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import cz.ingredient.service.IIngredientService;
import cz.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/ingredients")
public class IngredientControl {

    private static final String INGREDIENT_DETAIL = "ingredients/detail";
    private static final String INGREDIENT_LIST = "ingredients/list";

    private final IIngredientService ingredientService;

    @GetMapping
    public String getFarmersPage() {
        return INGREDIENT_LIST;
    }

    @GetMapping("/{id}/edit")
    public String updateIngredientForm(Model model, @PathVariable Long id) throws IngredientException {
        IngredientDto ingredientDto = ingredientService.updateIngredientForm(model, id);
        model.addAttribute("ingredient", ingredientDto);
        model.addAttribute("model", model);

        return INGREDIENT_DETAIL;
    }

    @GetMapping
    public String getList(Model model, @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<IngredientDto> bookPage = ingredientService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("ingredientPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return INGREDIENT_LIST;

    }

    @PostMapping
    @ResponseBody
    public IngredientDto addIngredient(@RequestAttribute IngredientDto ingredientDto) {
        Ingredient updatedEntity = ingredientService.addIngredient(ingredientDto);
        return new IngredientDto(updatedEntity);
    }

/*    @PostMapping("/{id}/edit")
    public IngredientDto updateIngredient(@RequestAttribute IngredientDto ingredientDto) throws IngredientException {
        Ingredient updatedEntity = ingredientService.updateIngredient(ingredientDto);
        return new IngredientDto(updatedEntity);
    }*/

    @PostMapping("/api/v1/{id}/remove")
    @ResponseBody
    public void removeIngredient(@PathVariable Long id) {
        ingredientService.removeIngredient(id);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpectedException(Exception e) {
        log.error(e.getMessage());

        return new ResponseEntity<>("Unexpected exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
