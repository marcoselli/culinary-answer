package br.dev.marco.handler;

import br.dev.marco.usecase.exceptions.OpenAiException;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionHandler {

    @ServerExceptionMapper
    protected Response toOpenAiExceptionResponse(OpenAiException exception){
        return Response.status(HttpResponseStatus.REQUEST_TIMEOUT.code())
                .entity(exception.getErrorMessage())
                .build();
    }
}
