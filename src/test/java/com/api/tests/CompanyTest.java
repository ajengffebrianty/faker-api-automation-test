package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.Address;
import model.Company;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class CompanyTest {
    private static final String pathCompany = "/companies";
    private RestAPIClient client;

    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }

    @Test
    public void statusCodeTest() {
        Company company = new Company();
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getCompanyWithSameSeedTest() {
        Company company = new Company();
        company.setQuantity("5");
        company.setSeed("100");
        Response response1 = client.fetchRequest(pathCompany, company.getQueryParams());
        Response response2 = client.fetchRequest(pathCompany, company.getQueryParams());
        List<Map<String, Object>> data1 = response1.jsonPath().getList("data");
        List<Map<String, Object>> data2 = response2.jsonPath().getList("data");
        Assert.assertEquals(data1, data2, "data's not match");

    }

    @Test
    public void validateCompanyAddressTest() {
        Company company = new Company();
        company.setQuantity("5");
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        Object companyAddress = response.jsonPath().get("data.addresses[0]");
        Assert.assertTrue(companyAddress instanceof ArrayList, "address is not a list");
        Assert.assertNotNull(companyAddress);
    }

    @Test
    public void validateCompanyLatitudeTest() {
        Company company = new Company();
        company.setQuantity("5");
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        Object latitude = response.jsonPath().get("data.addresses[0].latitude[0]");
        Assert.assertTrue(latitude instanceof Float, "Incorrect latitude type");
    }

    @Test
    public void validateCompanyLongitudeTest() {
        Company company = new Company();
        company.setQuantity("5");
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Object longitude = response.jsonPath().get("data.addresses[0].longitude[0]");
        Assert.assertTrue(longitude instanceof Float, "Incorrect longitude type");
    }

    @Test
    public void validateCompanyContactTest() {
        Company company = new Company();
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        Object companyContact = response.jsonPath().get("data.contact");
        Assert.assertNotNull(companyContact);
    }

    @Test
    public void validateAddressContactTest() {
        Company company = new Company();
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        Object AddressContact = response.jsonPath().get("data.contact.address");
        Assert.assertNotNull(AddressContact);
    }

    @Test
    public void validateContactLatitudeTest() {
        Company company = new Company();
        company.setQuantity("5");
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        Object latitude = response.jsonPath().get("data.contact[0].address.latitude");
        Assert.assertTrue(latitude instanceof Float, "Incorrect latitude type");
    }

    @Test
    public void validateContactLongitudeTest() {
        Company company = new Company();
        company.setQuantity("5");
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Object longitude = response.jsonPath().get("data.contact[0].address.longitude");
        Assert.assertTrue(longitude instanceof Float, "Incorrect longitude type");
    }

    @Test
    public void validateBirthdayFormatTest() {
        Company company = new Company();
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        List<String> birthday = response.jsonPath().getList("data.contact.birthday");
        for(String dateofbirth : birthday) {
            Assert.assertTrue(dateofbirth.matches("\\d{4}-\\d{2}-\\d{2}"),"Date is not in YYYY-MM-DD format");
        }
    }

    @Test
    public void validateCompanyEmailFormatTest() {
        Company company = new Company();
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        List<String> email = response.jsonPath().getList("data.email");
        for(String companyEmail : email) {
            Assert.assertTrue(companyEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"),"Invalid email format");
        }
    }

    @Test
    public void validateCompanyWebsiteFormatTest() {
        Company company = new Company();
        company.setLocale("id_ID");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        List<String> website = response.jsonPath().getList("data.website");
        for(String companyWebsite : website) {
            Assert.assertTrue(companyWebsite.matches("^https?:\\/\\/[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),"Invalid website format" + companyWebsite);
        }
    }

    @Test
    public void validateImageUrlFormatTest() {
        Company company = new Company();
        company.setLocale("fr_FR");
        Response response = client.fetchRequest(pathCompany, company.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
        List<String> image = response.jsonPath().getList("data.image");
        for(String companyImage : image) {
            Assert.assertTrue(companyImage.startsWith("http://placeimg.com/"));
        }
    }

}
