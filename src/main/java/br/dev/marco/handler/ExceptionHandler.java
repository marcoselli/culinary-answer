package br.dev.marco.handler;

import br.dev.marco.infra.security.sso.exceptions.SecurityException;
import br.dev.marco.domain.usecase.exceptions.OpenAiException;
import br.dev.marco.domain.usecase.exceptions.UnsupportedQuestionException;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionHandler {

    @ServerExceptionMapper
    protected Response toOpenAiExceptionResponse(OpenAiException exception) {
        var statusCode = exception.getRootCause().equals("RuntimeException") ?
                HttpResponseStatus.REQUEST_TIMEOUT.code()
                : HttpResponseStatus.INTERNAL_SERVER_ERROR.code();
        return Response.status(statusCode)
                .entity(new ErrorMessage(exception))
                .build();
    }

    @ServerExceptionMapper
    protected Response toUnUnsupportedQuestionExceptionResponse(UnsupportedQuestionException exception) {
        var errorMessage = ErrorMessage.builder()
                .errorType(exception.getClass().getSimpleName())
                .description(exception.getErrorMessage())
                .build();
        return Response.status(HttpResponseStatus.BAD_REQUEST.code())
                .entity(errorMessage)
                .build();
    }

    @ServerExceptionMapper
    protected Response toSecurityExceptionResponse(SecurityException exception) {
        var errorMessage = ErrorMessage.builder()
                .errorType(exception.getClass().getSimpleName())
                .description(exception.getMessage())
                .build();
        return Response.status(HttpResponseStatus.BAD_REQUEST.code())
                .entity(errorMessage)
                .build();
    }
}
