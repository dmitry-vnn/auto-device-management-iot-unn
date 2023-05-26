package dmitry.adm.service;

import dmitry.adm.service.impl.DeviceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public DeviceService deviceService() {
        return new DeviceServiceImpl();
    }


}
