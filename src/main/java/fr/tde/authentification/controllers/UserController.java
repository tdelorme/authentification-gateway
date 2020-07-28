package fr.tde.authentification.controllers;

import fr.tde.authentification.controllers.requests.UserRequest;
import fr.tde.authentification.controllers.responses.UserResponse;
import fr.tde.authentification.mappers.UserMapper;
import fr.tde.authentification.models.User;
import fr.tde.authentification.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/create")
    public UserResponse create(@RequestBody UserRequest userRequest) {
        User user = userMapper.convertToEntity(userRequest);
        User userCreated = userService.upsertUser(user);
        return userMapper.convertToDto(userCreated);
    }

    @GetMapping("/get")
    public UserResponse getById(@RequestParam("id") Long id){
        UserResponse response = new UserResponse();
        try {
            response = userMapper.convertToDto(userService.getUserById(id));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/{username}/{password}")
    public UserResponse getByUsernameAndPassword(@PathVariable("username") String username, @PathVariable("password") String password) {
        UserResponse response = new UserResponse();
        try {
            response = userMapper.convertToDto(userService.getUserByUsernameAndPassword(username, password));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
}
