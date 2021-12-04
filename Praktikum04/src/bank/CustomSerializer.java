package bank;

import bank.Transaction;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CustomSerializer implements JsonSerializer<Transaction> {
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {

      /*  String string = (new Gson().toJson(transaction));

        return new JsonPrimitive(string);*/
//        if (transaction instanceof Payment) {
     /*   String string = "CLASSNAME" + "\\n\n" + "[";
           return new JsonPrimitive(string + transaction.getClass());*/
//        }
        return null;
    }
}
