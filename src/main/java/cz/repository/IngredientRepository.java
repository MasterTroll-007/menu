package cz.repository;


import cz.ingredient.entity.Ingredient;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends DataTablesRepository<Ingredient, Long> {

}
