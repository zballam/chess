package net;

import com.google.gson.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.lang.reflect.Type;

public class ServerMessageDeserializer implements JsonDeserializer<ServerMessage> {
    @Override
    public ServerMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("game")) {
            return context.deserialize(jsonObject, LoadGameMessage.class);
        } else if (jsonObject.has("errorMessage")) {
            return context.deserialize(jsonObject, ErrorMessage.class);
        } else if (jsonObject.has("message")){ // Notification Message
            return context.deserialize(jsonObject, NotificationMessage.class);
        }
        else {
            return context.deserialize(jsonObject, ServerMessage.class);
        }
    }
}
