package com.example.cocktails.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Cocktail implements Comparable<Cocktail> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "instructions_id")
    @JsonIgnore
    private Collection<Instruction> instructions = new ArrayList<>();

    protected Cocktail() {
        // for JPA
    }

    public Cocktail(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Cocktail(String name, List<Instruction> instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public int compareTo(Cocktail other) {
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cocktail cocktail = (Cocktail) o;
        return Objects.equals(id, cocktail.id) && Objects.equals(name, cocktail.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
