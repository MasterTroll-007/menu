package cz.repository;


import cz.recipe.entity.RecipeTemplateIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeTemplateIngredientRepository extends JpaRepository<RecipeTemplateIngredient, Long> {

    void deleteById(Long recipeTemplate);

    @Query(nativeQuery = true, value = "delete from recipe_template_ingredient where recipe_template_id is null;")
    void deleteOrphans();
}
