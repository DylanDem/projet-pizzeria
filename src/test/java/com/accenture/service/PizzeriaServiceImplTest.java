package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.model.Tailles;
import com.accenture.repository.PizzaDao;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PizzeriaServiceImplTest {
    private static final String PIZZA_N_EXISTE_PAS = "La pizza n'existe pas";
    @InjectMocks
    private PizzaServiceImpl service;
    @Mock
    private PizzaDao pizzaDao;

    @Test
    void testAjouterNull() {
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(null));
        Assertions.assertEquals("Merci de saisir votre pizza", pe.getMessage());
    }

    @Test
    void testAjouterPizzaSansIngredients() {
        Pizza pizza = creerPizza();
        pizza.setIngredients(null);
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizza));
        assertEquals("Les ingrédients sont obligatoires", pe.getMessage());
    }

    @Test
    void testAjouterPizzaSansTarifs() {
        Pizza pizza = creerPizza();
        pizza.setTarifs(null);
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizza));
        assertEquals("Le tarif est obligatoire", pe.getMessage());
    }

    @Test
    void testAjouterPizzaSansNom() {
        Pizza pizza = creerPizza();
        pizza.setNom(null);
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizza));
        assertEquals("Le nom est obligatoire", pe.getMessage());
    }

    @Test
    void testAjouterPizzaOK() {
        Pizza pizza = creerPizza();
        Pizza pizzaApresSave = creerPizza();
        pizzaApresSave.setId(1);
        Mockito.when(pizzaDao.save(pizza)).thenReturn(pizzaApresSave);
        Pizza pizzaEnreg = assertDoesNotThrow(() -> service.ajouter(pizza));
        Assertions.assertSame(pizzaApresSave, pizzaEnreg);
        Mockito.verify(pizzaDao).save(pizza);
    }

    @Test
    void testTrouverTous() {
        Mockito.when(pizzaDao.findAll()).thenReturn(List.of(creerPizza(), creerPizza2()));
        List<Pizza> liste = service.trouverTous();
        List<Pizza> listeAttendue = List.of(creerPizza(), creerPizza2());
        List<Pizza> listeObtenue = List.of(creerPizza(), creerPizza2());
        assertIterableEquals(listeAttendue, listeObtenue);
        assertNotNull(liste);
        Mockito.verify(pizzaDao).findAll();
    }

    @Test
    void testTrouverPasPresent() {
        int idPizza = 1;
        Mockito.when(pizzaDao.findById(idPizza)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.trouver(idPizza));
        assertEquals("La pizza n'existe pas", ex.getMessage());
        Mockito.verify(pizzaDao).findById(idPizza);
    }

    @Test
    void testTrouverPresent() {
        int idPizza = 1;
        Pizza pizza = creerPizza();
        Mockito.when(pizzaDao.findById(idPizza)).thenReturn(Optional.of(pizza));
        Pizza trouve = assertDoesNotThrow(() -> service.trouver(idPizza));
        assertSame(pizza, trouve);
        Mockito.verify(pizzaDao).findById(idPizza);
    }

    @Test
    void testSupprimerPasOk() {
        int idPizza = 1;
        Mockito.when(pizzaDao.existsById(idPizza)).thenReturn(false);
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.supprimer(idPizza));
        assertEquals(PIZZA_N_EXISTE_PAS, ex.getMessage());
        Mockito.verify(pizzaDao).existsById(idPizza);
    }

    @Test
    void testSupprimerOk() {
        int idPizza = 1;
        Mockito.when(pizzaDao.existsById(idPizza)).thenReturn(true);
        assertDoesNotThrow(() -> service.supprimer(idPizza));
        Mockito.verify(pizzaDao).existsById(idPizza);
        Mockito.verify(pizzaDao).deleteById(idPizza);
    }

    //    =====================================================================================================////                                        METHODES PRIVEES////    =====================================================================================================

 private static Pizza creerPizza() {
        Ingredient ingredient = new Ingredient("tomate", 10);
        Ingredient ingredient2 = new Ingredient("basilic", 1);
        List<Ingredient> listIngredient = List.of(ingredient, ingredient2);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        return new Pizza("Margherita", tarifs, listIngredient);    }

    private static Pizza creerPizza2() {
        Ingredient ingredient = new Ingredient("cornichon", 10);
        Ingredient ingredient2 = new Ingredient("ananas", 14);
        List<Ingredient> listIngredient = List.of(ingredient, ingredient2);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 11.50);
        tarifs.put(Tailles.MOYENNE, 16.25);
        tarifs.put(Tailles.GRANDE, 21.30);
        return new Pizza("La Bretagnarde", tarifs, listIngredient);    }

}