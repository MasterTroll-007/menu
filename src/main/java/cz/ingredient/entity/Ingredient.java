package cz.ingredient.entity;

import com.fasterxml.jackson.annotation.JsonView;
import cz.menu.entity.AbstractPersistable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

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

    @JsonView(DataTablesOutput.View.class)
    private String name;
    @JsonView(DataTablesOutput.View.class)
    private int proteins;
    @JsonView(DataTablesOutput.View.class)
    private int carbohydrates;
    @JsonView(DataTablesOutput.View.class)
    private int fats;
    @JsonView(DataTablesOutput.View.class)
    private int fibre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Ingredient ingredient = (Ingredient) o;
        return  Objects.equals(name, ingredient.name) &&
                Objects.equals(proteins, ingredient.proteins) &&
                Objects.equals(carbohydrates, ingredient.carbohydrates) &&
                Objects.equals(fats, ingredient.fats) &&
                Objects.equals(fibre, ingredient.fibre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, proteins, carbohydrates, fats, fibre);
    }
}
