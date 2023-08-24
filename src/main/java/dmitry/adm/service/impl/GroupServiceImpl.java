package dmitry.adm.service.impl;

import dmitry.adm.entity.dto.*;
import dmitry.adm.entity.model.*;
import dmitry.adm.error.ApiErrorException;
import dmitry.adm.error.ErrorType;
import dmitry.adm.repository.*;
import dmitry.adm.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {




    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupDeviceRepository groupDeviceRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void createGroup(RequestCreateGroupData createGroupData) {

        User user = getUserOrError(createGroupData.getTelegramId());

        if (groupRepository.groupExists(createGroupData.getGroupName())) {
            throw new ApiErrorException(ErrorType.GROUP_ALREADY_EXISTS);
        }

        Group group = new Group(
                createGroupData.getGroupName(), user.getId(), createGroupData.getGroupPassword()
        );

        groupRepository.save(group);

        setUserActiveGroupId(user, group.getId());
    }

    private User getUserOrError(int telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new ApiErrorException(ErrorType.USER_NOT_FOUND));
    }

    @Override
    public void setActiveGroup(int telegramId, String groupName) {

        User user = getUserOrError(telegramId);

        Group group = getGroupOrError(groupName);

        if (!IsUserGroupMemberOrOwner(user.getId(), group)) {
            throw new ApiErrorException(ErrorType.USER_IS_NOT_GROUP_MEMBER);
        }

        setUserActiveGroupId(user, group.getId());

    }

    private boolean IsUserGroupMemberOrOwner(int userId, Group group) {
        return isOwner(userId, group.getOwnerId()) || groupMemberRepository.isGroupMember(group.getId(), userId);
    }

    private Group getGroupOrError(String groupName) {
        return groupRepository.findByName(groupName)
                .orElseThrow(() -> new ApiErrorException(ErrorType.GROUP_NOT_EXISTS));
    }

    @Override
    public void joinGroup(RequestJoinGroupData joinGroupData) {
        User user = getUserOrError(joinGroupData.getTelegramId());

        Group group = getGroupOrError(joinGroupData.getGroupName());

        if (!group.getPassword().equals(joinGroupData.getGroupPassword())) {
            throw new ApiErrorException(ErrorType.GROUP_WRONG_PASSWORD);
        }

        if (IsUserGroupMemberOrOwner(user.getId(), group)) {
            throw new ApiErrorException(ErrorType.GROUP_MEMBER_IS_ALREADY_JOINED);
        }

        groupMemberRepository.save(new GroupMember(group.getId(), user.getId()));

        setUserActiveGroupId(user, group.getId());
    }


    private void checkUserOwnerOrError(User user, Group group) {
        if (!isOwner(user, group)) {
            throw new ApiErrorException(ErrorType.USER_IS_NOT_GROUP_OWNER);
        }
    }

    private boolean isOwner(User user, Group group) {
        return isOwner(user.getId(), group.getOwnerId());
    }

    private boolean isOwner(int userId, int ownerId) {
        return userId == ownerId;
    }

    private Group getActiveGroupByUserOrError(User user) {
        Integer activeGroupId = user.getActiveGroupId();

        if (activeGroupId == null) {
            throw new ApiErrorException(ErrorType.USER_NO_ACTIVE_GROUP);
        }

        return groupRepository.findById(activeGroupId).orElseThrow();
    }

    @Override
    public void linkDevice(RequestLinkDeviceData linkDeviceData) {
        User user = getUserOrError(linkDeviceData.getTelegramId());

        Group group = getActiveGroupByUserOrError(user);

        checkUserOwnerOrError(user, group);

        Device device = getDeviceOrError(linkDeviceData.getDeviceId());
        if (!device.getPassword().equals(linkDeviceData.getDevicePassword())) {
            throw new ApiErrorException(ErrorType.DEVICE_WRONG_PASSWORD);
        }

        if (groupDeviceRepository.deviceIsLinked(group.getId(), device.getId())) {
            throw new ApiErrorException(ErrorType.DEVICE_ALREADY_LINKED_TO_GROUP);
        }

        groupDeviceRepository.save(new GroupDevice(group.getId(), device.getId(), linkDeviceData.getDeviceDescription()));

    }


    @Override
    public void removeDevice(int telegramId, int deviceId) {
        User user = getUserOrError(telegramId);

        Group group = getActiveGroupByUserOrError(user);

        checkUserOwnerOrError(user, group);

        if (!groupDeviceRepository.deviceIsLinked(group.getId(), deviceId)) {
            throw new ApiErrorException(ErrorType.DEVICE_NOT_LINKED_TO_GROUP);
        }

        groupDeviceRepository.removeById(new GroupDevice.PrimaryKey(group.getId(), deviceId));
    }

    @Override
    public boolean isOwnerGroup(int telegramId) {
        User user = getUserOrError(telegramId);

        Group group = getActiveGroupByUserOrError(user);

        return user.getId() == group.getOwnerId();
    }

    @Override
    public void kickMember(int telegramId, int kickedTelegramId) {
        User user = getUserOrError(telegramId);

        Group group = getActiveGroupByUserOrError(user);

        checkUserOwnerOrError(user, group);

        User kickedUser = getUserOrError(kickedTelegramId);

        if (user.getId() == kickedUser.getId()) {
            throw new ApiErrorException(ErrorType.GROUP_KICK_YOURSELF);
        }

        if (!groupMemberRepository.isGroupMember(group.getId(), kickedUser.getId())) {
            throw new ApiErrorException(ErrorType.USER_IS_NOT_GROUP_MEMBER);
        }

        groupMemberRepository.delete(new GroupMember(group.getId(), kickedUser.getId()));

        kickedUser.setActiveGroupId(null);
        userRepository.update(kickedUser);
    }

    @Override
    public void deleteGroup(int telegramId) {
        User user = getUserOrError(telegramId);

        Group group = getActiveGroupByUserOrError(user);

        checkUserOwnerOrError(user, group);

        //cascade delete correctly (on DB level)
        groupRepository.delete(group);

    }

    @Override
    public void leaveGroup(int telegramId) {
        User user = getUserOrError(telegramId);

        Group group = getActiveGroupByUserOrError(user);

        if (isOwner(user.getId(), group.getOwnerId())) {
            groupRepository.delete(group);
            return;
        }

        groupMemberRepository.delete(new GroupMember(group.getId(), user.getId()));

        user.setActiveGroupId(null);
        userRepository.update(user);
    }

    @Override
    public List<ResponseUserForGet> allMembers(int telegramId) {
        Group group = getActiveGroupByUserOrError(getUserOrError(telegramId));

        var list = groupMemberRepository.findAllByGroupId(group.getId());

        User owner = userRepository.findById(group.getOwnerId()).orElseThrow();

        list.add(new ResponseUserForGet(owner.getTelegramId(), owner.getFullName(), true));

        return list;
    }

    @Override
    public List<ResponseDeviceForGet> allDevices(int telegramId) {
        Group group = getActiveGroupByUserOrError(getUserOrError(telegramId));
        return groupDeviceRepository.
                findAllByGroupId(group.getId()).stream()
                .map(ResponseDeviceForGet::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> allGroups(int telegramId) {
        return groupRepository.findAllUserGroupNames(getUserOrError(telegramId).getId());
    }

    @Override
    public void changePassword(int telegramId, String password) {
        User user = getUserOrError(telegramId);

        Group group = getActiveGroupByUserOrError(user);

        checkUserOwnerOrError(user, group);

        group.setPassword(password);
        groupRepository.update(group);
    }

    @Override
    public void checkDeviceValidOrError(int telegramId, int deviceId) {
        User user = getUserOrError(telegramId);

        Group group = getActiveGroupByUserOrError(user);

        if (!groupDeviceRepository.deviceIsLinked(group.getId(), deviceId)) {
            throw new ApiErrorException(ErrorType.DEVICE_NOT_LINKED_TO_GROUP);
        }
    }

    private Device getDeviceOrError(int deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ApiErrorException(ErrorType.DEVICE_NOT_FOUND));
    }

    private void setUserActiveGroupId(User user, int groupId) {
        user.setActiveGroupId(groupId);
        userRepository.update(user);
    }
}
