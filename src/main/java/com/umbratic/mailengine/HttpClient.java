package com.umbratic.mailengine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private final String apiUrl;
    private final String bearerToken;
    private final ObjectMapper objectMapper;

    public HttpClient(String bearerToken, String apiUrl) {
        this.bearerToken = bearerToken;
        this.apiUrl = apiUrl;
        this.objectMapper = new ObjectMapper();
    }

    public String post(String endpoint, String jsonPayload) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl + endpoint);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + bearerToken);
            httpPost.setEntity(new StringEntity(jsonPayload));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseString = EntityUtils.toString(response.getEntity());

                if (statusCode >= 200 && statusCode < 300) {
                    return responseString;
                } else {
                    handleError(responseString);
                    throw new IOException("Error response from API: " + statusCode);
                }
            }
        }
    }

    private void handleError(String responseString) {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseString);
            String error = jsonNode.path("error").asText();
            String message = jsonNode.path("message").asText();
            logger.error("API Error - Error: {}, Message: {}", error, message);
        } catch (IOException e) {
            logger.error("Failed to parse error response: {}", responseString, e);
        }
    }
}
