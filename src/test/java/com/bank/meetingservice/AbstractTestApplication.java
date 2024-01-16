package com.bank.meetingservice;

import com.bank.meetingservice.config.MeetingMapper;
import com.bank.meetingservice.repository.MaintenanceWindowRepository;
import com.bank.meetingservice.repository.MeetingRepository;
import com.bank.meetingservice.repository.RoomRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Properties;

@ExtendWith(SpringExtension.class)
@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = AbstractTestApplication.Initializer.class)
public abstract class AbstractTestApplication {

    static MSSQLServerContainer mssqlServerContainer;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected MeetingMapper meetingMapper;

    @Autowired
    protected MaintenanceWindowRepository maintenanceWindowRepository;

    @Autowired
    protected MeetingRepository meetingRepository;

    @Autowired
    protected RoomRepository roomRepository;

    @MockBean
    protected RestTemplate restTemplate;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @SneakyThrows
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();

            Properties props = new Properties();
            props.put("spring.datasource.driver-class-name", "org.h2.Driver");
            props.put("spring.datasource.url", "jdbc:h2:mem:meetings;DB_CLOSE_DELAY=-1");
            props.put("spring.datasource.username", "sa");
            props.put("spring.datasource.password", "sa");

            environment.getPropertySources().addFirst(new PropertiesPropertySource("myTestDBProps", props));
            configurableApplicationContext.setEnvironment(environment);
        }
    }
}

