package com.accenture.service;

import com.accenture.model.Pizza;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;

import java.util.List;

public interface PizzaService {
    Pizza trouverParNom(String pizza);

    PizzaResponseDto ajouter(PizzaRequestDto pizzaRequestDto);

    List<Pizza> trouverTous();

    Pizza trouver(int idPizza);

    void supprimer(int idPizza);
}
