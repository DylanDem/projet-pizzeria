package com.accenture.service.dto;

import com.accenture.model.Tailles;

import java.util.List;
import java.util.Map;

public record PizzaRequestDto (

    String nom,
    List<Integer> idIngredient,
    Map<Tailles, Double> tarifs


) {

}
