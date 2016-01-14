package org.telegram.updatesreceivers;

import org.telegram.api.objects.Update;
import org.telegram.updateshandlers.UpdatesCallback;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Rest api to for webhook callback function
 * @date 20 of June of 2015
 */
@Path("callback")
public class RestApi {

    private final ConcurrentHashMap<String, UpdatesCallback> callbacks = new ConcurrentHashMap<>();

    public RestApi() {
    }

    public void registerCallback(UpdatesCallback callback, String botName) {
        if (!callbacks.containsKey(botName)) {
            callbacks.put(botName, callback);
        }
    }

    @POST
    @Path("/{botname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReceived(@PathParam("botname") String botname, Update update) {
        if (callbacks.containsKey(botname)) {
            return Response.ok(this.callbacks.get(botname).onWebhookUpdateReceived(update)).build();
        }
        return Response.ok().build();
    }

    @GET
    @Path("/{botname}")
    @Produces(MediaType.APPLICATION_JSON)
    public String testReceived(@PathParam("botname") String botname) {
        if (callbacks.containsKey(botname)) {
            return "Hi there " + botname + "!";
        } else {
            return "Callback not found for " + botname;
        }
    }
}
