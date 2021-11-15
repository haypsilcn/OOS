package bank;

public class OutcomingTransfer extends Transfer{

    /**
     * Konstruktor mit drei Attributen
     *
     * @param newDate        Wert für date
     * @param newDescription Wert für description
     * @param newAmount      Wert für amount
     */
    public OutcomingTransfer(String newDate, String newDescription, double newAmount) {
        super(newDate, newDescription, newAmount);
    }

    /**
     * Konstruktor mit aller Attributen
     *
     * @param newDate        Wert für date
     * @param newDescription Wert für description
     * @param newAmount      Wert für amount
     * @param newSender      Wert für sender
     * @param newRecipient   Wert für recipient
     */
    public OutcomingTransfer(String newDate, String newDescription, double newAmount, String newSender, String newRecipient) {
        super(newDate, newDescription, newAmount, newSender, newRecipient);
    }

    /**
     * Copy Constructor
     *
     * @param newTransfer neue Objekt festzulegen
     */
    public OutcomingTransfer(Transfer newTransfer) {
        super(newTransfer);
    }

    @Override
    public double calculate() {
        return -amount;
    }
}
