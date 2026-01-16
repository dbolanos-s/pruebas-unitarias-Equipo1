package tallerpruebas ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Assume;

public class EmployeeTest {

    private static final float RMU = 386.0f;
    private static final float DELTA = 0.001f;

    private int currentMonth() {
        Date date = new Date();
        LocalDate localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate.getMonthValue();
    }

    private float salarioSegunMoneda(float salary, String currency) {
        // Misma lógica del código del profe
        if (currency == "USD") return salary;
        return salary * 0.95f;
    }

    /* =========================
       PRUEBAS PARA CalculateYearBonus()
       ========================= */

    @org.junit.Test
    public void yearBonus_worker_devuelve_RMU() {
        Employee emp = new Employee(1000f, "USD", 200f, EmployeeType.Worker);
        assertEquals(RMU, emp.CalculateYearBonus(), DELTA);
    }

    @org.junit.Test
    public void yearBonus_supervisor_USD() {
        float salary = 1000f;
        Employee emp = new Employee(salary, "USD", 200f, EmployeeType.Supervisor);
        float esperado = salary + RMU * 0.5f;
        assertEquals(esperado, emp.CalculateYearBonus(), DELTA);
    }

    @org.junit.Test
    public void yearBonus_manager_monedaNoUSD_aplica_descuento_5() {
        float salary = 1000f;
        Employee emp = new Employee(salary, "EUR", 200f, EmployeeType.Manager);
        float esperado = (salary * 0.95f) + RMU * 1.0f;
        assertEquals(esperado, emp.CalculateYearBonus(), DELTA);
    }

    /* =========================
       PRUEBAS PARA cs() (DEPENDE DEL MES ACTUAL)
       ========================= */

    @org.junit.Test
    public void cs_worker_USD_mesActual() {
        float salary = 1000f;
        Employee emp = new Employee(salary, "USD", 200f, EmployeeType.Worker);

        int month = currentMonth();
        float salario = salarioSegunMoneda(salary, "USD");
        float decimo2Meses = (RMU / 12f) * 2f;

        float esperado = (month % 2 == 0) ? salario : (salario + decimo2Meses);
        assertEquals(esperado, emp.cs(), DELTA);
    }

    @org.junit.Test
    public void cs_supervisor_USD_mesActual_incluye_bono_y_decimo_si_impar() {
        float salary = 1000f;
        float bonus = 200f;
        Employee emp = new Employee(salary, "USD", bonus, EmployeeType.Supervisor);

        int month = currentMonth();
        float salario = salarioSegunMoneda(salary, "USD");
        float valueS = salario + (bonus * 0.35f);
        float decimo2Meses = (RMU / 12f) * 2f;

        float esperado = (month % 2 == 0) ? valueS : (valueS + decimo2Meses);
        assertEquals(esperado, emp.cs(), DELTA);
    }

    @org.junit.Test
    public void cs_manager_monedaNoUSD_mesActual_descuento_bono_y_decimo() {
        float salary = 1000f;
        float bonus = 200f;
        Employee emp = new Employee(salary, "GBP", bonus, EmployeeType.Manager);

        int month = currentMonth();
        float salario = salarioSegunMoneda(salary, "GBP"); // 0.95
        float valueM = salario + (bonus * 0.7f);
        float decimo2Meses = (RMU / 12f) * 2f;

        float esperado = (month % 2 == 0) ? valueM : (valueM + decimo2Meses);
        assertEquals(esperado, emp.cs(), DELTA);
    }

    /* =========================
       ASSUMPTION: DEMOSTRAR CONDICIÓN
       (y también evidencia el bug de comparar currency con ==)
       ========================= */

    @org.junit.Test
    public void assumption_y_bug_currency_USD_con_newString() {
        float salary = 1000f;

        // Este test solo lo evaluamos cuando el mes es PAR
        // para que cs() sea solo salario (sin décimo)
        Assume.assumeTrue(currentMonth() % 2 == 0);

        Employee empLiteral = new Employee(salary, "USD", 0f, EmployeeType.Worker);
        Employee empNewStr  = new Employee(salary, new String("USD"), 0f, EmployeeType.Worker);

        // Con literal "USD" debe dar salary
        assertEquals(salary, empLiteral.cs(), DELTA);

        // Con new String("USD") el código entra al else y descuenta 5% -> evidencia el defecto del ==
        assertNotEquals(salary, empNewStr.cs(), DELTA);
        assertEquals(salary * 0.95f, empNewStr.cs(), DELTA);
    }
}
