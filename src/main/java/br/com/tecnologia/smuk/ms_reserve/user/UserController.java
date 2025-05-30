package br.com.tecnologia.smuk.ms_reserve.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository iUserRepository;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        User userSave = iUserRepository.findByUsername(user.getUsername());

        if(Objects.nonNull(userSave)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado.");
        }

        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));

        return ResponseEntity.status(HttpStatus.CREATED).body(iUserRepository.save(user));
    }
}
