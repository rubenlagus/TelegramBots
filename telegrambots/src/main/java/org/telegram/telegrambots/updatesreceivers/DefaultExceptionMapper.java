package org.telegram.telegrambots.updatesreceivers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints exceptions in webhook bots to stderr
 *
 * @author Mouamle
 * @version 1.0
 */
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionMapper.class);

    @Override
    public Response toResponse(Throwable exception) {
        log.error("Exception caught: ", exception);
        return Response.serverError().build();
    }
}
