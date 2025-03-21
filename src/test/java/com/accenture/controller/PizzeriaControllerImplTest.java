package com.accenture.controller;

import com.accenture.model.Tailles;
import com.accenture.service.dto.PizzaRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PizzeriaControllerImplTest {
    private static final String PIZZA_N_EXISTE_PAS = "La pizza n'existe pas";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testDeletePizzaOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pizzas/1")).andExpect(status().isNoContent());
    }


    @Test
    void testPostPizzaAvecObjet() throws Exception {
        PizzaRequestDto pizzaRequestDto = creerPizza();
        mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizzaRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.startsWith("http://localhost/pizzas/")))
                .andExpect(header().string("Location", "http://localhost/pizzas/3"));

    }

    @Test
    void testTrouverOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nom").value("Chèvre_miel"))
                .andExpect(jsonPath("$.ingredients[*].nom").value(Matchers.hasItem(Matchers.containsString("basilic"))))
                .andExpect(jsonPath("$.tarifs.MOYENNE").value(15.3));
    }

    @Test
    void testPostPizzaPasOK() throws Exception {
        PizzaRequestDto pizza = creerPizzaNomNull();

        mockMvc.perform(MockMvcRequestBuilders.post("/pizzas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pizza)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation")).andExpect(jsonPath("$.message").value("Le nom est obligatoire"));
    }

    @Test
    void testTrouverTous() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2)).andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].nom").value("Margherita")).andExpect(jsonPath("$[1].nom").value("Chèvre_miel"));
    }

    @Test
    void testTrouverPizzaPasOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/121")).andExpect(status().isNotFound()).andExpect(jsonPath("$.type").value("Erreur fonctionnelle")).andExpect(jsonPath("$.message").value(PIZZA_N_EXISTE_PAS));
    }

    @Test
    void testDeletePizzaPasOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/pizzas/115")).andExpect(status().isNotFound()).andExpect(jsonPath("$.type").value("Erreur fonctionnelle")).andExpect(jsonPath("$.message").value(PIZZA_N_EXISTE_PAS));
    }

    @Test
    void testTrouverParNomOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/search?nom=Margherita"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(4));
    }

    @Test
    void testTrouverPizzaParNomPasOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/pizzas/search?nom=iklzh")).andExpect(status().isNotFound()).andExpect(jsonPath("$.type").value("Erreur fonctionnelle")).andExpect(jsonPath("$.message").value(PIZZA_N_EXISTE_PAS));
    }


    //    =====================================================================================================////
    //                                                  METHODES PRIVEES////
    //    =====================================================================================================


    private static PizzaRequestDto creerPizza() {
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        return new PizzaRequestDto("Margherita", List.of(1, 2), tarifs);
    }

    private static PizzaRequestDto creerPizzaNomNull() {
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        return new PizzaRequestDto(null, List.of(1, 2), tarifs);
    }

}