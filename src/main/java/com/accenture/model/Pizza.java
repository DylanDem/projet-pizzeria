package com.accenture.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private List<String> ingredients;

    public Pizza(String nom, Tailles tailles, List<String> ingredients) {
        this.nom = nom;
        this.tailles = tailles;
        this.ingredients = ingredients;
    }

}
