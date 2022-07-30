package cz.menu.model;

import lombok.Getter;

@Getter
public enum Sex {
    MALE("Muž"),
    FEMALE("Žena");

    private final String gender;

    Sex(String gender) {
        this.gender = gender;
    }
}
