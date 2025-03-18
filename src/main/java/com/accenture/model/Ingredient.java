package com.accenture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tomate;
    private String mozzarella;
    private String chorizo;
    private String olive;
    private String basilic;
    private String poulet;
    private String thon;
    private int quantite;

    public Ingredient(String mozzarella, String chorizo, String olive, String basilic, String poulet, String thon, int quantite, String tomate) {
        this.mozzarella = mozzarella;
        this.chorizo = chorizo;
        this.olive = olive;
        this.basilic = basilic;
        this.poulet = poulet;
        this.thon = thon;
        this.quantite = quantite;
        this.tomate = tomate;
    }
}
