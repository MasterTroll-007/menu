package cz.menu.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Client extends AbstractPersistable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientFirstName;

    private String clientSecondName;

    private int clientAge;

    private int clientWeight;

    private int clientHeight;

    private float bmrCoef;
}
