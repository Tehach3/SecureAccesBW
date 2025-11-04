package com.snowhorse.secureBW.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String, Object>> obtenerPostsExternos() {
        String url = "https://jsonplaceholder.typicode.com/posts";
        try {
            Map<String, Object>[] response = restTemplate.getForObject(url, Map[].class);
            return Arrays.asList(response);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al consumir la API externa: " + e.getMessage());
        }
    }
}