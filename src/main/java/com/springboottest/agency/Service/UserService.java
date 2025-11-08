
package com.springboottest.agency.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboottest.agency.Entity.UserEntity;
import com.springboottest.agency.Repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

   public ResponseEntity<?> addUser(UserEntity userEntity) {

    UserEntity existingUser = userRepo.findByUsername(userEntity.getUsername());
    if (existingUser != null) {
        return ResponseClass.responseFailure("Username already exists");
    }
    UserEntity newUser = new UserEntity();
    newUser.setUsername(userEntity.getUsername());
    newUser.setRole(userEntity.getRole());

    String encodedPassword = encoder.encode(userEntity.getPassword());
    newUser.setPassword(encodedPassword);

    // Save user
    userRepo.save(newUser);

    return ResponseClass.responseSuccess("User created successfully");
}



}
