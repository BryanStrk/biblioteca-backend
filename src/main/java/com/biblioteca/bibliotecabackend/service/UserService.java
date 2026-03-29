package com.biblioteca.bibliotecabackend.service;

import com.biblioteca.bibliotecabackend.dto.UserDTO;
import com.biblioteca.bibliotecabackend.exception.BadRequestException;
import com.biblioteca.bibliotecabackend.exception.ResourceNotFoundException;
import com.biblioteca.bibliotecabackend.model.User;
import com.biblioteca.bibliotecabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));
        return toDTO(user);
    }

    public UserDTO createUser(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException(
                    "Email already registered: " + dto.getEmail());
        }
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        return toDTO(userRepository.save(user));
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));

        user.setName(dto.getName());
        user.setPhone(dto.getPhone());

        return toDTO(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    //  Mapper
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .registrationDate(user.getRegistrationDate())
                .build();
    }
}