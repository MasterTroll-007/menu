package cz.menu.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Embeddable
public class MenuType extends AbstractPersistable implements Serializable {

    private String name;

    private String description;

}
