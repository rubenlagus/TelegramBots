package org.telegram.telegrambots.updatesreceivers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import javax.inject.Inject;
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
 * Rest api to for webhook callback function
 */
@Path(Constants.WEBHOOK_URL_PATH)
@Slf4j
public class RestApi {

    @Inject
    CallbacksProxy callbacksProxy;

    @POST
    @Path("/{botPath}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReceived(@PathParam("botPath") String botPath, Update update) {
        if (callbacksProxy.containsBotPath(botPath)) {
            try {
                BotApiMethod<?> response = callbacksProxy.getWebhookBot(botPath).onWebhookUpdateReceived(update);
                if (response != null) {
                    response.validate();
                }
                return Response.ok(response).build();
            } catch (TelegramApiValidationException e) {
                log.error(e.getLocalizedMessage(), e);
                return Response.serverError().build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{botPath}")
    @Produces(MediaType.APPLICATION_JSON)
    public String testReceived(@PathParam("botPath") String botPath) {
        if (callbacksProxy.containsBotPath(botPath)) {
            return "Hi there " + botPath + "!";
        } else {
            return "Callback not found for " + botPath;
        }
    }
}
