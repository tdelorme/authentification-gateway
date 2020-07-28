package fr.tde.authentification.controllers.requests;

import lombok.Data;

@Data
public class RouteRequest {

    private String keyToRoute;
    private Object objectRequest;

}
