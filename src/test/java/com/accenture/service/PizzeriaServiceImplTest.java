package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.model.Tailles;
import com.accenture.repository.PizzaDao;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PizzeriaServiceImplTest {

    @InjectMocks
    private PizzaServiceImpl service;

    @Mock
    private PizzaDao pizzaDao;


    @Test
    void testAjouterNull(){

        PizzaException pe = Assertions.assertThrows(PizzaException.class,() -> service.ajouter(null));
        Assertions.assertEquals("Merci de saisir votre pizza", pe.getMessage());

    }


    @Test
    void testAjouterPizzaSansIngredients(){

        Pizza pizza = creerPizza();
        pizza.setIngredients(null);

        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizza));
        assertEquals("Les ingrédients sont obligatoires", pe.getMessage());

    }

//    @Test
//    void testAjouterPizzaSansTarifs() {
//
//        Pizza pizza = creerPizza();
//        pizza.setTarifs(null);
//
//        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizza));
//        assertEquals("Le tarif est obligatoire", pe.getMessage());
//
//    }


    @Test
    void testAjouterPizzaSansNom(){

        Pizza pizza = creerPizza();
        pizza.setNom(null);

        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizza));
        assertEquals("Le nom est obligatoire", pe.getMessage());

    }


    @Test
    void testAjouterPizzaOK(){
        Pizza pizza = creerPizza();
        Pizza pizzaApresSave = creerPizza();

        pizzaApresSave.setId(1);
        Mockito.when(pizzaDao.save(pizza)).thenReturn(pizzaApresSave);
        Pizza pizzaEnreg = assertDoesNotThrow(() -> service.ajouter(pizza));
        Assertions.assertSame(pizzaApresSave, pizzaEnreg);

        Mockito.verify(pizzaDao).save(pizza);
    }



    private static Pizza creerPizza() {

        Ingredient ingredient = new Ingredient("tomate", 10);
        Ingredient ingredient2 = new Ingredient("basilic", 1);
        List<Ingredient> listIngredient = List.of(ingredient, ingredient2);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        return new Pizza("Margherita", tarifs, listIngredient);

    }

}