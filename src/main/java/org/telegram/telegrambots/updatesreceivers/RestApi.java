package org.telegram.telegrambots.updatesreceivers;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.ITelegramWebhookBot;

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

    private final ConcurrentHashMap<String, ITelegramWebhookBot> callbacks = new ConcurrentHashMap<>();

    public RestApi() {
    }

    public void registerCallback(ITelegramWebhookBot callback) {
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
            return Response.ok(this.callbacks.get(botPath).onWebhookUpdateReceived(update)).build();
        }
        return Response.ok().build();
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
