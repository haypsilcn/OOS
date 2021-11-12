package bank;

public class Transfer extends Transaction {

    /**
     * Name des Absenders, der die Überweisung gemacht hat
     */
    private String sender;

    /**
     * Name des Empfängers, der die Überweisung bekommen hat
     */
    private String recipient;

    /**
     * Legt das Attribut sender fest
     * @param newSender neuer Wert für sender
     */
    public void setSender(String newSender) {
        this.sender = newSender;
    }

    /**
     * @return den aktuellen Wert von sender
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * Legt das Attribut recipient fest
     * @param newRecipient neuer Wert von recipient
     */
    public void setRecipient(String newRecipient) {
        this.recipient = newRecipient;
    }

    /**
     * @return den aktuellen Wert von recipient
     */
    public String getRecipient() {
        return this.recipient;
    }

    /**
     * Konstruktor mit drei Attributen
     * @param newDate Wert für date
     * @param newDescription Wert für description
     * @param newAmount Wert für amount
     */
    public Transfer(String newDate, String newDescription, double newAmount) {
        super(newDate, newDescription, newAmount);
    }

    /**
     * Konstruktor mit drei Attributen
     * @param newDate Wert für date
     * @param newDescription Wert für description
     * @param newAmount Wert für amount
     * @param newSender Wert für sender
     * @param newRecipient Wert für recipient
     */
    public Transfer(String newDate, String newDescription, double newAmount, String newSender, String newRecipient) {
        this(newDate, newDescription, newAmount);
        this.sender = newSender;
        this.recipient = newRecipient;
    }

    /**
     * Copy Constructor
     * @param newTransfer neue Objekt festzulegen
     */
    public Transfer(Transfer newTransfer) {
        this(newTransfer.date, newTransfer.description, newTransfer.amount, newTransfer.sender, newTransfer.recipient);
    }

    /**
     * @return unveränderter Wert von amount
     */
    @Override
    public double calculate() {
        return amount;
    }

    /**
     * @return den Inhalt aller Klassenattribute
     */
    @Override
    public String toString() {
        return super.toString() + ", Sender: " + sender + ", Recipient: " + recipient + "\n";
    }

    /**
     * Zwei Objekte der Klasse Transfer zum Vergleich
     * @param obj das zu vergleichende Objekt
     * @return true, wenn beide sind gleich sonst false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transfer transfer)
            return (super.equals(transfer) && this.sender.equals(transfer.sender) && this.recipient.equals(transfer.recipient));
        return false;
    }
}