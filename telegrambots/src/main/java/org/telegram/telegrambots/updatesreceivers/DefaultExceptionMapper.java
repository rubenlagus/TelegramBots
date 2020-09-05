package org.telegram.telegrambots.updatesreceivers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Prints exceptions in webhook bots to stderr
 *
 * @author Mouamle
 * @version 1.0
 */
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace();
        return Response.serverError().build();
    }

}
