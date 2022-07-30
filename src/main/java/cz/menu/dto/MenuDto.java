package cz.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto implements Serializable {
    private Long id;
    private ClientDto client;
    private List<RecipeDto> recipes;

}
