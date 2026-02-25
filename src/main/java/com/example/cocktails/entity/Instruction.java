package com.example.cocktails.entity;

import jakarta.persistence.*;

@Entity
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amountcl")
    private Integer amountCL;

    @ManyToOne
    private Ingredient ingredient;

    protected Instruction() {
        // for JPA
    }

    public Instruction(Integer amountCL, Ingredient ingredient) {
        this.amountCL = amountCL;
        this.ingredient = ingredient;
    }

    public Integer getAmountCL() {
        return amountCL;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

}
