package bank;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CustomDe_Serializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
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
