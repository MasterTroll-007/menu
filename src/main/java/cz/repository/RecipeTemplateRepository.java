package cz.repository;


import cz.recipe.entity.RecipeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeTemplateRepository extends JpaRepository<RecipeTemplate, Long> {

}
