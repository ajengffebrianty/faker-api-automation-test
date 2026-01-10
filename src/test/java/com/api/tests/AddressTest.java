package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.Address;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class AddressTest {
    private static final String pathAddress = "/addresses";
    private RestAPIClient client;

    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }

    @Test
    public void statusCodeTest() {
        Address address = new Address();
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void fiveQuantityTest() {
        Address address = new Address();
        address.setQuantity("5");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        int count = response.jsonPath().getList("data").size();
        Assert.assertEquals(count, 5);
    }

    @Test
    public void nullQuantityTest() {
        Address address = new Address();
        address.setQuantity(null);
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        int count = response.jsonPath().getList("data").size();
        Assert.assertEquals(count, 10);
    }

    @Test
    public void minusQuantityTest() {
        Address address = new Address();
        address.setQuantity("-1");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        int count = response.jsonPath().getList("data").size();
        Assert.assertEquals(count, 10);
    }

    @Test
    public void maxQuantityTest() {
        Address address = new Address();
        address.setQuantity("1000");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        int count = response.jsonPath().getList("data").size();
        Assert.assertEquals(count, 1000);
    }

    @Test
    public void exceedQuantityTest() {
        Address address = new Address();
        address.setQuantity("1001");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        int count = response.jsonPath().getList("data").size();
        Assert.assertEquals(count, 1000);
    }

    @Test
    public void alphabetQuantityTest() {
        Address address = new Address();
        address.setQuantity("abc");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        int count = response.jsonPath().getList("data").size();
        Assert.assertEquals(count, 10);
    }

    @Test
    public void defaultLocaleTest() {
        Address address = new Address();
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        String locale = response.jsonPath().getString("locale");
        Assert.assertEquals(locale, "en_US");
    }

    @Test
    public void customLocaleTest() {
        Address address = new Address();
        address.setLocale("fr_FR");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        String locale = response.jsonPath().getString("locale");
        Assert.assertEquals(locale, "fr_FR");
    }

    @Test
    public void nonExistingLocaleTest() {
        Address address = new Address();
        address.setLocale("xyz");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        String locale = response.jsonPath().getString("locale");
        Assert.assertEquals(locale, "en_US");
    }

    @Test
    public void getAddressWithSameSeedTest() {
        Address address = new Address();
        address.setQuantity("5");
        address.setSeed("100");
        Response response1 = client.fetchRequest(pathAddress, address.getQueryParams());
        Response response2 = client.fetchRequest(pathAddress, address.getQueryParams());
        List<Map<String, Object>> data1 =
                response1.jsonPath().getList("data");
        List<Map<String, Object>> data2 =
                response2.jsonPath().getList("data");
        Assert.assertEquals(data1, data2);

    }

    @Test
    public void countryInCountryCodeTest() {
        Address address = new Address();
        address.setCountryCode("JP");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        List<String> countryCodes = response.jsonPath().getList("data.country_code");
        List<String> countries = response.jsonPath().getList("data.country");
        for (String country_code : countryCodes) {
            Assert.assertEquals(country_code, "JP");
        }
        for (String country : countries) {
            Assert.assertEquals(country, "Japan");
        }
    }

    @Test
    public void nonExistingCountryInCountryCodeTest() {
        Address address = new Address();
        address.setCountryCode("abcd");
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        List<String> countries = response.jsonPath().getList("data.country");
        for (String country : countries) {
            Assert.assertEquals(country, "");
        }
    }

    @Test
    public void validateTypeLatLangTest() {
        Address address = new Address();
        Response response = client.fetchRequest(pathAddress, address.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        Object latitude = response.jsonPath().get("data.latitude[0]");
        Assert.assertTrue(latitude instanceof Float);
    }
}
