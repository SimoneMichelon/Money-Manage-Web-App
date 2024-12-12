package osiride.vitt_be.utils;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import osiride.vitt_be.constant.Role;
import osiride.vitt_be.domain.Credential;
import osiride.vitt_be.domain.User;
import osiride.vitt_be.repository.CredentialRepository;
import osiride.vitt_be.repository.UserRepository;

@Slf4j
@Component
public class InitialDataLoader implements CommandLineRunner {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByRole(Role.ADMIN)) {
        	
            Credential credential = new Credential();
            credential.setEmail("admin@admin.com");
            credential.setPassword(passwordEncoder.encode("admin1234"));
            
            User user = new User();
            user.setDob(LocalDate.now());
            user.setFirstName("Andrew");
            user.setLastName("Tate");
            user.setImgProfile("https://img.favpng.com/10/24/6/user-profile-instagram-computer-icons-png-favpng-rzQf3Y9u65VmEgArYxVb3Dd7H.jpg");
            user.setRole(Role.ADMIN);
            credential.setUser(user);
            userRepository.save(user);
            credentialRepository.save(credential);
            log.info("COMPONENT - Admin load complete !!! ");
        }
    }
}
