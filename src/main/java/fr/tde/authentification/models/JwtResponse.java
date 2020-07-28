package fr.tde.authentification.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable
{

    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
}
