package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class RestAPIClient {
    private static final String BASE_URI = "https://fakerapi.it/api/v2";

    public Response fetchRequest(String path, Map<String, ?> queryParam) {
        RestAssured.baseURI = BASE_URI;
        
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Response response = RestAssured.given()
                .queryParams(queryParam)
                .when().get(path);

        return response;
    }
}
