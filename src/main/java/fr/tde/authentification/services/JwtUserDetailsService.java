package fr.tde.authentification.services;

import fr.tde.authentification.controllers.requests.CheckUserRequest;
import fr.tde.authentification.controllers.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        RestTemplate restTemplate = new RestTemplate();
        UserResponse userResponse = restTemplate.postForObject("http://localhost:8081/users/username", new CheckUserRequest(username, null), UserResponse.class);

        if(userResponse != null && userResponse.getUsername() != null && userResponse.getPassword() != null) {
            return new User(userResponse.getUsername(), userResponse.getPassword(), new ArrayList<>());
        }

        if ("admin".equals(username))
        {
            return new User("admin", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}