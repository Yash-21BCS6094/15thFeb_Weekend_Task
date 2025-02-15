package com.example.Food_Ordering.service.implentation;

import com.example.Food_Ordering.dto.OrderDTO;
import com.example.Food_Ordering.dto.UserDTO;
import com.example.Food_Ordering.entity.User;
import com.example.Food_Ordering.exceptions.ResourceNotFoundException;
import com.example.Food_Ordering.repository.AddressRepository;
import com.example.Food_Ordering.repository.ProductRepository;
import com.example.Food_Ordering.repository.UserRepository;
import com.example.Food_Ordering.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public Page<UserDTO> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable).
                map(customer -> modelMapper.map(customer, UserDTO.class));
    }

    @Override
    public UserDTO updateUser(UUID customerId, UserDTO updatedCustomer) {
        User user = userRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find customer")
        );
        User updatedCustomerEntity = modelMapper.map(updatedCustomer, User.class);
        user.setAddress(updatedCustomerEntity.getAddress());
        user.setFirstName(updatedCustomerEntity.getFirstName());
        user.setLastName(updatedCustomerEntity.getLastName());
        user.setEmail(updatedCustomerEntity.getEmail());

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Cannot find customer"));
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email does not exits")
        );

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> searchUserByName(String name) {
        List<User> users = userRepository.findByFirstNameContainingIgnoreCase(name);
        List<UserDTO> userDTOS = (List<UserDTO>) users.stream().map((customer) -> modelMapper.map(customer, UserDTO.class));
        return userDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrder(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find customer")
        );

        return user.getOrders().stream().map(order -> modelMapper.map(order, OrderDTO.class)).
                toList();
    }

}
