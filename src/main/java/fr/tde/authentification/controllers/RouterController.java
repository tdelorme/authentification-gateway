package fr.tde.authentification.controllers;

import fr.tde.authentification.controllers.requests.RouteRequest;
import fr.tde.authentification.services.RouterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class RouterController {

    @Autowired
    RouterService routerService;

    @PostMapping("/route")
    public ResponseEntity route(@RequestBody RouteRequest request, @RequestParam(required = false) Map<String, String> paramRequest) throws IOException {

        return routerService.route(request.getKeyToRoute(), request.getObjectRequest(), paramRequest);
    }

}
