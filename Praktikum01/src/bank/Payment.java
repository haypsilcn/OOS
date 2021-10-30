package bank;


// repräsentiere Ein- und Auszahlungen
public class Payment {

    private String date;                // stellt den Zeitpunkt einer Ein- oder Auszahlung mit dem Format als DD.MM.YYYY dar
    private String description;         // eine zusätzliche Beschreibung des Vorgangs
    private double amount;              // stellt die Geldmenge dar, die positiv bei der Einzahlung oder negativ bei der Auszahlung ist
    private double incomingInterest;    // die Zinsen (positiver Wert in Prozent, 0 bis 1) bei einer Einzahlung anfallen
    private double outcomingInterest;   // die Zinsen (positiver Wert in Prozent, 0 bis 1) bei einer Auszahlung anfallen

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

    // GETTER für amount
    public double getAmount() {
        return this.amount;
    }
    // SETTER für amount
    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    // GETTER für incomingInterest
    public double getIncomingInterest() {
        return this.incomingInterest;
    }
    // SETTER für incomingInterest
    public void setIncomingInterest(double newIncomingInterest) {
        this.incomingInterest = newIncomingInterest;
    }

    // GETTER für outcomingInterest
    public double getOutcomingInterest() {
        return this.outcomingInterest;
    }
    // SETTER für outcomingInterest
    public void setOutcomingInterest(double newOutcomingInterest) {
        this.outcomingInterest = newOutcomingInterest;
    }

    // Konstruktor für 3 Attribute: date, amount, description
    public Payment(String newDate, String newDescription, double newAmount) {
        this.date = newDate;
        this.description = newDescription;
        this.amount =  newAmount;
    }

    // Konstruktor für alle Attribute mit der Wiederverwendung des 1. Konstruktor
    public Payment(String newDate, String newDescription, double newAmount, double newIncomingInterest, double newOutcomingInterest) {
        this(newDate, newDescription, newAmount);
        this.incomingInterest = newIncomingInterest;
        this.outcomingInterest = newOutcomingInterest;
    }

    // Copy-Konstruktor
    public Payment(Payment newPayment) {
       this(newPayment.date, newPayment.description, newPayment.amount, newPayment.incomingInterest, newPayment.outcomingInterest);
    }

    // Gibt den Inhalt aller Klassenattribute auf der Konsole aus
    public void printObject() {
        System.out.println("Date: " + date + "\nDescription: " + description + "\nAmount: " + amount + " €\nIncoming Interest: " + incomingInterest + " %\nOutcoming Interest: " + outcomingInterest + " %\n");
    }
}
