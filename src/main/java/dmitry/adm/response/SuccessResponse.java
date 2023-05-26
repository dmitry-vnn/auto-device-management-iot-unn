package dmitry.adm.response;

public class SuccessResponse extends Response {

    public SuccessResponse(Object responseBody) {
        super(ResponseType.SUCCESS, responseBody);
    }
}
