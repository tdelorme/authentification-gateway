package fr.tde.authentification.controllers.requests;

import lombok.Data;

@Data
public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private String email;

}
