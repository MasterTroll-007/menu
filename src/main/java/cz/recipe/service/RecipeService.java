package cz.recipe.service;

import cz.recipe.dto.RecipeDto;
import cz.recipe.entity.Recipe;
import cz.recipe.exception.RecipeException;
import cz.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService implements IRecipeService{

    private final RecipeRepository recipeRepository;

    @Override
    public Recipe addRecipe(RecipeDto recipeDto) {
        recipeDto.setRecipeIngredients(
                recipeDto.getRecipeIngredients()
                .stream()
                .filter(recipeIngredient -> recipeIngredient.getIngredientId() != null)
                .toList()
        );
        Recipe recipeEntity = recipeEntityMapper(recipeDto);
        return recipeRepository.save(recipeEntity);
    }

    @Override
    public RecipeDto findById(Long recipeId) throws RecipeException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Nenalezeny žádný recept s daným ID=" + recipeId));
        return new RecipeDto(recipe);
    }

    @Override
    public RecipeDto updateRecipeForm(Model model, Long recipeId) throws RecipeException {
        Recipe entity = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Nenalezeny žádný recept s daným ID=" + recipeId));
        //TODO execute update
        return new RecipeDto(entity);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public Page<RecipeDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Recipe> recipes = recipeRepository.findAll(pageable);
        List<RecipeDto> recipesDto = new ArrayList<>();
        recipes.forEach(recipe ->
                recipesDto.add(new RecipeDto(recipe)));

        return new PageImpl<>(recipesDto, pageable, recipes.getTotalElements());
    }

    private Recipe recipeEntityMapper(RecipeDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Recipe.class);
    }

}
