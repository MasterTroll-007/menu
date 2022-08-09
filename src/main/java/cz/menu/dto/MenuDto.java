package cz.menu.dto;

import cz.client.dto.ClientDto;
import cz.menu.entity.Menu;
import cz.recipe.dto.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto implements Serializable {
    private Long id;
    private ClientDto client;
    private List<RecipeDto> recipes;

    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.client = new ClientDto(menu.getClient());
        List<RecipeDto> recipeDtos = new ArrayList<>();
        menu.getRecipes()
                .forEach(recipe -> recipeDtos.add(new RecipeDto(recipe)));
        this.recipes = recipeDtos;
    }
}
