package fr.tde.authentification.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Service
@Slf4j
public class RouterService {

    private static final String GATEWAY_PROPERTIES = "routing.properties";
    private static final String METHOD = ".method";
    private Properties gatewayProperty;

    @PostConstruct
    public void init() throws IOException {

        gatewayProperty = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(GATEWAY_PROPERTIES);
        if (inputStream != null) {
            try {
                gatewayProperty.load(inputStream);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            } finally {
                inputStream.close();
            }
        } else {
            throw new FileNotFoundException("property file '" + GATEWAY_PROPERTIES + "' not found in the classpath");
        }
    }

    public ResponseEntity<?> route(String keyToRoute, Object objectRequest, Map<String, ?> paramRequest) {

        String link = gatewayProperty.getProperty(keyToRoute);
        String methodToCallWS = gatewayProperty.getProperty(keyToRoute + METHOD);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = new ResponseEntity(HttpStatus.OK);

        switch (methodToCallWS) {
            case "GET":
                response = restTemplate.getForEntity(link, Object.class, paramRequest);
                break;
            case "POST":
                response = restTemplate.postForEntity(link, objectRequest, Object.class, paramRequest);
                break;
            case "PUT":
                restTemplate.put(link, objectRequest, paramRequest);
                break;
            case "DELETE":
                restTemplate.delete(link, paramRequest);
                break;
            default:
                restTemplate.getForObject(link, Object.class, paramRequest);
        }

        return response;
    }
}