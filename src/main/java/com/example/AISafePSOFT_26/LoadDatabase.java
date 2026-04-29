package com.example.AISafePSOFT_26;

import com.example.AISafePSOFT_26.model.User;
import com.example.AISafePSOFT_26.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        log.info("Loading database...");
        return args -> {
            userRepository.save(new User("Bilbo", "bilbo@shire.com", "burglar"));
            userRepository.save(new User("Frodo", "frodo@shire.com", "thief"));

            userRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));
        };
    }
}
