package cz.client.dto;

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
}
