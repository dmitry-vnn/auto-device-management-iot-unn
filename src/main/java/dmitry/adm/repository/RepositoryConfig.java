package dmitry.adm.repository;

import dmitry.adm.repository.impl.DatabasedDeviceRepository;
import dmitry.adm.repository.impl.DatabasedDeviceValueRepository;
import dmitry.adm.repository.impl.DatabasedUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public DeviceRepository deviceRepository() {
        return new DatabasedDeviceRepository();
    }

    @Bean
    public DeviceValueRepository deviceValueRepository() {
        return new DatabasedDeviceValueRepository();
    }

    @Bean
    public UserRepository userRepository() {
        return new DatabasedUserRepository();
    }

}
