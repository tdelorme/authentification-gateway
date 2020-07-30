package fr.tde.authentification.controllers;

import fr.tde.authentification.configurations.JwtTokenUtil;
import fr.tde.authentification.controllers.requests.CheckUserRequest;
import fr.tde.authentification.controllers.responses.BooleanResponse;
import fr.tde.authentification.models.JwtRequest;
import fr.tde.authentification.models.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
    {
        if(authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword())) {
            final UserDetails userDetails = new User(authenticationRequest.getUsername(), authenticationRequest.getPassword(), new ArrayList<>());

            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        else{
            return ResponseEntity.badRequest().body("INVALID CREDENTIAL");
        }

    }

    private boolean authenticate(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        BooleanResponse response = restTemplate.postForObject("http://localhost:8081/users/check",new CheckUserRequest(username, password),BooleanResponse.class);
        if( response != null )
            return response.getResponse();
        else {
            log.error("Service user not reachable !");
            return false;
        }
    }

}
