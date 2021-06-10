package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.DTO.UserDTO;
import recipes.model.User;
import recipes.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(UserDTO userDTO) {
        if (Optional.ofNullable(userRepository.findByEmail(userDTO.getEmail())).isPresent()) {
            return false;
        } else {
            userRepository.save(new User(userDTO.getEmail(), new BCryptPasswordEncoder().encode(userDTO.getPassword())));
            return true;
        }
    }
}