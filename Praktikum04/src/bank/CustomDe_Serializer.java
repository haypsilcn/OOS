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

         /*  String string = (new Gson().toJson(transaction));

        return new JsonPrimitive(string);*/
//        if (transaction instanceof Payment) {
     /*   String string = "CLASSNAME" + "\\n\n" + "[";
           return new JsonPrimitive(string + transaction.getClass());*/
//        }

        JsonObject jsonTransaction = new JsonObject();
        jsonTransaction.addProperty("CLASS: ", String.valueOf(transaction.getClass()));

        return jsonTransaction;
    }
}
