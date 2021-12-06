package bank;

public class Payment extends Transaction {

    /**
     * die Zinsen (positiver Wert in Prozent, 0 bis 1) bei einer Einzahlung anfallen
     */
    private double incomingInterest;

    /**
     * die Zinsen (positiver Wert in Prozent, 0 bis 1) bei einer Auszahlung anfallen
     */
    private double outcomingInterest;

    /**
     * Legt das Attribut incomingInterest fest
     * @param newIncomingInterest neuer Wert für incomingInterest
     */
    public void setIncomingInterest (double newIncomingInterest) {
        this.incomingInterest = newIncomingInterest;
    }

    /**
     * @return den aktuellen Wert von incomingInterest
     */
    public double getIncomingInterest() {
        return this.incomingInterest;
    }

    /**
     * Legt das Attribut outcomingInterest fest
     * @param newOutcomingInterest neuer Wert für outcomingInterest
     */
    public void setOutcomingInterest (double newOutcomingInterest) {
        this.outcomingInterest = newOutcomingInterest;
    }

    /**
     * @return den aktuellen Wert von outcomingInterest
     */
    public double getOutcomingInterest () {
        return this.outcomingInterest;
    }

    /**
     * Konstruktor mit drei Attributen
     * @param newDate Wert für date
     * @param newDescription Wert für description
     * @param newAmount Wert für amount
     */
    public Payment (String newDate, String newDescription, double newAmount) {
        super(newDate, newDescription, newAmount);
    }

    /**
     * Konstruktor mit aller Attribute
     * @param newDate Wert für date
     * @param newDescription Wert für description
     * @param newAmount Wert für amount
     * @param newIncomingInterest Wert für incomingInterest
     * @param newOutcomingInterest Wert für outcomingInterest
     */
    public Payment (String newDate, String newDescription, double newAmount, double newIncomingInterest, double newOutcomingInterest) {
        this(newDate, newDescription, newAmount);
        this.incomingInterest = newIncomingInterest;
        this.outcomingInterest = newOutcomingInterest;
    }

    /**
     * Copy Constructor
     * @param newPayment neue Objekt festzulegen
     */
    public Payment (Payment newPayment) {
        this(newPayment.date, newPayment.description, newPayment.amount, newPayment.incomingInterest, newPayment.outcomingInterest);
    }

    /**
     * @return neue amount nach Interest
     */
    @Override
    public double calculate() {
        if (amount >= 0) {
            return (amount - incomingInterest * amount);
        }
        else
            return (amount + outcomingInterest * amount);
    }

    /**
     * @return den Inhalt aller Klassenattribute
     */
    @Override
    public String toString() {
        return super.toString() + ", Incoming Interest: " + incomingInterest + ", Outcoming Interest: " + outcomingInterest + "\n";
    }

    /**
     * Zwei Objekte der Klasse Payment zum Vergleich
     * @param obj das zu vergleichende Objekt
     * @return true, wenn beide sind gleich sonst false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Payment payment)
            return (super.equals(payment) && incomingInterest == payment.incomingInterest && outcomingInterest == payment.outcomingInterest);
        return false;
    }

}