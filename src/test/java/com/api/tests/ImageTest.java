package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.CreditCard;
import model.Image;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class ImageTest {
    private static final String pathImage = "/images";
    private RestAPIClient client;

    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }

    @Test
    public void statusCodeTest() {
        Image image = new Image();
        Response response = client.fetchRequest(pathImage, image.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void validateWidthHeightFromParamTest() {
        Image image = new Image();
        image.setQuantity("5");
        image.setWidth("100");
        image.setHeight("200");
        Response response = client.fetchRequest(pathImage, image.getQueryParams());
        List<String> url = response.jsonPath().getList("data.url");
        for (String imageUrl : url) {
            String[] parts = imageUrl.split("/");
            int length = parts.length;
            String width = parts[length - 2];
            String height = parts[length - 1];
            Assert.assertEquals(width, image.getWidth());
            Assert.assertEquals(height, image.getHeight());
        }
    }


    @Test
    public void validateWidthHeightAnyTypeTest() {
        Image image = new Image();
        image.setQuantity("5");
        image.setType("any");
        Response response = client.fetchRequest(pathImage, image.getQueryParams());
        List<String> url = response.jsonPath().getList("data.url");
        for (String imageUrl : url) {
            String[] parts = imageUrl.split("/");
            int length = parts.length;
            String width = parts[length - 2];
            String height = parts[length - 1];
            Assert.assertEquals(width, "640");
            Assert.assertEquals(height, "480");
        }
    }

    @Test
    public void validateWidthHeightNullTypeTest() {
        Image image = new Image();
        image.setQuantity("5");
        image.setType(null);
        Response response = client.fetchRequest(pathImage, image.getQueryParams());
        List<String> url = response.jsonPath().getList("data.url");
        for (String imageUrl : url) {
            String[] parts = imageUrl.split("/");
            int length = parts.length;
            String width = parts[length - 2];
            String height = parts[length - 1];
            Assert.assertEquals(width, "640");
            Assert.assertEquals(height, "480");
        }
    }

    @Test
    public void validateWidthHeightnonExistingTypeTest() {
        Image image = new Image();
        image.setQuantity("5");
        image.setType("xyzz");
        Response response = client.fetchRequest(pathImage, image.getQueryParams());
        List<String> url = response.jsonPath().getList("data.url");
        for (String imageUrl : url) {
            String[] parts = imageUrl.split("/");
            int length = parts.length;
            String width = parts[length - 2];
            String height = parts[length - 1];
            Assert.assertEquals(width, "640");
            Assert.assertEquals(height, "480");
        }
    }

    @Test
    public void validateImageFormatAnyTypeTest() {
        Image image = new Image();
        image.setQuantity("5");
        image.setType("any");
        Response response = client.fetchRequest(pathImage, image.getQueryParams());
        List<String> url = response.jsonPath().getList("data.url");
        for(String imageUrl : url) {
            Assert.assertTrue(imageUrl.startsWith("https://picsum.photos/"));
        }
    }

    @Test
    public void validateImageFormatPokemonTypeTest() {
        Image image = new Image();
        image.setQuantity("5");
        image.setType("pokemon");
        Response response = client.fetchRequest(pathImage, image.getQueryParams());
        List<String> url = response.jsonPath().getList("data.url");
        for(String imageUrl : url) {
            Assert.assertTrue(imageUrl.startsWith("https://lorempokemon.fakerapi.it/pokemon/"));
        }
    }
}
