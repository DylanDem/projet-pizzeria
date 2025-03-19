package com.accenture.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @ElementCollection
    @CollectionTable(name = "pizzas_prix", joinColumns = @JoinColumn(name = "pizza_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "taille")// Stocke la clé de l'EnumMap sous forme de chaîne
    @Column(name = "prix") // Valeur stockée
    private Map<Tailles, Double> tarifs;


    @ManyToMany
    private List<Ingredient> ingredients;

    public Pizza(String nom, Map<Tailles, Double> tarifs, List<Ingredient> ingredients) {
        this.nom = nom;
        this.tarifs = tarifs;
        this.ingredients = ingredients;
    }
}