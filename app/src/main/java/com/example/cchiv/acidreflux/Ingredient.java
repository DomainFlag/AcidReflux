package com.example.cchiv.acidreflux;

/**
 * Created by Cchiv on 20/09/2017.
 */

public class Ingredient {
    private String name;
    private int acidity;

    Ingredient(String name, int acidity) {
        this.name = name;
        this.acidity = acidity;
    }

    String getName() {
        return this.name;
    }

    int getAcidity() {
        return this.acidity;
    }
}
