package cz.menu.entity;


import cz.client.entity.Client;
import cz.recipe.entity.Recipe;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Menu extends AbstractPersistable implements Serializable {

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

    @OneToMany
    private List<Recipe> recipes;
}
