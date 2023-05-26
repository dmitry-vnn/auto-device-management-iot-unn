package dmitry.adm.exception;

public interface ApiError {

    String getMessage();

    int getCode();

    int getHttpStatusCode();

}
