package bank;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TransferTest {

    static Transfer incomingTransfer;
    static Transfer outgoingTransfer;
    static Transfer copyTransfer;

    @BeforeAll
    public static void setUp() {
        incomingTransfer = new IncomingTransfer("03.03.2000", "IncomingTransfer from Molziles to Elixir; 80", 80);
        outgoingTransfer = new OutgoingTransfer("30.07.2020", "OutgoingTransfer to Hagen", 1890, "Elixir", "Hagen");
        copyTransfer = new OutgoingTransfer(outgoingTransfer);
    }


    @Test
    public void threeAttributesConstructorTest() {
        assertEquals("03.03.2000", incomingTransfer.getDate());
        assertEquals("IncomingTransfer from Molziles to Elixir; 80", incomingTransfer.getDescription());
        assertEquals(80, incomingTransfer.getAmount());

    }

    @Test
    public void allAttributesConstructorTest() {
        assertEquals("30.07.2020", outgoingTransfer.getDate());
        assertEquals("OutgoingTransfer to Hagen", outgoingTransfer.getDescription());
        assertEquals(1890, outgoingTransfer.getAmount());
        assertEquals("Elixir", outgoingTransfer.getSender());
        assertEquals("Hagen", outgoingTransfer.getRecipient());
    }

    @Test
    public void copyConstructorTester() {
        assertEquals(outgoingTransfer.getAmount(), copyTransfer.getAmount());
        assertEquals(outgoingTransfer.getDate(), copyTransfer.getDate());
        assertEquals(outgoingTransfer.getRecipient(), copyTransfer.getRecipient());
        assertEquals(outgoingTransfer.getAmount(), copyTransfer.getAmount());
        assertEquals(outgoingTransfer.getSender(), copyTransfer.getSender());
        assertEquals(outgoingTransfer.getDescription(), copyTransfer.getDescription());
    }

    @Test
    public void calculateIncomingTransferTest() {
        assertInstanceOf(IncomingTransfer.class, incomingTransfer);
        assertEquals(incomingTransfer.getAmount(), incomingTransfer.calculate());
    }

    @Test
    public void calculateOutgoingTransferTest() {
        assertInstanceOf(OutgoingTransfer.class, outgoingTransfer);
        assertEquals(-outgoingTransfer.getAmount(), outgoingTransfer.calculate());
    }

    @Test
    public void equalsTrueTest() {
        assertEquals(outgoingTransfer, copyTransfer);
    }

    @Test
    public void equalsFalseTest() {
        assertNotEquals(incomingTransfer, outgoingTransfer);
    }

    @Test
    public void toStringTester() {
        assertEquals("Date: 30.07.2020, Description: OutgoingTransfer to Hagen, Amount: -1890.0 €, Sender: Elixir, Recipient: Hagen\n", outgoingTransfer.toString());
    }
}
