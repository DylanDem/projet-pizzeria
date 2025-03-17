package com.accenture;

import com.accenture.exception.PizzaException;
import com.accenture.model.Ingredient;
import com.accenture.model.Pizza;
import com.accenture.model.Tailles;
import com.accenture.repository.PizzaDao;
import com.accenture.service.PizzaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class PizzeriaApplicationTests {

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

	@Test
	void testAjouterPizzaSansTaille(){

		Pizza pizza = creerPizza();
		pizza.setTailles(null);

		PizzaException pe = Assertions.assertThrows(PizzaException.class, () -> service.ajouter(pizza));
		assertEquals("La taille est obligatoire", pe.getMessage());

	}


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

		Ingredient ingredient = new Ingredient();
		ingredient.setChorizo("Chorizo");
		ingredient.setTomate("Tomate");
		return new Pizza("Margherita", Tailles.PETITE, 15 , ingredient);
	}


}
