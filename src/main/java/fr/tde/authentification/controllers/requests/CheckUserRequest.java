package fr.tde.authentification.controllers.requests;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckUserRequest {

    private String username;
    private String password;

}
