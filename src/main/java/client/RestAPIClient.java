package client;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class RestAPIClient {

    public Response fetchRequest(String path, Map<String, ?> queryParam) {
        RestAssured.baseURI = "https://fakerapi.it/api/v2";
        Response response;
        response = RestAssured.given()
                .queryParams(queryParam)
                .when().get(path);

        return response;
    }
}
