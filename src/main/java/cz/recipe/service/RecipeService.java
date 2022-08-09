package cz.recipe.service;

import cz.recipe.dto.RecipeTemplateDto;
import cz.recipe.dto.RecipeTemplateIngredientDto;
import cz.recipe.entity.RecipeTemplate;
import cz.recipe.entity.RecipeTemplateIngredient;
import cz.recipe.exception.RecipeException;
import cz.repository.RecipeTemplateIngredientRepository;
import cz.repository.RecipeTemplateRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeService implements IRecipeService {

    private final RecipeTemplateRepository recipeTemplateRepository;
    private final RecipeTemplateIngredientRepository recipeTemplateIngredientRepository;

    @Override
    public RecipeTemplate addRecipe(RecipeTemplateDto recipeTemplateDto) {
        recipeTemplateDto.setRecipeTemplateIngredients(
                recipeTemplateDto.getRecipeTemplateIngredients()
                        .stream()
                        .filter(ingredientDto -> ingredientDto.getIngredientId() != null)
                        .toList()
        );
        RecipeTemplate recipeTemplateEntity = recipeTemplateEntityMapper(recipeTemplateDto);
        RecipeTemplate savedEntity = recipeTemplateRepository.save(recipeTemplateEntity);

        savedEntity.getRecipeTemplateIngredients().forEach(ingredient -> ingredient.setRecipeTemplateId(savedEntity.getId()));
        return recipeTemplateRepository.save(savedEntity);
    }

    @Override
    public RecipeTemplateDto findById(Long recipeId) throws RecipeException {
        RecipeTemplate recipeTemplate = recipeTemplateRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Nenalezeny žádný recept s daným ID=" + recipeId));
        return new RecipeTemplateDto(recipeTemplate);
    }

    @Override
    @Transactional
    public RecipeTemplateDto updateRecipeForm(RecipeTemplateDto dto, Long recipeId) throws RecipeException {
        RecipeTemplate entity = recipeTemplateRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeException("Nenalezeny žádný recept s daným ID=" + recipeId));
        List<RecipeTemplateIngredient> ingredients = entity.getRecipeTemplateIngredients();
        List<RecipeTemplateIngredientDto> ingredientsDto = dto.getRecipeTemplateIngredients()
                .stream()
                .filter(ingredient -> ingredient.getIngredientId() != null)
                .toList();
        //TODO      have IDs of ingredientsDto -> map it to the entity and save it ->
        //TODO      compare id, if it exists, replace entity.get(i) content with dto.get(i) content;
        //TODO      If there is new id, just store it into new RecipeTemplateIngredient() and add it to the list before save
        //TODO      If there is id in entity which is not at dto, remove it by deleteById(entity.get(i).getId());

        return null;
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeTemplateRepository.deleteById(recipeId);
    }

    @Override
    public Page<RecipeTemplateDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<RecipeTemplate> recipes = recipeTemplateRepository.findAll(pageable);
        List<RecipeTemplateDto> recipeTemplateDtos = new ArrayList<>();
        recipes.forEach(recipe ->
                recipeTemplateDtos.add(new RecipeTemplateDto(recipe)));

        return new PageImpl<>(recipeTemplateDtos, pageable, recipes.getTotalElements());
    }

    private RecipeTemplate recipeTemplateEntityMapper(RecipeTemplateDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, RecipeTemplate.class);
    }

    private RecipeTemplateIngredient recipeTemplateIngredientEntityMapper(RecipeTemplateIngredientDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, RecipeTemplateIngredient.class);
    }

}
