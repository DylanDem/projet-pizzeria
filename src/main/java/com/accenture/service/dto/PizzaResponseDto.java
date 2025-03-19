package com.accenture.service.dto;

import com.accenture.model.Tailles;

import java.util.List;
import java.util.Map;

public record PizzaResponseDto (

        int id,
    String nom,
        List<String> nomIngredient,
        Map<Tailles, Double> tarif

) {}
