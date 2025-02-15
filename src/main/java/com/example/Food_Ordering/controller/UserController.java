package com.example.Food_Ordering.controller;

import com.example.Food_Ordering.dto.AuthenticationRequest;
import com.example.Food_Ordering.dto.AuthenticationResponse;
import com.example.Food_Ordering.dto.UserDTO;
import com.example.Food_Ordering.entity.User;
//import com.example.Food_Ordering.service.AuthenticationService;
import com.example.Food_Ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class UserController {

    @Autowired
    private UserService customerServices;
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    // creating customer with address
//    @PostMapping("/signup")
//    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody UserDTO userDTO) {
//        AuthenticationResponse response = authenticationService.register(userDTO);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest authenticationRequest){
//        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
//        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
//    }

    // updating customer's information
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") UUID userId, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(customerServices.updateUser(userId, userDTO), HttpStatus.ACCEPTED);
    }

    // Get the customer by his Id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerServices.getUserById(id));
    }

    // Delete the customer using id
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        customerServices.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Get the customer by his email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable String email) {
        return ResponseEntity.ok(customerServices.getUserByEmail(email));
    }

    // Not getting this
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String name) {
        return ResponseEntity.ok(customerServices.searchUserByName(name));
    }

    // To get the customer by page and size
    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam("page") int page,
                                                     @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.of(Optional.ofNullable(customerServices.getAllUser(pageable)));
    }
}
