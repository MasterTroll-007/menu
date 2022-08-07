package cz.recipe.control;

import cz.recipe.dto.RecipeDto;
import cz.recipe.exception.RecipeException;
import cz.recipe.service.IRecipeService;
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

    @GetMapping
    public String getList(Model model, @RequestParam(name = "page", defaultValue = "1") Integer currentPage,
                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                          @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
                          @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        Page<RecipeDto> recipePage = recipeService.findPaginated(currentPage, size, sortField, sortDir);

        model.addAttribute("recipePage", recipePage);

        int totalPages = recipePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .toList();
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("totalItems", recipePage.getTotalElements());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", recipePage.getTotalPages());
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("sortField", sortField);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        }

        return RECIPE_LIST;
    }

    @GetMapping("/new")
    public String newRecipe(Model model, @ModelAttribute RecipeDto recipeDto) {
        model.addAttribute("model", model);
        model.addAttribute("recipe", recipeDto);
        return NEW_RECIPE;
    }

    @PostMapping("/new")
    public String addRecipe(@ModelAttribute(name = "recipe") RecipeDto recipeDto) {
        Long recipeId = recipeService.addRecipe(recipeDto).getId();
        return REDIRECT_EDIT + recipeId;
    }

    @PostMapping("/edit/{id}")
    public String updateRecipe(@PathVariable Long id, Model model) throws RecipeException {
        recipeService.updateRecipeForm(model, id);
        return REDIRECT_EDIT + id;
    }

    @GetMapping("/edit/{id}")
    public String updateRecipeForm(Model model, @PathVariable Long id) throws RecipeException {
        RecipeDto recipeDto = recipeService.findById(id);

        model.addAttribute("recipe", recipeDto);
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

}
