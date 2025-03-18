package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.model.Tailles;
import com.accenture.repository.IngredientDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @InjectMocks
    private IngredientServiceImpl service;

    @Mock
    private IngredientDao ingredientDao;


    @Test
    void testAjouterNull(){

        IngredientException ie = Assertions.assertThrows(IngredientException.class,() -> service.ajouterIngredient(null));
        Assertions.assertEquals("Merci de saisir un ingrédient", ie.getMessage());

    }

    @Test
    void testAjouterIngredientSansQuantite(){

        Ingredient ingredient = creerIngredient();
        ingredient.setQuantite(null);

        IngredientException ie = Assertions.assertThrows(IngredientException.class, () -> service.ajouterIngredient(ingredient));
        assertEquals("La quantité des ingrédients est obligatoire", ie.getMessage());

    }


    @Test
    void testAjouterIngredientSansNom(){

        Ingredient ingredient = creerIngredient();
        ingredient.setNom(null);

        IngredientException ie = Assertions.assertThrows(IngredientException.class, () -> service.ajouterIngredient(ingredient));
        assertEquals("Le nom des ingrédients est obligatoire", ie.getMessage());

    }


    @Test
    void testAjouterIngredientOK(){
      Ingredient ingredient = creerIngredient();
        Ingredient ingredientApresSave = creerIngredient();

        ingredientApresSave.setId(1);
        Mockito.when(ingredientDao.save(ingredient)).thenReturn(ingredientApresSave);
        Ingredient ingredientEnreg = assertDoesNotThrow(() -> service.ajouterIngredient(ingredient));
        Assertions.assertSame(ingredientApresSave, ingredientEnreg);

        Mockito.verify(ingredientDao).save(ingredient);
    }



    private static Ingredient creerIngredient() {

       return new Ingredient("tomate", 10);
    }
}