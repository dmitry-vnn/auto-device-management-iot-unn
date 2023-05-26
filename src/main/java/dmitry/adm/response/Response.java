package dmitry.adm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {

    private final ResponseType responseType;
    private final Object responseBody;

    public enum ResponseType {

        ERROR,
        SUCCESS
    }
}
