package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.Product;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductTest {
    private static final String pathProduct = "/products";
    private RestAPIClient client;

    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }

    @Test
    public void statusCodeTest() {
        Product product = new Product();
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void validateImageProductFormatTest() {
        Product product = new Product();
        product.setQuantity("5");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<String> url = response.jsonPath().getList("data.image");
        for (String imageUrl : url) {
            Assert.assertTrue(imageUrl.startsWith("http://placeimg.com"));
        }
    }

    @Test
    public void validateImageListTest() {
        Product product = new Product();
        product.setQuantity("5");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        Object images = response.jsonPath().get("data.images");
        Assert.assertTrue(images instanceof ArrayList, "address is not a list");
        Assert.assertNotNull(images);
    }

    @Test
    public void validateImagesListFormatTest() {
        Product product = new Product();
        product.setQuantity("5");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<List<String>> urls = response.jsonPath().getList("data.images.url");
        for (List<String> imageRow : urls) {
            for (String imageUrl : imageRow) {
                Assert.assertNotNull(imageUrl, "URL should not be null");
                Assert.assertTrue(imageUrl.startsWith("https://picsum.photos/"));
            }
        }
    }

    @Test
    public void priceRangeTest() {
        Product product = new Product();
        product.setQuantity("5");
        product.setPriceMin("0.1");
        product.setPriceMax("0.5");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<Float> priceRange = response.jsonPath().getList("data.net_price");
        for (Float price : priceRange) {
            Float priceRangeMin = Float.parseFloat(product.getPriceMin());
            Float priceRangeMax = Float.parseFloat(product.getPriceMax());
            Assert.assertTrue(price >= priceRangeMin && price <= priceRangeMax);

        }


    }

    @Test
    public void validateTaxFromParamTest() {
        Product product = new Product();
        product.setQuantity("5");
        product.setTaxes("8.2");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<Number> taxes = response.jsonPath().getList("data.taxes");
        for (Number tax : taxes) {
            Assert.assertEquals(tax.floatValue(), Float.parseFloat(product.getTaxes()));
        }

    }

    @Test
    public void defaultTaxTest() {
        Product product = new Product();
        product.setQuantity("5");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<Number> taxes = response.jsonPath().getList("data.taxes");
        for (Number tax : taxes) {
            Assert.assertEquals(tax.floatValue(), 22);
        }
    }

    @Test
    public void validateCategoriesTest() {
        Product product = new Product();
        product.setQuantity("5");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        Object categories = response.jsonPath().getList("data.categories[0]");
        Assert.assertTrue(categories instanceof ArrayList, "categories is not a list");
        Assert.assertNotNull(categories);

    }

    @Test
    public void defaultCategoriesTest() {
        Product product = new Product();
        product.setQuantity("5");
        product.setCategories(null);
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<List<Number>> categories = response.jsonPath().getList("data.categories");
        for (List<Number> listCategory : categories) {
            for (Number category  : listCategory) {
                Assert.assertTrue(category instanceof Integer, "Value is not integer: ");
            }
        }

    }

    @Test
    public void uuidCategoriesTest() {
        Product product = new Product();
        product.setQuantity("5");
        product.setCategories("uuid");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<List<String>> categories = response.jsonPath().getList("data.categories");
        for (List<String> listCategory : categories) {
            for (String category  : listCategory) {
                try {
                    UUID.fromString(category);
                } catch (IllegalArgumentException e) {
                    Assert.fail("Invalid UUID format: " + category);
                }
            }
        }
    }

    @Test
    public void StringCategoriesTest() {
        Product product = new Product();
        product.setQuantity("5");
        product.setCategories("uuid");
        Response response = client.fetchRequest(pathProduct, product.getQueryParams());
        List<List<String>> categories = response.jsonPath().getList("data.categories");
        for (List<String> listCategory : categories) {
            for (String category  : listCategory) {
               Assert.assertTrue(category instanceof  String, "Value is not a string");
            }
        }
    }

}
