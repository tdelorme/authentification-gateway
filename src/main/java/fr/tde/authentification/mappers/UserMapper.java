package fr.tde.authentification.mappers;

import fr.tde.authentification.controllers.requests.UserRequest;
import fr.tde.authentification.controllers.responses.UserResponse;
import fr.tde.authentification.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse convertToDto(User userCreated) {
        UserResponse response = modelMapper.map(userCreated, UserResponse.class);
        return response;
    }

    public User convertToEntity(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        return user;
    }
}
