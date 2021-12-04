import bank.*;

import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AccountDoesNotExistException;
import bank.exceptions.TransactionAlreadyExistException;
import bank.exceptions.TransactionDoesNotExistException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main (String[] args) throws AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionDoesNotExistException, IOException {

        Payment payment = new Payment("Test", "testing payment", 100);

        Gson gson = new Gson();
        String json = gson.toJson(payment);

        GsonBuilder gsonBuilder = new GsonBuilder();
        CustomSerializer serializer = new CustomSerializer();
        gsonBuilder.registerTypeAdapter(Payment.class, serializer);

        Gson custom = gsonBuilder.create();
        String customJson = custom.toJson(payment);


//        System.out.println("Printing json:\n" + json + "\n");
        try (FileWriter file = new FileWriter("testingPayment.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(customJson);
//            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


      /*  Gson gson_ = new GsonBuilder().registerTypeAdapter(Payment.class, new bank.CustomSerializer()).setPrettyPrinting().create();
        System.out.println(gson_.toJson(payment));

        Payment gsonPayment = gson.fromJson(json, Payment.class);*/

//        System.out.println("Printing object from Gson:\n" + gsonPayment);


        PrivateBank deutscheBank = new PrivateBank("Deutsche Bank", 0.25, 0.3);
        PrivateBank aachenerBank= new PrivateBank("Aachener Bank", 0.25, 0.3);

        deutscheBank.createAccount("Molziles", List.of(
                new Payment("12.03.2008", "Payment", 321),
                new Payment("23.09.1897", "Payment", -2500, 0.8, 0.5),
                new OutcomingTransfer("03.03.2000", "OutcomingTransfer", 80, "Molziles", "Elixir")
        ));
        deutscheBank.createAccount("Elixir", List.of(
                new Payment("22.06.1998", "Payment", 435, 0., 0.),
                new IncomingTransfer("03.03.2000", "IncomingTransfer", 80, "Molziles", "Elixir"),
                new Payment("05.08.2022", "Payment", -118, 0., 0.),
                new OutcomingTransfer("15.04.1990", "OutcomingTransfer", 185, "Elixir", "Vaio"),
                new OutcomingTransfer("30.07.2020", "OutcomingTransfer", 1890, "Elixir", "Booth"),
                new Payment("19.01.2011", "Payment", -789, 0.9, 0.25)
        ));
        deutscheBank.createAccount("Hagen");

        String jsonPrivateBank = (new Gson()).toJson(deutscheBank);
        try (FileWriter fileBank = new FileWriter("src/JsonFiles/privateBank" + ".json")) {
            fileBank.write(jsonPrivateBank);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
