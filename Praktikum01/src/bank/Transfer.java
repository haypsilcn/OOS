package bank;

// repräsentiere Überweisungen
public class Transfer {

    private String date;            // stellt den Zeitpunkt einer Überweisung mit dem Format als DD.MM.YYYY dar
    private String description;     // eine zusätzliche Beschreibung des Vorgangs
    private String sender;          // wer hat die Überweisung gemacht
    private String recipient;       // wer hat die Überweisung bekommen
    private double amount;          // stellt die Geldmenge einer Überweisung dar

    // GETTER für date
    public String getDate() {
        return this.date;
    }
    // SETTER für date
    public void setDate(String newDate) {
        this.date = newDate;
    }

    // GETTER für description
    public String getDescription() {
        return this.description;
    }
    // SETTER für description
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    // GETTER für sender
    public String getSender() {
        return this.sender;
    }
    // SETTER für sender
    public void setSender(String newSender) {
        this.sender = newSender;
    }

    // GETTER für recipient
    public String getRecipient() {
        return this.recipient;
    }
    // SETTER für recipient
    public void setRecipient(String newRecipient) {
        this.recipient = newRecipient;
    }

    // GETTER für amount
    public double getAmount() {
        return this.amount;
    }
    // SETTER für amount
    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    // Konstruktor für 3 Attribute: date, description, amount
    public Transfer (String newDate, String newDescription, double newAmount) {
        this.date = newDate;
        this.description = newDescription;
        this.amount = newAmount;
    }

    // Konstruktor für alle Attribute mit der Wiederverwendung des 1. Konstruktor
    public Transfer (String newDate, String newDescription, double newAmount, String newSender, String newRecipient) {
        this(newDate,newDescription,newAmount);
        this.sender = newSender;
        this.recipient = newRecipient;
    }

    // Copy-Konstruktor
    public Transfer(Transfer newTransfer) {
        this(newTransfer.date, newTransfer.description, newTransfer.amount, newTransfer.sender, newTransfer.recipient);
    }

    // Gibt den Inhalt aller Klassenattribute auf der Konsole aus
    public void printObject() {
        System.out.println("Date: " + date + "\nDescription: " + description + "\nAmount: " + amount + " €\nSender: " + sender + "\nRecipient: " + recipient + "\n");
    }
}
