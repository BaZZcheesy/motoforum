package ch.wiss.motoforumapi.setup;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.wiss.motoforumapi.repository.RoleRepository;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.security.ERole;
import ch.wiss.motoforumapi.security.Role;
import ch.wiss.motoforumapi.security.User;

@Component
public class DataBaseLoader implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DataBaseLoader(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        if(roleRepository.count() == 0) {
            this.roleRepository.save(new Role(ERole.ROLE_USER));
            this.roleRepository.save(new Role(ERole.ROLE_ADMIN));
            this.roleRepository.save(new Role(ERole.ROLE_MODERATION));
        }
        if (userRepository.count() == 0) {
            User user = new User("janedoe", "jane.doe@email.com",
                passwordEncoder.encode("p@ssw0rd"));
            user.setRoles(new HashSet<>(Arrays.asList(
                    roleRepository.findByRole(ERole.ROLE_USER).get())));
            this.userRepository.save(user);
        }
    }
}