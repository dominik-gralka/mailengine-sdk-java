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

/**
 * HttpClient is a utility class for sending HTTP POST requests to a specified API.
 */
public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private final String apiUrl;
    private final String bearerToken;
    private final ObjectMapper objectMapper;

    /**
     * Constructs an HttpClient instance with the given bearer token and API URL.
     *
     * @param bearerToken the bearer token used for authorization
     * @param apiUrl      the base URL of the API
     */
    public HttpClient(String bearerToken, String apiUrl) {
        this.bearerToken = bearerToken;
        this.apiUrl = apiUrl;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends an HTTP POST request to the specified endpoint with the provided JSON payload.
     *
     * @param endpoint    the endpoint to which the POST request will be sent
     * @param jsonPayload the JSON payload to be included in the POST request
     * @return the response string from the API if the request is successful
     * @throws IOException if an I/O error occurs or if the API response indicates a failure
     */
    public String post(String endpoint, String jsonPayload) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl + endpoint);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + bearerToken);
            httpPost.setEntity(new StringEntity(jsonPayload));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                // Log sending attempt
                JsonNode jsonNodePayload = objectMapper.readTree(jsonPayload);
                String senderEmail = jsonNodePayload.path("senderEmail").asText();
                logger.info("Attempting send action to: {}", senderEmail);

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

    /**
     * Handles API error responses by logging the error details.
     *
     * @param responseString the response string containing error details
     */
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
