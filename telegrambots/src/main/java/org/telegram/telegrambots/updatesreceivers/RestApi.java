package org.telegram.telegrambots.updatesreceivers;

import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiValidationException;
import org.telegram.telegrambots.generics.WebhookBot;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Rest api to for webhook callback function
 * @date 20 of June of 2015
 */
@Path("callback")
public class RestApi {

    private final ConcurrentHashMap<String, WebhookBot> callbacks = new ConcurrentHashMap<>();

    public RestApi() {
    }

    public void registerCallback(WebhookBot callback) {
        if (!callbacks.containsKey(callback.getBotPath())) {
            callbacks.put(callback.getBotPath(), callback);
        }
    }

    @POST
    @Path("/{botPath}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReceived(@PathParam("botPath") String botPath, Update update) {
        if (callbacks.containsKey(botPath)) {
            try {
                BotApiMethod response = callbacks.get(botPath).onWebhookUpdateReceived(update);
                if (response != null) {
                    response.validate();
                }
                return Response.ok(response).build();
            } catch (TelegramApiValidationException e) {
                BotLogger.severe("RESTAPI", e);
                return Response.serverError().build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{botPath}")
    @Produces(MediaType.APPLICATION_JSON)
    public String testReceived(@PathParam("botPath") String botPath) {
        if (callbacks.containsKey(botPath)) {
            return "Hi there " + botPath + "!";
        } else {
            return "Callback not found for " + botPath;
        }
    }
}
