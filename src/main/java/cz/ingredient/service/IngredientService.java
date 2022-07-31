package cz.ingredient.service;

import cz.ingredient.dto.IngredientDto;
import cz.ingredient.entity.Ingredient;
import cz.ingredient.exception.IngredientException;
import cz.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collections;
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
                .orElseThrow(() -> new IngredientException("Nenalezeny žádné ingredience"));
        return new IngredientDto(entity);
    }

    @Override
    public void removeIngredient(Long ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @Override
    public Page<IngredientDto> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Ingredient> ingredients = ingredientRepository.findAll();
        List<IngredientDto> ingredientsDto = new ArrayList<>();
        ingredients.forEach(ingredient ->
            ingredientsDto.add(new IngredientDto(ingredient)));

        List<IngredientDto> list;

        if (ingredientsDto.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, ingredientsDto.size());
            list = ingredientsDto.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), ingredients.size());
    }

    private Ingredient ingredientEntityMapper(IngredientDto dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Ingredient.class);
    }

}
