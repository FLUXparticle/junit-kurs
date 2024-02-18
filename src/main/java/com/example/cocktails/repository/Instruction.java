package com.example.cocktails.repository;

import org.jdbi.v3.core.mapper.reflect.*;
import org.json.simple.*;

import java.io.*;

public class Instruction {

    private Long id;

    private Integer amountCL;

    private Ingredient ingredient;

    public Instruction(@ColumnName("id") Long id, @ColumnName("amountCL") Integer amountCL) {
        this.id = id;
        this.amountCL = amountCL;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (amountCL != null) {
            sb.append(amountCL);
            sb.append("cl ");
        }
        sb.append(ingredient.getName());

        return sb.toString();
    }

    public Integer getAmountCL() {
        return amountCL;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

}
