package cz.menu.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
