package cz.recipe.control;

import cz.recipe.dto.RecipeTemplateDto;
import cz.recipe.exception.RecipeException;
import cz.recipe.service.IRecipeService;
import cz.recipe.service.IRecipeTemplateIngredientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
@RequestMapping("/recipes")
public class RecipeControl {

    private static final String RECIPE_EDIT = "recipes/edit";
    private static final String NEW_RECIPE = "recipes/add";
    private static final String RECIPE_LIST = "recipes/list";
    private static final String REDIRECT_EDIT = "redirect:/recipes/edit/";

    private final IRecipeService recipeService;
    private final IRecipeTemplateIngredientService recipeTemplateIngredientService;

    @GetMapping
    public String getList(Model model, @RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                          @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
                          @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        Page<RecipeTemplateDto> recipeTemplatePage = recipeService.findPaginated(currentPage, size, sortField, sortDir);

        model.addAttribute("recipeTemplatePage", recipeTemplatePage);

        int totalPages = recipeTemplatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("totalItems", recipeTemplatePage.getTotalElements());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", recipeTemplatePage.getTotalPages());
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("sortField", sortField);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        }

        return RECIPE_LIST;
    }

    @GetMapping("/new")
    public String newRecipe(Model model, @ModelAttribute RecipeTemplateDto recipeTemplateDto) {
        model.addAttribute("model", model);
        model.addAttribute("recipeTemplate", recipeTemplateDto);
        return NEW_RECIPE;
    }

    @PostMapping("/new")
    public String addRecipe(@ModelAttribute(name = "recipeTemplate") RecipeTemplateDto recipeTemplateDto) {
        Long recipeId = recipeService.addRecipe(recipeTemplateDto).getId();
        return REDIRECT_EDIT + recipeId;
    }

    @PostMapping("/edit/{id}")
    public String updateRecipe(@PathVariable Long id, @ModelAttribute RecipeTemplateDto recipeTemplateDto) throws RecipeException {
        recipeService.updateRecipeForm(recipeTemplateDto, id);
        return REDIRECT_EDIT + id;
    }

    @GetMapping("/edit/{id}")
    public String updateRecipeForm(Model model, @PathVariable Long id) throws RecipeException {
        RecipeTemplateDto recipeDto = recipeService.findById(id);

        model.addAttribute("recipeTemplate", recipeDto);
        model.addAttribute("model", model);

        return RECIPE_EDIT;
    }

    @GetMapping("/delete")
    public String deleteRecipe(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "page") Long page,
                                   @RequestParam(name = "size") Long size,
                                   @RequestParam(name = "sortField") String sortField,
                                   @RequestParam(name = "sortDir") String sortDir) {
        recipeService.deleteRecipe(id);
        return "redirect:/recipes?size=" + size + "&page=" + page + "&sortField=" + sortField + "&sortDir=" + sortDir;
    }

    @PostMapping("/removeRecipeTemplateIngredient/{ingredientId}/{id}")
    public String removeRecipeTemplateIngredient(@PathVariable Long ingredientId, @PathVariable Long id) {
        recipeTemplateIngredientService.removeRecipeTemplateIngredient(ingredientId);
        return REDIRECT_EDIT + id;
    }
}
