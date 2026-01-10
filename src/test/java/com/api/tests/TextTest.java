package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.Text;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TextTest {
    private static final String pathText = "/texts";
    private RestAPIClient client;

    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }


    @Test
    public void statusCodeTest() {
        Text text = new Text();
        Response response = client.fetchRequest(pathText, text.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void defaultCharactersTest() {
        Text text = new Text();

        Response response = client.fetchRequest(pathText, text.getQueryParams());
        List<String> characters = response.jsonPath().getList("data.content");
        for (String character : characters){
            Integer expected = 200;
            Integer tolerance = 10;
            Assert.assertTrue(
                    character.length() >= (expected - tolerance) && character.length() <= (expected + tolerance), "character not within tolerance limit"
            );
        }
    }

    @Test
    public void zeroCharactersTest() {
        Text text = new Text();
        text.setCharacters("0");
        Response response = client.fetchRequest(pathText, text.getQueryParams());
        List<String> characters = response.jsonPath().getList("data.content");
        for (String character : characters){
        Assert.assertTrue(character.length() >0);

        }
    }

    @Test
    public void fiftyCharactersTest() {
        Text text = new Text();
        text.setCharacters("50");
        Response response = client.fetchRequest(pathText, text.getQueryParams());
        List<String> characters = response.jsonPath().getList("data.content");
        for (String character : characters){
            Integer expected = Integer.parseInt(text.getCharacters());
            Integer tolerance = 10;
            Assert.assertTrue(
                    character.length() >= (expected - tolerance) && character.length() <= (expected + tolerance), "character not within tolerance limit"
            );;
        }
    }

    @Test
    public void thousandCharactersTest() {
        Text text = new Text();
        text.setCharacters("1000");
        Response response = client.fetchRequest(pathText, text.getQueryParams());
        List<String> characters = response.jsonPath().getList("data.content");
        for (String character : characters){
            Integer expected = Integer.parseInt(text.getCharacters());
            Integer tolerance = 10;
            Assert.assertTrue(
                    character.length() >= (expected - tolerance) && character.length() <= (expected + tolerance), "character not within tolerance limit"
            );;
        }
    }

}
