package cz.menu.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;import java.io.Serializable;

@Getter
@Setter
@Entity
public class Client extends AbstractPersistable implements Serializable {
    private Long id;

    private String firstName;

    private String secondName;

    private int age;

    private int weight;

    private int height;

    private float bmrCoef;
}
