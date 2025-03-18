package com.accenture.service.dto;

import com.accenture.model.Tailles;

public record PizzaResponseDto (

    String nom,
    Tailles tailles,
    IngredientDto ingredient,
    int tarif

) {}
