package cz.client.dto;

import cz.client.entity.Client;
import cz.ingredient.entity.Ingredient;
import cz.menu.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto implements Serializable {
    private Long id;
    private Sex sex;
    private String firstName;
    private String secondName;
    private int age;
    private int weight;
    private int height;
    private float bmrCoef;
    public ClientDto(Client client) {
        this.id = client.getId();
        this.sex = client.getSex();
        this.firstName = client.getFirstName();
        this.secondName = client.getSecondName();
        this.age = client.getAge();
        this.weight = client.getWeight();
        this.height = client.getHeight();
        this.bmrCoef = client.getBmrCoef();
    }
}
