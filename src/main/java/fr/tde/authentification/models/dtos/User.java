package fr.tde.authentification.models.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
