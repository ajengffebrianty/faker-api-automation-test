package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.Custom;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomTest {
    private RestAPIClient client;
    private static final String pathCustom = "/custom";
    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }

    @Test
    public void customColumnTest() {
        Custom custom = new Custom();
        custom.setName("name");
        custom.setNumber("number");
        custom.setEmail("email");
        custom.setCard_type("card_type");
        custom.setExpiry_date("card_expiration");

        Response response = client.fetchRequest(pathCustom, custom.getQueryParams());
        List<Map<String, Object>> dataList = response.jsonPath().getList("data");

        for (Map<String, Object> data : dataList) {
            Set<String> actualColumns = data.keySet();
            Assert.assertTrue(actualColumns.containsAll(custom.listCustomColumn()), "Missing columns. Actual columns: " + actualColumns);
        }
    }

    @Test
    public void validateDataCustomTest() {
        Custom custom = new Custom();
        custom.setName("name");
        custom.setNumber("number");
        custom.setEmail("email");
        custom.setCard_type("card_type");
        custom.setExpiry_date("card_expiration");

        Response response = client.fetchRequest(pathCustom, custom.getQueryParams());
        List<Map<String, Object>> dataList = response.jsonPath().getList("data");

        for (Map<String, Object> item : dataList) {
            for (Map.Entry<String, Object> entry : item.entrySet()) {
                Assert.assertNotNull(entry.getValue(), "Field '" + entry.getKey() + "' is null"
                );
            }
        }
    }

    @Test
    public void validateNonExistingCustomTest() {
        Custom custom = new Custom();
        custom.setName("name2");
        custom.setNumber("number2");
        custom.setEmail("email2");
        custom.setCard_type("card_type2");
        custom.setExpiry_date("card_expiration2");

        Response response = client.fetchRequest(pathCustom, custom.getQueryParams());
        List<Map<String, Object>> dataList = response.jsonPath().getList("data");

        for (Map<String, Object> item : dataList) {
            for (Map.Entry<String, Object> entry : item.entrySet()) {
                Assert.assertNull(entry.getValue(), "Field '" + entry.getKey() + "' not null"
                );
            }
        }
    }
}
