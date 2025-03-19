package com.accenture.controller;

import com.accenture.model.Ingredient;
import com.accenture.service.IngredientService;
import com.accenture.service.dto.IngredientRequestDto;
import com.accenture.service.dto.IngredientResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    ResponseEntity<Ingredient> ajouter(@RequestBody Ingredient ingredient) {
        Ingredient ajoute = ingredientService.ajouterIngredient(ingredient);
        log.info("ajoute : " + ajoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(ajoute);
    }

    @GetMapping
    List<Ingredient> ingredients() {
        return ingredientService.trouverTous();
    }

    @GetMapping("/{idIngredient}")
    ResponseEntity<Ingredient> trouver(@PathVariable("idIngredient") int idIngredient){
        Ingredient ingredient = ingredientService.trouver(idIngredient);
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{idIngredient}")
    ResponseEntity<Void> supprimer(@PathVariable("idIngredient") int idIngredient){
        ingredientService.supprimer(idIngredient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{idIngredient}")
    ResponseEntity<IngredientResponseDto> modifierPartiellement(@PathVariable("idIngredient") int idIngredient, @RequestBody IngredientRequestDto ingredientRequestDto) {
        IngredientResponseDto reponse = ingredientService.modifierPartiellement(idIngredient, ingredientRequestDto);
     return ResponseEntity.ok(reponse);
    }
    }


