package fr.tde.authentification.models.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class Role {


    private Long id;
    private String name;
    private Set<User> users;

}
