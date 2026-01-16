package pruebasUnitarias;

import static org.junit.Assert.assertEquals;

public class Test {

    @org.junit.Test
    public void testFindMax() {
        // 1) positivos
        assertEquals(4, Calculation.findMax(new int[] {1, 3, 4, 2}));

        // 2) negativos
        assertEquals(-1, Calculation.findMax(new int[] {-12, -1, -3, -4, -2}));

        // 3) mixto
        assertEquals(10, Calculation.findMax(new int[] {-5, 10, -3, 7, -1}));
    }
}
