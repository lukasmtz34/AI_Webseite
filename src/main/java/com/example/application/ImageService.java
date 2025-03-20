package com.example.application;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
class ImageService {
  private static final String API_URL = "https://api.vyro.ai/v2/image/generations";
  private static final String API_KEY = "vk-8J7BAQIW2TU11H2Jv2m03wyBn2MQA0AVkuRQ57OD2O065"; // Setze hier deinen API-Key ein
  private final RestTemplate restTemplate = new RestTemplate();

  public byte[] generateImage(String prompt, String style, String styleId) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + API_KEY);
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("prompt", prompt);
    body.add("style", style);
    body.add("style_id", styleId);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<byte[]> response = restTemplate.postForEntity(API_URL, requestEntity, byte[].class);

    return response.getBody();
  }

  public void saveImage(byte[] bytes, String prompt) {

    try {
      Files.write(Paths.get("C:\\Users\\lukas.matzelt\\Documents\\Rando-Projects\\AI-Image-Generator\\src\\main\\resources\\images\\"+prompt+".jpg"),bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
