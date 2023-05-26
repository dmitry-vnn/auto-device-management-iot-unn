package dmitry.adm.response;

public class ErrorResponse extends Response {

    public ErrorResponse(Object responseBody) {
        super(ResponseType.ERROR, responseBody);
    }
}
