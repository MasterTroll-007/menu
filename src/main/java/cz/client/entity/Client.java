package cz.client.entity;


import cz.menu.entity.AbstractPersistable;
import cz.menu.model.Sex;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Client extends AbstractPersistable implements Serializable {

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String firstName;

    private String secondName;

    private long age;

    private long weight;

    private long height;

    private float bmrCoef;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bmr_id", referencedColumnName = "id")
    private BMR bmr;

    public String getFullName() {
        return this.getFirstName() + " " + this.getSecondName();
    }
}
