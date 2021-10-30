import bank.Payment;
import bank.Transfer;


public class Main {
    public static void main(String[] args) {
        Transfer transferThreeAttr = new Transfer("6.12.2039", "TransferThreeAttr", 755.8597);
        transferThreeAttr.printObject();
        Transfer transferAllAttr = new Transfer("23.08.2222", "TransferAllAttr", 200.5, "Tom", "Jonas");
        transferAllAttr.printObject();
        Transfer transfer = new Transfer(transferAllAttr);
        transfer.printObject();

        Payment einzahlung = new Payment("17.09.2009", "Einzahlung", 569.4912);
        einzahlung.printObject();
        Payment auszahlung = new Payment("30.03.1980", "Auszahlung", -10983, 0, 0.789);
        auszahlung.printObject();
        Payment payment = new Payment(auszahlung);
        payment.printObject();
    }
}
