package cz.menu.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Recipe extends AbstractPersistable implements Serializable {

    private String name;

    private String description;
    @ManyToMany
    private List<Ingredient> ingredients;
}
