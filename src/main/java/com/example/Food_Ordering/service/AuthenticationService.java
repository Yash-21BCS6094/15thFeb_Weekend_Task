package com.example.Food_Ordering.service;
import com.example.Food_Ordering.dto.AuthenticationRequest;
import com.example.Food_Ordering.dto.AuthenticationResponse;
import com.example.Food_Ordering.dto.UserDTO;
import com.example.Food_Ordering.entity.Address;
import com.example.Food_Ordering.entity.User;
import com.example.Food_Ordering.exceptions.ResourceNotFoundException;
import com.example.Food_Ordering.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 JwtService jwtService,
                                 ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    // While registering the user
    public AuthenticationResponse register(UserDTO userDTO){
        User user = new User();
        user.setAddress(modelMapper.map(userDTO.getAddress(), Address.class));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        String jwtToken = jwtService.generateToken(user.getUsername());
        String refreshToken = jwtService.generateRefresh(new HashMap<>(), user.getUsername());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
        // While registering we are building an authentication response and
        // then passing the user jwtToken and refresh token
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);
        response.setRefreshToken(refreshToken);

        return response;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try{
            User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find user")
            );
            String jwtToken = jwtService.generateToken(user.getUsername());
            String refreshToken = jwtService.generateRefresh(new HashMap<>(), user.getUsername());
            userRepository.save(user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setToken(jwtToken);
            authenticationResponse.setRefreshToken(refreshToken);
            return authenticationResponse;

        }catch(Exception ex){
            throw new ResourceNotFoundException("Cannot find user with given email");
        }

    }

    public AuthenticationResponse refreshToken(String refreshToken){
        String email = jwtService.getEmailFromToken(refreshToken);
        try{
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find user")
            );
            String jwtToken = jwtService.generateToken(user.getUsername());
            String newRefreshToken = jwtService.generateRefresh(new HashMap<>(), user.getUsername());

            // This refresh token method is used when the token expires user can give a
            // refresh token and generate a new token from the server.
            AuthenticationResponse response = new AuthenticationResponse();
            response.setToken(jwtToken);
            response.setRefreshToken(newRefreshToken);
            return response;
        }catch(Exception ex){
            throw new ResourceNotFoundException("Cannot find user");
        }
    }

    public Boolean validateToken(String token, UUID id) {
        return jwtService.validateToken(token, id);
    }

}