package com.accenture.service.dto;

import com.accenture.model.Tailles;

public record PizzaRequestDto (

    String nom,
    Tailles tailles,
    IngredientRequestDto ingredient,
    int tarif


) {

}
