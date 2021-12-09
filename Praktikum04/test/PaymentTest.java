import bank.Payment;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class PaymentTest {

    static Payment payment1;
    static Payment payment2;
    static Payment copyPayment;

    @BeforeAll
    public static void setUp() {
        System.out.println("Set up for Payment objects");
        payment1 = new Payment("12.03.2008", "Payment 01", 321);
        payment2 = new Payment("23.09.1897", "Payment 02", -2500, 0.8, 0.5);
        copyPayment = new Payment(payment2);
    }


    @Test
    public void threeAttributesConstructorTest() {
        assertEquals("12.03.2008", payment1.getDate());
        assertEquals("Payment 01", payment1.getDescription());
        assertEquals(321, payment1.getAmount());
    }

    @Test
    public void allAttributesConstructorTest() {
        assertEquals("23.09.1897", payment2.getDate());
        assertEquals("Payment 02", payment2.getDescription());
        assertEquals(-2500, payment2.getAmount());
        assertEquals(0.8, payment2.getIncomingInterest());
        assertEquals(0.5, payment2.getOutgoingInterest());
    }

    @Test
    public void copyConstructorTest() {
        assertEquals(payment2.getDate(), copyPayment.getDate());
        assertEquals(payment2.getDescription(), copyPayment.getDescription());
        assertEquals(payment2.getAmount(), copyPayment.getAmount());
        assertEquals(payment2.getIncomingInterest(), copyPayment.getIncomingInterest());
        assertEquals(payment2.getOutgoingInterest(), copyPayment.getOutgoingInterest());
    }


    @Test
    public void calculateIncomingInterestTest() {
        double expected = payment1.getAmount() - payment1.getIncomingInterest() * payment1.getAmount();
        assertTrue(payment1.getAmount() >= 0);
        assertEquals(expected, payment1.calculate());
    }

    @Test
    public void calculateOutgoingInterestTest() {
        double expected = payment2.getAmount() + payment2.getOutgoingInterest() * payment2.getAmount();
        assertTrue(payment2.getAmount() < 0);
        assertEquals(expected, payment2.calculate());
    }

    @Test
    public void equalsTrueTest() {
        assertEquals(payment2, copyPayment);
    }

    @Test
    public void equalsFalseTest() {
        assertNotEquals(payment1, payment2);
    }

    @Test
    public void toStringTester() {
        String string = "Date: 23.09.1897, Description: Payment 02, Amount: -3750.0 €, Incoming Interest: 0.8, Outgoing Interest: 0.5\n";
        assertEquals(string, payment2.toString());
    }

}
