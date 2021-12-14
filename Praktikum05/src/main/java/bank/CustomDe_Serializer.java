package bank;

import com.google.gson.*;

import java.lang.reflect.Type;


public class CustomDe_Serializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.get("CLASSNAME").getAsString().equals("Payment"))
            return new Payment(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("incomingInterest").getAsDouble(),
                    jsonObject.get("outgoingInterest").getAsDouble());

        else if (jsonObject.get("CLASSNAME").getAsString().equals("IncomingTransfer"))
            return new IncomingTransfer(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("sender").getAsString(),
                    jsonObject.get("recipient").getAsString()
            );
        else
            return new OutgoingTransfer(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("sender").getAsString(),
                    jsonObject.get("recipient").getAsString()
            );
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
            jsonTransaction.addProperty("outgoingInterest", payment.getOutgoingInterest());
        }
        jsonTransaction.addProperty("date", transaction.getDate());
        jsonTransaction.addProperty("amount", transaction.getAmount());
        jsonTransaction.addProperty("description", transaction.getDescription());

        jsonObject.addProperty("CLASSNAME", transaction.getClass().getSimpleName());
        jsonObject.add("INSTANCE", jsonTransaction);

        return jsonObject;
    }
}
