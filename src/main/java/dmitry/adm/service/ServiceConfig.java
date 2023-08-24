package dmitry.adm.service;

import dmitry.adm.service.impl.GroupServiceImpl;
import dmitry.adm.service.impl.UserServiceImpl;
import dmitry.adm.service.impl.DeviceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public DeviceService deviceService() {
        return new DeviceServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public GroupService groupService() {
        return new GroupServiceImpl();
    }


}
