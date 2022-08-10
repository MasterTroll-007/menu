package cz.ingredient.service;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface IIngredientService {

    Ingredient addIngredient(IngredientDto ingredientDto);

    IngredientDto updateIngredientForm(IngredientDto ingredientDto, Long ingredientId) throws IngredientException;

    IngredientDto getIngredientForm(Long id) throws IngredientException;

    void deleteIngredient(Long ingredientId);

    Page<IngredientDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir);

    List<Ingredient> getIngredients(String term) throws IngredientException;

    Ingredient getById(Long id) throws IngredientException;
}
