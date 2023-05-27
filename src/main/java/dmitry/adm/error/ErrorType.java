package dmitry.adm.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType implements ApiError {

    NOT_ALL_ARGS_REPRESENT(        0, "One of the params is missing", 400),

    DEVICE_WRONG_PASSWORD(         10, "Wrong password", 400),
    DEVICE_NOT_FOUND     (         11, "Device not found", 404),

    USER_NOT_FOUND       (         20, "User not found", 404),
    USER_ALREADY_EXISTS  (         21, "User already exists", 400),
    USER_NO_ACTIVE_GROUP(          22, "User is not set active group", 400),

    GROUP_ALREADY_EXISTS (         30, "Group already exists", 400),
    GROUP_NOT_EXISTS     (         31, "Group not exists", 404),
    USER_IS_NOT_GROUP_MEMBER(      32, "User is not group member", 400),
    GROUP_WRONG_PASSWORD(          33, "Group wrong password", 400),
    GROUP_MEMBER_IS_ALREADY_JOINED(34, "Group member is already joined", 400),
    USER_IS_NOT_GROUP_OWNER(       35, "User is not group owner" , 400),

    DEVICE_ALREADY_LINKED_TO_GROUP(36, "Device already linked to group", 400),
    DEVICE_NOT_LINKED_TO_GROUP(    37, "Device not linked to group", 400);

    private final int code;
    private final String message;
    private final int httpStatusCode;
}
