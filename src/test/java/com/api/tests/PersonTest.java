package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.Company;
import model.Image;
import model.Person;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

public class PersonTest {
    private static final String pathPerson = "/persons";
    private RestAPIClient client;

    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }

    @Test
    public void statusCodeTest() {
        Person person = new Person();
        Response response = client.fetchRequest(pathPerson, person.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void maleGenderTest() {
        Person person = new Person();
        person.setGender("male");
        Response response = client.fetchRequest(pathPerson, person.getQueryParams());
        List<String> genderList = response.jsonPath().getList("data.gender");
        for (String gender: genderList) {
            Assert.assertEquals(gender, "male");
        }
    }

    @Test
    public void femaleGenderTest() {
        Person person = new Person();
        person.setGender("female");
        Response response = client.fetchRequest(pathPerson, person.getQueryParams());
        List<String> genderList = response.jsonPath().getList("data.gender");
        for (String gender: genderList) {
            Assert.assertEquals(gender, "female");
        }
    }

    @Test
    public void validateBirthdayFormatTest() {
        Person person = new Person();
        person.setQuantity("5");
        Response response = client.fetchRequest(pathPerson, person.getQueryParams());
        String birthday = response.jsonPath().getString("data.birthday[0]");
        System.out.println("Birthday: " + birthday);
        Assert.assertTrue(birthday.matches("\\d{4}-\\d{2}-\\d{2}"),"Date is not in YYYY-MM-DD format");
    }

    @Test
    public void validateRangeBirthdayTest() {
        Person person = new Person();
        person.setQuantity("20");
        person.setBirthday_start("2015-05-05");
        person.setBirthday_end("2015-05-07");
        Response response = client.fetchRequest(pathPerson, person.getQueryParams());
        List<String> birthday = response.jsonPath().getList("data.birthday");

        LocalDate startDate = LocalDate.parse(person.getBirthday_start());
        LocalDate endDate   = LocalDate.parse(person.getBirthday_end());
        for (String dateString: birthday) {
            LocalDate date = LocalDate.parse(dateString);
            System.out.println(date);
            Assert.assertTrue(
                    !date.isBefore(startDate) && !date.isAfter(endDate),
                    "Date " + date + " is out of range " + startDate + " - " + endDate
            );

        }

    }

    @Test
    public void validateAddressTest() {
        Person person = new Person();
        person.setLocale("id_ID");
        Response response = client.fetchRequest(pathPerson, person.getQueryParams());
        Object address = response.jsonPath().get("data.address");
        Assert.assertNotNull(address);}
}
