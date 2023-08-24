package dmitry.adm.error;

public interface ApiError {

    String getMessage();

    int getCode();

    int getHttpStatusCode();

}
