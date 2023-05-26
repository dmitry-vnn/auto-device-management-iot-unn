package dmitry.adm.exception;

import dmitry.adm.entity.dto.ResponseError;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler({ ApiErrorException.class })
    public ResponseEntity<ResponseError> handleInvalidJson(ApiErrorException error, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseError(
                        error.getApiError()),
                        HttpStatusCode.valueOf(error.getApiError().getHttpStatusCode()
                )
        );
    }



}
