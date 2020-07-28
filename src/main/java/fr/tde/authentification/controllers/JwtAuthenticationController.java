package fr.tde.authentification.controllers;

import fr.tde.authentification.configurations.JwtTokenUtil;
import fr.tde.authentification.models.JwtRequest;
import fr.tde.authentification.models.JwtResponse;
import fr.tde.authentification.models.User;
import fr.tde.authentification.services.JwtUserDetailsService;
import fr.tde.authentification.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception
    {
        if(authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword())) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());


            final String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        else{
            return ResponseEntity.badRequest().body("INVALID CREDENTIAL");
        }

    }

    private boolean authenticate(String username, String password) throws Exception {
        try {
           User user = userService.getUserByUsernameAndPassword(username, password);
           if( user != null) {
               return true;
           }
           else{
               return false;
           }
        } catch (NotFoundException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
