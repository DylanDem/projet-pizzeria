package com.accenture.controller;

import com.accenture.model.Ingredient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IngredientControllerImplTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostIngrAvecObjet() throws Exception {
        Ingredient ingredient = new Ingredient("chèvre", 1);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(ingredient))).andExpect(status().isCreated()).andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.id").value(Matchers.not(0))).andExpect(jsonPath("$.nom").value("chèvre"));
    }

    @Test
    void testPostIngrPasOK() throws Exception {
        Ingredient ingredient = new Ingredient(null, 1);
        mockMvc.perform(MockMvcRequestBuilders.post("/ingredients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(ingredient))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.type").value("Erreur validation")).andExpect(jsonPath("$.message").value("Le nom des ingrédients est obligatoire"));
    }

    @Test
    void testPostIngredientAvecObjet() throws Exception {
        Ingredient ingredient = new Ingredient("Tomate",1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(Matchers.not(0)))
                .andExpect(jsonPath("$.nom").value("Tomate"));
    }

    @Test
    void testPostIngredientPasOK() throws Exception {
        Ingredient ingredient = new Ingredient(null,1);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/ingredients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("Erreur validation"))
                .andExpect(jsonPath("$.message").value("Le nom des ingrédients est obligatoire"));
    }

    @Test
    void testTrouverIngredientPasOK() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/ingredients/122"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("Erreur fonctionnelle"))
                .andExpect(jsonPath("$.message").value("L'ingrédient n'existe pas"));

    }


}