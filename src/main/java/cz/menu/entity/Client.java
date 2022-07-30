package cz.menu.entity;


import cz.menu.model.Sex;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Client extends AbstractPersistable implements Serializable {
    private Long id;

    private Sex sex;

    private String firstName;

    private String secondName;

    private int age;

    private int weight;

    private int height;

    private float bmrCoef;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bmr_id", referencedColumnName = "id")
    private BMR bmr;
}
