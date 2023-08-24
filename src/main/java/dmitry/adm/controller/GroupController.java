package dmitry.adm.controller;

import dmitry.adm.entity.dto.*;
import dmitry.adm.service.DeviceService;
import dmitry.adm.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        value = "/api/group/",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("create")
    public void createGroup(RequestCreateGroupData createGroupData) {
        groupService.createGroup(createGroupData);
    }

    @PostMapping("setActive")
    public void setActiveGroup(int telegramId, String groupName) {
        groupService.setActiveGroup(telegramId, groupName);
    }

    @PostMapping("join")
    public void joinGroup(RequestJoinGroupData joinGroupData) {
        groupService.joinGroup(joinGroupData);
    }

    @PostMapping("linkDevice")
    public void linkDevice(RequestLinkDeviceData linkDeviceData) {
        groupService.linkDevice(linkDeviceData);
    }

    @PostMapping("removeDevice")
    public void removeDevice(int telegramId, int deviceId) {
        groupService.removeDevice(telegramId, deviceId);
    }

    @GetMapping("isOwner")
    public Map<String, Boolean> isOwnerGroup(int telegramId) {
        return Collections.singletonMap(
                "status", groupService.isOwnerGroup(telegramId)
        );
    }

    @PostMapping("kick")
    public void kickMember(int telegramId, int kickedTelegramId) {
        groupService.kickMember(telegramId, kickedTelegramId);
    }

    @DeleteMapping("delete")
    public void deleteGroup(int telegramId) {
        groupService.deleteGroup(telegramId);
    }

    @PostMapping("leave")
    public void leaveGroup(int telegramId) {
        groupService.leaveGroup(telegramId);
    }

    @GetMapping("allMembers")
    public List<ResponseUserForGet> allMembers(int telegramId) {
        return groupService.allMembers(telegramId);
    }

    @GetMapping("allDevices")
    public List<ResponseDeviceForGet> allDevices(int telegramId) {
        return groupService.allDevices(telegramId);
    }

    //All user joined/owned groups
    @GetMapping("allGroups")
    public List<String> groups(int telegramId) {
        return groupService.allGroups(telegramId);
    }

    @PostMapping("changePassword")
    public void changeGroupPassword(int telegramId, String newPassword) {
        groupService.changePassword(telegramId, newPassword);
    }

    @GetMapping("stats")
    public List<DeviceStats> getLastDeviceValues(int telegramId, int deviceId, int lastHours) {
        groupService.checkDeviceValidOrError(telegramId, deviceId);

        return deviceService.getStats(deviceId, lastHours);
    }

}
