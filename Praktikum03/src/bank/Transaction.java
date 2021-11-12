package bank;

abstract class Transaction implements CalculateBill {

    /**
     * stellt den Zeitpunkt der Transaktion mit dem Format als DD.MM.YYYY dar
     */
    protected String date;

    /**
     * eine zusätzliche Beschreibung des Vorgangs
     */
    protected String description;

    /**
     * stellt die Geldmenge der Transaktion dar
     */
    protected double amount;

    /**
     * Konstruktor für abstrakte Klasse
     * @param newDate Wert für date
     * @param newDescription Wert für description
     * @param newAmount Wert für amount
     */
    public Transaction (String newDate, String newDescription, double newAmount) {
        this.date = newDate;
        this.description = newDescription;
        this.amount = newAmount;
    }

    /**
     * Legt das Attribut date fest
     * @param newDate neuer Wert für date
     */
    public void setDate (String newDate) {
        this.date = newDate;
    }

    /**
     * @return den aktuellen Wert von date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Legt das Attribute description fest
     * @param newDescription neuer Wert für description
     */
    public void setDescription (String newDescription) {
        this.description = newDescription;
    }

    /**
     * @return den aktuellen Wert von description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Legt das Attribut amount fest
     * @param newAmount neuer Wert für amount
     */
    public void setAmount (double newAmount) {
        this.amount = newAmount;
    }

    /**
     * @return den aktuellen Wert von amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * @return den Inhalt aller Klassenattribute
     */
    @Override
    public String toString() {
        return "Date: " + date + ", Description: " + description + ", Amount: " + calculate() + " €";
    }

    /**
     * Zwei Transaktionsobjekte zum Vergleich
     * @param obj das zu vergleichende Objekt
     * @return true, wenn beide sind gleich sonst false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Transaction transaction)
            return (this.date.equals(transaction.date) && this.description.equals(transaction.description) && this.amount == transaction.amount);
        return false;
    }
}