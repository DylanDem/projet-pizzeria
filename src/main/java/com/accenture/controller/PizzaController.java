package com.accenture.controller;

import com.accenture.model.Pizza;
import com.accenture.service.PizzaService;
import com.accenture.service.dto.PizzaRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pizzas")
public class PizzaController {
    private PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

//    @PostMapping
//    ResponseEntity<Void> ajouter(@RequestBody PizzaRequestDto pizzaRequestDto) {
//        PizzaRequestDto ajoute = pizzaService.ajouter(pizza);
//        log.info("ajoute : " + ajoute);
//        return ResponseEntity.status(HttpStatus.CREATED).body(ajoute);
//    }
    //TODO REFAIRE CETTE METHODE

    @GetMapping
    ResponseEntity<List<Pizza>> trouverTous() {
        return ResponseEntity.ok(pizzaService.trouverTous());
    }

    @GetMapping("/{idPizza}")
    ResponseEntity<Pizza> trouver(@PathVariable("idPizza") int idPizza) {
        Pizza pizza = pizzaService.trouver(idPizza);
        return ResponseEntity.ok(pizza);
    }

    @DeleteMapping("/{idPizza}")
    ResponseEntity<Void> supprimer(@PathVariable("idPizza") int idPizza) {
        pizzaService.supprimer(idPizza);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}