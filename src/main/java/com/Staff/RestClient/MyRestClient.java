package com.Staff.RestClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MyRestClient {

    public ResponseEntity<String> sendDataToServer(Object data) {
        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity with data and headers
        HttpEntity<Object> requestEntity = new HttpEntity<>(data, headers);

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make POST request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8083/api/staff/bulk", requestEntity, String.class);

        // Return response
        return responseEntity;
    }
}
