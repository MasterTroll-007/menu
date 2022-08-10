package cz.client.dto;

import cz.client.entity.Client;
import cz.menu.model.Sex;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ClientDto implements Serializable {
    private Long id;
    private Sex sex;
    private String firstName;
    private String secondName;
    private long age;
    private long weight;
    private long height;
    private float bmrCoef;
    private BMRDto bmr;

    public String getFullName() {
        return this.getFirstName() + " " + this.getSecondName();
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.sex = client.getSex();
        this.firstName = client.getFirstName();
        this.secondName = client.getSecondName();
        this.age = client.getAge();
        this.weight = client.getWeight();
        this.height = client.getHeight();
        this.bmrCoef = client.getBmrCoef();
        if (client.getBmr() != null) {
            this.bmr = new BMRDto(client.getBmr());
        } else {
            this.bmr = new BMRDto();
        }
    }
}
