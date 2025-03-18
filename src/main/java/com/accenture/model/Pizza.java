package com.accenture.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @Enumerated(EnumType.STRING)
    private Tailles tailles;
    private int tarif;

    @ManyToMany
    private List<Ingredient> ingredients;

    public Pizza(String nom, Tailles tailles, int tarif, List<Ingredient> ingredients) {
        this.nom = nom;
        this.tailles = tailles;
        this.tarif = tarif;
        this.ingredients = ingredients;
    }
}