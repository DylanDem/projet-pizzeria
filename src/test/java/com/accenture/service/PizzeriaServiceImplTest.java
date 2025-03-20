package com.accenture.service;

import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.model.Tailles;
import com.accenture.repository.IngredientDao;
import com.accenture.repository.PizzaDao;
import com.accenture.service.dto.PizzaRequestDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
    @Mock
    IngredientDao ingredientDao;
    @InjectMocks
    private PizzaServiceImpl service;
    @Mock
    private PizzaDao pizzaDao;

    private static PizzaRequestDto creerPizza() {
        List<Integer> listIngredient = List.of(1, 2);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        return new PizzaRequestDto("Margherita", listIngredient, tarifs);
    }

    private static PizzaRequestDto creerPizza2() {
        List<Integer> listIngredient = List.of(3, 4);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 11.50);
        tarifs.put(Tailles.MOYENNE, 16.25);
        tarifs.put(Tailles.GRANDE, 21.30);
        return new PizzaRequestDto("La Bretagnarde", listIngredient, tarifs);
    }

    @Test
    void testAjouterNull() {
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(null));
        Assertions.assertEquals("Merci de saisir votre pizza", pe.getMessage());
    }

    @Test
    void testAjouterPizzaSansIngredient() {
        PizzaRequestDto pizzaRequestDto = creerPizzaSansIngredient();
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizzaRequestDto));
        assertEquals("Les ingrédients sont obligatoires", pe.getMessage());
    }

    @Test
    void testAjouterPizzaSansNom() {
        PizzaRequestDto pizzaRequestDto = creerPizzaSansNom();
        PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizzaRequestDto));
        assertEquals("Le nom est obligatoire", pe.getMessage());
    }

    @Test
    void testAjouterPizzaOK() {
        PizzaRequestDto pizzaRequestDto = creerPizza();
        Pizza pizza = service.toPizza(pizzaRequestDto);
        Mockito.when(ingredientDao.findAllById(ArgumentMatchers.any())).thenReturn(List.of());
        Mockito.when(pizzaDao.save(pizza)).thenReturn(pizza);
        assertDoesNotThrow(() -> service.ajouter(pizzaRequestDto));
        Mockito.verify(pizzaDao).save(pizza);
    }

    @Test
    void testTrouverPresent() {
        int idPizza = 1;
        PizzaRequestDto pizzaRequestDto = creerPizza();
        Pizza pizza = service.toPizza(pizzaRequestDto);
        Mockito.when(pizzaDao.findById(idPizza)).thenReturn(Optional.of(pizza));
        Pizza trouve = assertDoesNotThrow(() -> service.trouver(idPizza));
        assertSame(pizza, trouve);
        Mockito.verify(pizzaDao).findById(idPizza);

    }

    @Test
    void testTrouverTous() {
        Map<Tailles, Double> tarifsPizza1 = Map.of(Tailles.PETITE, 8.0, Tailles.GRANDE, 12.0);
        Map<Tailles, Double> tarifsPizza2 = Map.of(Tailles.PETITE, 9.0, Tailles.GRANDE, 13.0);
        List<Ingredient> ingredientsPizza1 = List.of(new Ingredient("tomate", 2), new Ingredient("Mozzarella", 3));
        List<Ingredient> ingredientsPizza2 = List.of(new Ingredient("Pepperoni", 45), new Ingredient("Fromage", 2));

        Pizza pizza1 = new Pizza("Test", tarifsPizza1, ingredientsPizza1);
        Pizza pizza2 = new Pizza("Test2", tarifsPizza2, ingredientsPizza2);

        List<Pizza> pizzasMock = List.of(pizza1, pizza2);

        Mockito.when(pizzaDao.findAll()).thenReturn(pizzasMock);
        List<Pizza> liste = service.trouverTous();
        List<PizzaRequestDto> listeAttendue = List.of(creerPizza(), creerPizza2());
        List<PizzaRequestDto> listeObtenue = List.of(creerPizza(), creerPizza2());
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

    @Test
    void testAjouterNomNull() {
        List<Integer> listIngredient = List.of(1, 2);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto(null, listIngredient, tarifs);


        PizzaException pizzaException = assertThrows(PizzaException.class, () -> service.ajouter(pizzaRequestDto));
        assertEquals("Le nom est obligatoire", pizzaException.getMessage());


    }

    @Test
    void testAjouterNomVide() {
        List<Integer> listIngredient = List.of(1, 2);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto("  \n  ", listIngredient, tarifs);


        PizzaException pizzaException = assertThrows(PizzaException.class, () -> service.ajouter(pizzaRequestDto));
        assertEquals("Le nom est obligatoire", pizzaException.getMessage());


    }


    //    =====================================================================================================////
    //                                                  METHODES PRIVEES////
    //    =====================================================================================================


    private PizzaRequestDto creerPizzaSansIngredient() {
        List<Integer> listIngredient = null;
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        return new PizzaRequestDto("Margherita", listIngredient, tarifs);
    }

    private PizzaRequestDto creerPizzaSansNom() {
        List<Integer> listIngredient = List.of(1, 2);
        Map<Tailles, Double> tarifs = new HashMap<>();
        tarifs.put(Tailles.PETITE, 10.50);
        tarifs.put(Tailles.MOYENNE, 15.25);
        tarifs.put(Tailles.GRANDE, 20.30);
        return new PizzaRequestDto(null, listIngredient, tarifs);
    }

}