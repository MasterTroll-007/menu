package cz.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto implements Serializable {
    private Long id;
    private String firstName;
    private String secondName;
    private int age;
    private int weight;
    private int height;
    private float bmrCoef;
}
