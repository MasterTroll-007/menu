package cz.ingredient.entity;

import cz.menu.entity.AbstractPersistable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@RequiredArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ingredient extends AbstractPersistable implements Serializable {

    private String name;
    private Long proteins;
    private Long carbohydrates;
    private Long fats;
    private Long fibre;
    private Long kJ;

    public void calculateKj() {
        this.kJ = proteins * 17 + carbohydrates * 17 + fats * 38;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(name, ingredient.name) &&
                Objects.equals(proteins, ingredient.proteins) &&
                Objects.equals(carbohydrates, ingredient.carbohydrates) &&
                Objects.equals(fats, ingredient.fats) &&
                Objects.equals(fibre, ingredient.fibre) &&
                Objects.equals(kJ, ingredient.kJ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, proteins, carbohydrates, fats, fibre, kJ);
    }
}
