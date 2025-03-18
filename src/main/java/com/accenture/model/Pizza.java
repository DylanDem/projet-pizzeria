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
    private Tailles tailles;
    private int tarif;

    @ManyToMany(cascade = CascadeType.ALL)
    private Ingredient ingredients;

    public Pizza(String nom, Tailles tailles, int tarif, Ingredient ingredients) {
        this.nom = nom;
        this.tailles = tailles;
        this.tarif = tarif;
        this.ingredients = ingredients;
    }
}