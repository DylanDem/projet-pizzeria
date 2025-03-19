package com.accenture.service;

import com.accenture.model.Ingredient;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;

import java.util.List;

public interface IngredientService {
    Ingredient ajouterIngredient(Ingredient ingredient);

    List<Ingredient> trouverTous();

    Ingredient trouver(int idIngredient);

    void supprimer(int idIngredient);

    IngredientResponseDto modifierPartiellement(int idIngredient, IngredientRequestDto ingredientRequestDto);
}
