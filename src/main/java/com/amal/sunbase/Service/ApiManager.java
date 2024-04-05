package com.amal.sunbase.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiManager {

    private final String tokenUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
    private final String customerListApi = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

    // Method to obtain token and customer data from APIs
    public Object[] getTokenAndCustomers() {
        // Constructing request body for obtaining token
        String requestBody = "{ \"login_id\": \"test@sunbasedata.com\", \"password\": \"Test@123\" }";
        // Getting token from token URL
        String accessToken = getToken(tokenUrl, requestBody);

        // Getting customer data using obtained token
        List<Object> customers = getCustomers(accessToken, customerListApi);

        // Returning customer data as an array
        return customers.toArray();
    }

    // Method to obtain token from token URL
    private String getToken(String apiUrl, String requestBody) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return responseEntity.getBody().substring(19, responseEntity.getBody().length() - 3);
    }

    // Method to fetch customer data using token
    private List<Object> getCustomers(String token, String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Object[]> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                Object[].class
        );
        return List.of(responseEntity.getBody());
    }
}

