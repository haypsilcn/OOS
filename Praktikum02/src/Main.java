import bank.Payment;
import bank.Transfer;

public class Main {
    public static void main (String[] args) {

        Payment ersteEinzahlung = new Payment("17.09.2009", "1. Einzahlung", 569.4912);
        Payment zweiteEinzahlung = new Payment("27.10.2017", "2. Einzahlung", 1090, 0.5, 1);
        Payment auszahlung = new Payment("30.03.1980", "Auszahlung", -10983, 0.6, 0.789);
        Payment payment = new Payment(auszahlung);

        System.out.print("\nTesting toString() method:\nersteEinzahlung:\n\t\tExpected:\tDate: 17.09.2009, Description: 1. Einzahlung, Amount: 569.4912 €, Incoming Interest: 0.0, Outcoming Interest: 0.0\n\t\tResult:\t\t" + ersteEinzahlung + "zweiteEinzahlung:\n\t\tExpected:\tDate: 27.10.2017, Description: 2. Einzahlung, Amount: 545.0 €, Incoming Interest: 0.5, Outcoming Interest: 1.0\n\t\tResult:\t\t" + zweiteEinzahlung + "auszahlung:\n\t\tExpected:\tDate: 30.03.1980, Description: Auszahlung, Amount: -19648.587 €, Incoming Interest: 0.6, Outcoming Interest: 0.789\n\t\tResult:\t\t" + auszahlung + "payment:\n\t\tExpected:\tDate: 30.03.1980, Description: Auszahlung, Amount: -19648.587 €, Incoming Interest: 0.6, Outcoming Interest: 0.789\n\t\tResult:\t\t" + payment);

        System.out.println("\nTesting equals() method:\nersteEinzahlung vs zweiteEinzahlung expected <false> => " + ersteEinzahlung.equals(zweiteEinzahlung) + "\nzweiteEinzahlung vs auszahlung expected <false> => " + zweiteEinzahlung.equals(auszahlung) + "\nauszahlung vs payment expected <true> => " + auszahlung.equals(payment) + "\n\n");


        Transfer firstTransfer = new Transfer("17.09.2009", "1. Einzahlung", 569.4912);
        Transfer secondTransfer = new Transfer("23.08.2222", "2. Transfer", 200.5, "Tom", "Jonas");
        Transfer thirdTransfer = new Transfer("08.05.2000", "3. Transfer", 1248, "Violett", "Brause");
        Transfer transfer = new Transfer(secondTransfer);

        System.out.println("Testing toString() method:\nfirstTransfer:\n\t\tExpected:\tDate: 17.09.2009, Description: 1. Einzahlung, Amount: 569.4912 €, Sender: null, Recipient: null\n\t\tResult:\t\t" + firstTransfer + "secondTransfer:\n\t\tExpected:\tDate: 23.08.2222, Description: 2. Transfer, Amount: 200.5 €, Sender: Tom, Recipient: Jonas\n\t\tResult:\t\t" + secondTransfer + "thirdTransfer:\n\t\tExpected:\tDate: 08.05.2000, Description: 3. Transfer, Amount: 1248.0 €, Sender: Violett, Recipient: Brause\n\t\tResult:\t\t" + thirdTransfer + "transfer:\n\t\tExpected:\tDate: 23.08.2222, Description: 2. Transfer, Amount: 200.5 €, Sender: Tom, Recipient: Jonas\n\t\tResult:\t\t" + transfer);

        System.out.println("Testing equals() method:\npayment vs firstTransfer expected <false> => " + payment.equals(firstTransfer) + "\nfirstTransfer vs secondTransfer expected <false> => " + firstTransfer.equals(secondTransfer) + "\nsecondTransfer vs thirdTransfer expected <false> => " + secondTransfer.equals(thirdTransfer) + "\nsecondTransfer vs transfer expected <true> => " + secondTransfer.equals(transfer));

    }
}
