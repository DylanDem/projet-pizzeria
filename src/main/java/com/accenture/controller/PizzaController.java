package com.accenture.controller;

import com.accenture.model.Pizza;
import com.accenture.service.PizzaService;
import com.accenture.service.dto.PizzaRequestDto;
import com.accenture.service.dto.PizzaResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


/**
 * Contrôleur REST pour gérer les opérations sur les pizzas.
 */
@RestController
@Slf4j
@RequestMapping("/pizzas")
public class PizzaController {
    private PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }


    /**
     * Ajoute une nouvelle pizza.
     *
     * @param pizzaRequestDto les données de la pizza à ajouter
     * @return une réponse avec l'emplacement de la nouvelle pizza créée
     */
    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody @Valid PizzaRequestDto pizzaRequestDto) {
        log.info("Ajout d'une nouvelle pizza : {}", pizzaRequestDto);
        PizzaResponseDto pizzaRetour = pizzaService.ajouter(pizzaRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pizzaRetour.id())
                .toUri();
        log.debug("Nouvelle pizza créée avec l'ID : {}", pizzaRetour.id());
        return ResponseEntity.created(location).build();
    }


    /**
     * Récupère la liste de toutes les pizzas.
     *
     * @return une réponse contenant la liste des pizzas
     */
    @GetMapping
    ResponseEntity<List<Pizza>> trouverTous() {
        log.info("Récupération de toutes les pizzas.");
        return ResponseEntity.ok(pizzaService.trouverTous());
    }


    /**
     * Récupère une pizza par son id.
     *
     * @param idPizza l'identifiant de la pizza
     * @return une réponse contenant la pizza correspondante
     */
    @GetMapping("/{idPizza}")
    ResponseEntity<Pizza> trouver(@PathVariable("idPizza") int idPizza) {
        log.info("Recherche de la pizza avec l'ID : {}", idPizza);
        Pizza pizza = pizzaService.trouver(idPizza);
        log.debug("Pizza trouvée : {}", pizza);
        return ResponseEntity.ok(pizza);
    }


    /**
     * Supprime une pizza par son id.
     *
     * @param idPizza l'identifiant de la pizza à supprimer
     * @return une réponse avec un statut 204 (sans contenu)
     */
    @DeleteMapping("/{idPizza}")
    ResponseEntity<Void> supprimer(@PathVariable("idPizza") int idPizza) {
        log.info("Suppression de la pizza avec l'ID : {}", idPizza);
        pizzaService.supprimer(idPizza);
        log.debug("Pizza avec l'ID {} supprimée avec succès.", idPizza);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}