package com.example.cocktails.entity;

public class FridgeIngredient {

    private Long id;
    private String name;
    private boolean inFridge;

    protected FridgeIngredient() {
        // for JSON
    }

    public FridgeIngredient(Long id, String name, boolean inFridge) {
        this.id = id;
        this.name = name;
        this.inFridge = inFridge;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isInFridge() {
        return inFridge;
    }

}
