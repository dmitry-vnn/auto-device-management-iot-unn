package dmitry.adm.controller;

import dmitry.adm.entity.dto.RequestUser;
import dmitry.adm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/user/",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("add")
    void addUser(RequestUser requestUser) {
        userService.addUser(requestUser);
    }

}
