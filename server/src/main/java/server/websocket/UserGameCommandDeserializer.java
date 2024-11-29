package server.websocket;

import com.google.gson.*;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import java.lang.reflect.Type;

public class UserGameCommandDeserializer implements JsonDeserializer<UserGameCommand> {
    @Override
    public UserGameCommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("moveCommand")) {
            return context.deserialize(jsonObject, MakeMoveCommand.class);
        }
        else {
            return context.deserialize(jsonObject, UserGameCommand.class);
        }
    }
}
