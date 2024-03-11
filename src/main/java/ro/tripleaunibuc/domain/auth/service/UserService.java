package ro.tripleaunibuc.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tripleaunibuc.domain.auth.model.Role;
import ro.tripleaunibuc.domain.auth.model.User;
import ro.tripleaunibuc.application.auth.dto.request.RegistrationModel;
import ro.tripleaunibuc.domain.auth.repository.RoleRepository;
import ro.tripleaunibuc.domain.auth.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void registerUser(RegistrationModel registrationModel) {
        createUser(registrationModel);
        assignRole(registrationModel);
    }

    private void createUser(RegistrationModel registrationModel) {
        User user = new User();
        user.setUsername(registrationModel.getUsername());
        user.setPassword(passwordEncoder.encode(registrationModel.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private void assignRole(RegistrationModel registrationModel) {
        Role role = new Role();
        role.setUsername(registrationModel.getUsername());
        role.setRole("ROLE_USER");
        roleRepository.save(role);
    }
}
