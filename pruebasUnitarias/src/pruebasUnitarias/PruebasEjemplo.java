package pruebasUnitarias;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PruebasEjemplo {

	    /*
	       ESCENARIO 1: Arreglo solo positivo
	     */
	    @Test
	    public void testFindMax_AllPositive() {
	        int[] arr = {1, 3, 4, 2};
	        int result = Calculation.findMax(arr);

	        // El mayor valor del arreglo es 4
	        assertEquals(4, result);
	    }

	    /* 
	       ESCENARIO 2: Arreglo solo negativo
	       (este caso fallaba ANTES)
	        */
	    @Test
	    public void testFindMax_AllNegative() {
	        int[] arr = {-12, -1, -3, -4};
	        int result = Calculation.findMax(arr);

	        // El mayor valor es -1 (antes devolvía 0 )
	        assertEquals(-1, result);
	    }

	    /* ESCENARIO 3: Arreglo mixto */
	    @Test
	    public void testFindMax_MixedValues() {
	        int[] arr = {-5, 0, 7, -2, 3};
	        int result = Calculation.findMax(arr);

	        // El mayor valor es 7
	        assertEquals(7, result);
	    }
	}



