package fr.tde.authentification.controllers.responses;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String password;
    private String email;

}
