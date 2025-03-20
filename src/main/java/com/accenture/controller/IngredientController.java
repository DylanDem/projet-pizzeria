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


/**
 * Contrôleur REST pour gérer les opérations sur les ingrédients.
 * Fournit des endpoints pour ajouter, récupérer, modifier partiellement et supprimer des ingrédients.
 * Utilise un logger pour enregistrer les actions importantes.
 */
@RestController
@Slf4j
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    /**
     * Ajoute un nouvel ingrédient.
     *
     * @param ingredient les détails de l'ingrédient à ajouter
     * @return une réponse contenant l'ingrédient ajouté avec un statut HTTP 201
     */
    @PostMapping
    ResponseEntity<Ingredient> ajouter(@RequestBody Ingredient ingredient) {
        log.info("Ajout d'un nouvel ingrédient : {}", ingredient);
        Ingredient ajoute = ingredientService.ajouterIngredient(ingredient);
        log.debug("ajouté : " + ajoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(ajoute);
    }

    @GetMapping
    List<Ingredient> ingredients() {
        log.info("Récupération de tous les ingrédients.");
        return ingredientService.trouverTous();
    }


    /**
     * Récupère un ingrédient par son identifiant.
     *
     * @param idIngredient l'identifiant de l'ingrédient
     * @return une réponse contenant l'ingrédient trouvé
     */
    @GetMapping("/{idIngredient}")
    ResponseEntity<Ingredient> trouver(@PathVariable("idIngredient") int idIngredient){
        Ingredient ingredient = ingredientService.trouver(idIngredient);
        log.debug("Ingrédient trouvé : {}", ingredient);
        return ResponseEntity.ok(ingredient);
    }


    /**
     * Supprime un ingrédient par son identifiant.
     *
     * @param idIngredient l'identifiant de l'ingrédient à supprimer
     * @return une réponse avec un statut HTTP 204 (sans contenu)
     */
    @DeleteMapping("/{idIngredient}")
    ResponseEntity<Void> supprimer(@PathVariable("idIngredient") int idIngredient){
        log.info("Suppression de l'ingrédient avec l'ID : {}", idIngredient);
        ingredientService.supprimer(idIngredient);
        log.debug("Ingrédient avec l'ID {} supprimé avec succès.", idIngredient);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * Modifie partiellement un ingrédient existant.
     *
     * @param idIngredient l'identifiant de l'ingrédient à modifier
     * @param ingredientRequestDto les modifications à appliquer à l'ingrédient
     * @return une réponse contenant l'ingrédient modifié
     */
    @PatchMapping("/{idIngredient}")
    ResponseEntity<IngredientResponseDto> modifierPartiellement(@PathVariable("idIngredient") int idIngredient, @RequestBody IngredientRequestDto ingredientRequestDto) {
        log.info("Modification partielle de l'ingrédient avec l'ID : {}", idIngredient);
        IngredientResponseDto reponse = ingredientService.modifierPartiellement(idIngredient, ingredientRequestDto);
        log.debug("Ingrédient modifié : {}", reponse);
     return ResponseEntity.ok(reponse);
    }
    }


