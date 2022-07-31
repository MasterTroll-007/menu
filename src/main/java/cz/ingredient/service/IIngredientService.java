package cz.ingredient.service;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface IIngredientService {


    Ingredient addIngredient(IngredientDto ingredientDto);

    IngredientDto updateIngredientForm(Model model, Long ingredientId) throws IngredientException;

    void removeIngredient(Long ingredientId);

    Page<IngredientDto> findPaginated(Pageable pageable);
}
