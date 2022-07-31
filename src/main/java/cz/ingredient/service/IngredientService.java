package cz.ingredient.service;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import cz.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public Ingredient updateIngredient(IngredientDto dto) throws IngredientException {
        Ingredient entity = ingredientRepository.findById(dto.getId())
                .orElseThrow(() -> new IngredientException("Nenalezeny žádné ingredience"));
        mapIngredientEntity(dto, entity);

        return ingredientRepository.save(entity);
    }

    @Override
    public Ingredient removeIngredient(Long ingredientId) {
        return null;
    }

    private void mapIngredientEntity(IngredientDto dto, Ingredient entity) {
        entity.setCarbohydrates(dto.getCarbohydrates());
        entity.setFats(dto.getFats());
        entity.setProteins(dto.getProteins());
        entity.setFibre(dto.getFibre());
        entity.setName(dto.getName());
    }

    private Ingredient ingredientEntityMapper(IngredientDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Ingredient.class);
    }

}
