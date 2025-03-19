package com.accenture.service;

import com.accenture.model.Pizza;

import java.util.List;

public interface PizzaService {
    Pizza ajouter(Pizza pizza);

    List<Pizza> trouverTous();

    Pizza trouver(int idPizza);

    void supprimer(int idPizza);
}
