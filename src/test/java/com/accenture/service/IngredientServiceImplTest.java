package com.accenture.service;

import com.accenture.exception.IngredientException;
import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.model.Tailles;
import com.accenture.repository.IngredientDao;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testTrouverTous() {
        Mockito.when(ingredientDao.findAll()).thenReturn(List.of(creerIngredient()));
        List<Ingredient> liste = service.trouverTous();
        Assertions.assertNotNull(liste);
        Mockito.verify(ingredientDao).findAll();

    }

    @Test
    void testTrouverPasPresent() {
        int idIngredient = 1;
        Mockito.when(ingredientDao.findById(idIngredient)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(idIngredient));
        assertEquals("L'ingrédient n'existe pas", ex.getMessage());
        Mockito.verify(ingredientDao).findById(idIngredient);
    }

    @Test
    void testTrouverPresent() {
        int idIngredient = 1;
        Ingredient ingredient = creerIngredient();
        Mockito.when(ingredientDao.findById(idIngredient)).thenReturn(Optional.of(ingredient));

        Ingredient ingredientTrouve = assertDoesNotThrow(() -> service.trouver(idIngredient));
        assertSame(ingredient, ingredientTrouve);
        Mockito.verify(ingredientDao).findById(idIngredient);
    }

    @Test
    void testSupprimerIngredientNExistePas() {
        int idIngredient = 1;
        Mockito.when(ingredientDao.existsById(idIngredient)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.supprimer(idIngredient));
        assertEquals("L'ingrédient n'existe pas", ex.getMessage());

        Mockito.verify(ingredientDao).existsById(idIngredient);

    }

    @Test
    void testSupprimerIngredientOK() {
        int idIngredient = 1;
        Mockito.when(ingredientDao.existsById(idIngredient)).thenReturn(true);

        assertDoesNotThrow(() -> service.supprimer(idIngredient));

        Mockito.verify(ingredientDao).existsById(idIngredient);
        Mockito.verify(ingredientDao).deleteById(idIngredient);
    }



    private static Ingredient creerIngredient() {

       return new Ingredient("tomate", 10);
    }
}