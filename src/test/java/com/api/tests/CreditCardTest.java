package com.api.tests;

import client.RestAPIClient;
import io.restassured.response.Response;
import model.CreditCard;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class CreditCardTest {
    private static final String pathCreditCard = "/creditCards";
    private RestAPIClient client;

    @BeforeTest
    void setup() {
        client = new RestAPIClient();
    }

    @Test
    public void statusCodeTest() {
        CreditCard creditcard = new CreditCard();
        Response response = client.fetchRequest(pathCreditCard, creditcard.getQueryParams());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void validateExpireDateFormatTest() {
        CreditCard creditcard = new CreditCard();
        Response response = client.fetchRequest(pathCreditCard, creditcard.getQueryParams());
        List<String> expireDate = response.jsonPath().getList("data.expiration");
        for(String expire : expireDate) {
            String[] parts = expire.split("/");
            Assert.assertEquals(parts.length, 2, "Expiry format should be MM/YY");
            int month = Integer.parseInt(parts[0]);
            Assert.assertTrue(month >= 1 && month <= 12, "Invalid expiry month: " + month
            );
        }
    }

    @Test
    public void validateCardTypeTest() {
        CreditCard creditcard = new CreditCard();
        creditcard.setQuantity("5");
        Response response = client.fetchRequest(pathCreditCard, creditcard.getQueryParams());
        List<String> cardType = response.jsonPath().getList("data.type");
        for(String card : cardType) {
            Assert.assertNotNull(card);
        }
    }

    @Test
    public void validateCardNameTest() {
        CreditCard creditcard = new CreditCard();
        creditcard.setQuantity("5");
        Response response = client.fetchRequest(pathCreditCard, creditcard.getQueryParams());
        List<String> cardHolder = response.jsonPath().getList("data.owner");
        for(String owner : cardHolder) {
            Assert.assertNotNull(owner);
        }
    }

    @Test
    public void validateCardNumberTest() {
        CreditCard creditcard = new CreditCard();
        creditcard.setQuantity("5");
        Response response = client.fetchRequest(pathCreditCard, creditcard.getQueryParams());
        List<String> cardNumber = response.jsonPath().getList("data.number");
        for (String number : cardNumber) {
            Assert.assertTrue( number.matches("\\d+"), "Card number contains invalid characters: " + cardNumber);
        }
    }
}
