package com.accenture.controller;

import com.accenture.model.Ingredient;
import com.accenture.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientService ingredientService;

    @PostMapping
    ResponseEntity<Ingredient> ajouter(@RequestBody Ingredient ingredient) {
        Ingredient ajoute = ingredientService.ajouterIngredient(ingredient);
        log.info("ajoute : " + ajoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(ajoute);
    }
}