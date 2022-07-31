package cz.ingredient.control;

import com.fasterxml.jackson.annotation.JsonView;
import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import cz.ingredient.service.IIngredientService;
import cz.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/ingredients")
public class IngredientControl {

    private final IIngredientService ingredientService;
    private final IngredientRepository ingredientRepository;

    @GetMapping
    public String getFarmersPage() {
        return "ingredients/list";
    }

    @ResponseBody
    @JsonView(DataTablesOutput.View.class)
    @GetMapping("/api/v1")
    public DataTablesOutput<IngredientDto> getIngredients( DataTablesInput input) {
        input.addOrder("name", true);
        return ingredientRepository.findAll(input, IngredientDto::new);
    }

    @PostMapping
    @ResponseBody
    public IngredientDto addIngredient(@RequestAttribute IngredientDto ingredientDto) {
        Ingredient updatedEntity = ingredientService.addIngredient(ingredientDto);
        return new IngredientDto(updatedEntity);
    }

    @PostMapping("/api/v1/{id}/edit")
    public IngredientDto updateIngredient(@RequestAttribute IngredientDto ingredientDto) throws IngredientException {
        Ingredient updatedEntity = ingredientService.updateIngredient(ingredientDto);
        return new IngredientDto(updatedEntity);
    }

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
