package cz.ingredient.service;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;

public interface IIngredientService {


    Ingredient addIngredient(IngredientDto ingredientDto);

    Ingredient updateIngredient(IngredientDto ingredientDto) throws IngredientException;

    void removeIngredient(Long ingredientId);
}
