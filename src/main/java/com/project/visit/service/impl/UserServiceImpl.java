package com.project.visit.service.impl;

import com.project.visit.model.Doctor;
import com.project.visit.model.Role;
import com.project.visit.model.User;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.RoleRepository;
import com.project.visit.repository.UserRepository;
import com.project.visit.service.AuthService;
import com.project.visit.service.UserService;
import com.project.visit.service.model.UserCreationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final DoctorRepository doctorRepository;

    private final AuthService authService;

    @Override
    public void createUser(UserCreationModel model) {
        var optionalUser = userRepository.findByPhone(model.getPhone());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            checkPassword(model.getPassword(), user);
            addRole("USER", user);
        } else {
            createNewUser(model);
        }
    }

    @Override
    public void createDoctor(UserCreationModel model) {
        var user = userRepository.findByPhone(model.getPhone()).orElse(null);
        if (user != null) {
            checkPassword(model.getPassword(), user);
            user = addRole("DOCTOR", user);
            createNewDoctor(user, model);
        } else {
            user = createNewUser(model);
            createNewDoctor(user, model);
        }
    }

    private User addRole(String roleName, User user) {
        if (!user.getRoles().stream().map(Role::getName).toList().contains(roleName)) {
            user.getRoles().add(roleRepository.findByName(roleName));
            return userRepository.save(user);
        }
        return user;
    }

    private User createNewUser(UserCreationModel model) {
        var user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(model.getName());
        user.setFamily(model.getFamily());
        user.setPhone(model.getPhone());
        user.setPassword(model.getPassword());

        var role = roleRepository.findByName("USER");
        user.setRoles(List.of(role));

        return userRepository.save(user);
    }

    private void createNewDoctor(User user, UserCreationModel model) {
        var doctorOptional = doctorRepository.findByMedicalCode(model.getMedicalCode());
        if (doctorOptional.isEmpty()) {
            var doctor = new Doctor();
            doctor.setUserId(user.getUserId());
            doctor.setName(model.getName());
            doctor.setMedicalCode(model.getMedicalCode());
            doctor.setFamily(model.getFamily());
            doctorRepository.save(doctor);
        }
    }

    private void checkPassword(String pass, User user) {
        if (!user.getPassword().equals(pass)) {
            throw new IllegalArgumentException();
        }
    }
}
