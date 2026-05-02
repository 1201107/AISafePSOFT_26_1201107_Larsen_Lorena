package com.example.AISafePSOFT_26;

import com.example.AISafePSOFT_26.Users.domain.Collaborator;
import com.example.AISafePSOFT_26.Users.infrastructure.CollaboratorRepository;
import com.example.AISafePSOFT_26.Users.domain.Role;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Profile("dev")
public class Bootstrap implements ApplicationRunner {

    private final CollaboratorRepository collaboratorRepository;
    private final PasswordEncoder passwordEncoder;

    public Bootstrap(CollaboratorRepository collaboratorRepository,
                     PasswordEncoder passwordEncoder) {
        this.collaboratorRepository = collaboratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (collaboratorRepository.count() == 0) {
            collaboratorRepository.save(new Collaborator("admin","admin@email.com",passwordEncoder.encode("admin123"), Set.of(Role.ADMIN)));
            collaboratorRepository.save(new Collaborator("atcc","atcc@email.com",passwordEncoder.encode("atcc123"),   Set.of(Role.ATCC)));
            collaboratorRepository.save(new Collaborator("backoffice","backoffice@email.com",passwordEncoder.encode("backoffice123"),   Set.of(Role.BACKOFFICE)));
            collaboratorRepository.save(new Collaborator("technician","technician@email.com",passwordEncoder.encode("technician123"),   Set.of(Role.MAINTENANCE_TECHNICIAN)));
            collaboratorRepository.save(new Collaborator("supervisor","supervisor@email.com",passwordEncoder.encode("supervisor123"),   Set.of(Role.MAINTENANCE_SUPERVISOR)));
        }
    }
}
