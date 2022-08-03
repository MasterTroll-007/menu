package cz.ingredient.service;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import cz.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngredientService implements IIngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public Ingredient addIngredient(IngredientDto ingredientDto) {
        Ingredient ingredientEntity = ingredientEntityMapper(ingredientDto);
        return ingredientRepository.save(ingredientEntity);
    }

    @Override
    public IngredientDto updateIngredientForm(Model model, Long ingredientId) throws IngredientException {
        Ingredient entity = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IngredientException("Nenalezeny žádné ingredience s daným ID=" + ingredientId));
        return new IngredientDto(entity);
    }

    @Override
    public void deleteIngredient(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @Override
    public Page<IngredientDto> findPaginated(int currentPage, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Ingredient> ingredients = ingredientRepository.findAll(pageable);
        List<IngredientDto> ingredientsDto = new ArrayList<>();
        ingredients.forEach(ingredient ->
            ingredientsDto.add(new IngredientDto(ingredient)));

        return new PageImpl<>(ingredientsDto, pageable, ingredients.getTotalElements());
    }

    @Override
    public List<Ingredient> getIngredients(String term) throws IngredientException {
        return ingredientRepository.findByNameIgnoreCaseLike("%" + term + "%")
                .orElseThrow(() -> new IngredientException("Ingredience nenalezena."));
    }

    private Ingredient ingredientEntityMapper(IngredientDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        Ingredient ingredient = modelMapper.map(dto, Ingredient.class);
        ingredient.calculateKj();
        return ingredient;
    }

}
