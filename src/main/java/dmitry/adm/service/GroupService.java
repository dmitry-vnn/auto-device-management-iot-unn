package dmitry.adm.service;

import dmitry.adm.entity.dto.*;

import java.util.List;

public interface GroupService {
    void createGroup(RequestCreateGroupData createGroupData);

    void setActiveGroup(int telegramId, String groupName);

    void joinGroup(RequestJoinGroupData joinGroupData);

    void linkDevice(RequestLinkDeviceData linkDeviceData);

    void removeDevice(int telegramId, int deviceId);

    boolean isOwnerGroup(int telegramId);

    void kickMember(int telegramId, int kickedTelegramId);

    void deleteGroup(int telegramId);

    void leaveGroup(int telegramId);

    List<ResponseUserForGet> allMembers(int telegramId);

    List<ResponseDeviceForGet> allDevices(int telegramId);

    List<String> allGroups(int telegramId);

    void changePassword(int telegramId, String password);

    void checkDeviceValidOrError(int telegramId, int deviceId);
}
