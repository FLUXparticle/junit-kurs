package com.example.cocktails.repository;

import org.jdbi.v3.core.mapper.reflect.*;

import java.util.*;

public class Cocktail implements Comparable<Cocktail> {

    private Long id;

    private String name;

    private Collection<Instruction> instructions;

    public Cocktail(@ColumnName("id") Long id, @ColumnName("name") String name) {
        this.id = id;
        this.name = name;

        instructions = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructions=" + instructions +
                '}';
    }

    @Override
    public int compareTo(Cocktail other) {
        return name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cocktail cocktail = (Cocktail) o;
        return id.equals(cocktail.id) && name.equals(cocktail.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
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

}
