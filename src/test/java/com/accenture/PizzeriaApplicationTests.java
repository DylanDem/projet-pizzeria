package com.accenture;

import com.accenture.exception.PizzaException;
import com.accenture.model.Pizza;
import com.accenture.service.PizzaServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PizzeriaApplicationTests {

	@InjectMocks
	private PizzaServiceImpl service;


	@Test
	void testAjouterNull(){
		System.out.println(service);
		PizzaException pe = Assertions.assertThrows(PizzaException.class,() -> service.ajouter(null));
		Assertions.assertEquals("Merci de saisir votre pizza", pe.getMessage());

	}



}
