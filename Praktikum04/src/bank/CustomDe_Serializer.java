package bank;

import com.google.gson.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomDe_Serializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

       /* try {
            Reader reader = Files.newBufferedReader(Paths.get("src/PersistenceSample.json"));
            JsonArray parser = JsonParser.parseReader(reader).getAsJsonArray();
//            jsonElement.getAsJsonArray().addAll(parser);

            for (jsonElement : parser.getAsJsonArray()) {
                JsonObject obj = jsonElement.getAsJsonObject();
            }

            System.out.println(jsonElement);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*JsonArray jsonArray = (JsonArray) JsonParser.parseString(jsonElement.toString());


        JsonObject jsonObject = jsonArray.getAsJsonObject();

//        System.out.println(jsonObject.getClass());
//        System.out.println(jsonObject.get("CLASSNAME").getAsString());

        if (jsonObject.get("CLASSNAME").getAsString().equals("Payment"))
            System.out.println("testing");
        return new Payment(
                jsonObject.get("date").getAsString(),
                jsonObject.get("description").getAsString(),
                jsonObject.get("amount").getAsDouble(),
                jsonObject.get("incomingInterest").getAsDouble(),
                jsonObject.get("outcomingInterest").getAsDouble()
        ) ;*/
        /*|| new IncomingTransfer(
                jsonObject.get("date").getAsString(),
                jsonObject.get("description").getAsString(),
                jsonObject.get("amount").getAsDouble(),
                jsonObject.get("sender").getAsString(),
                jsonObject.get("recipient").getAsString()
        );*/

//        return null;

//        JsonObject jsonObj = jsonElement.getAsJsonObject();
//        JsonObject jsonObject = jsonObj.getAsJsonObject();

        /*Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Reader reader = new FileReader("src/PersistenceSample.json")) {
            jsonElement = gson.fromJson(reader, JsonElement.class);

            String jsonInString = gson.toJson(jsonElement);

            System.out.println(jsonInString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        */

        /*return new Payment(
                jsonObject.get("date").getAsString(),
                jsonObject.get("description").getAsString(),
                jsonObject.get("amount").getAsDouble(),
                jsonObject.get("incomingInterest").getAsDouble(),
                jsonObject.get("outcomingInterest").getAsDouble()
        );*/

//        JsonArray parser = JsonParser.

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.get("CLASSNAME").getAsString().equals("Payment"))
            return new Payment(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("incomingInterest").getAsDouble(),
                    jsonObject.get("outcomingInterest").getAsDouble());

        else if (jsonObject.get("CLASSNAME").getAsString().equals("IncomingTransfer"))
            new IncomingTransfer(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("sender").getAsString(),
                    jsonObject.get("recipient").getAsString()
            );
        else if (jsonObject.get("CLASSNAME").getAsString().equals("IncomingTransfer"))
            return new OutcomingTransfer(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("sender").getAsString(),
                    jsonObject.get("recipient").getAsString()
            );
        return null;
    }

    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject jsonTransaction = new JsonObject();
        JsonObject jsonObject = new JsonObject();

        if (transaction instanceof Transfer transfer) {
            jsonTransaction.addProperty("sender", transfer.getSender());
            jsonTransaction.addProperty("recipient", transfer.getRecipient());
        }
        else if (transaction instanceof Payment payment) {
            jsonTransaction.addProperty("incomingInterest", payment.getIncomingInterest());
            jsonTransaction.addProperty("outcomingInterest", payment.getOutcomingInterest());
        }
        jsonTransaction.addProperty("date", transaction.getDate());
        jsonTransaction.addProperty("amount", transaction.getAmount());
        jsonTransaction.addProperty("description", transaction.getDescription());

        jsonObject.addProperty("CLASSNAME", transaction.getClass().getSimpleName());
        jsonObject.add("INSTANCE", jsonTransaction);

        return jsonObject;
    }
}
