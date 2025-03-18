package com.accenture.controller;

import com.accenture.model.Pizza;
import com.accenture.service.PizzaService;
import com.accenture.service.dto.PizzaResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    ResponseEntity<Pizza> ajouter(@RequestBody Pizza pizza) {
        Pizza ajoute = pizzaService.ajouter(pizza);
        log.info("ajoute : " + ajoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(ajoute);
    }


}