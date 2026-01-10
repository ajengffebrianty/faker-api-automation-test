package model;

import client.RestAPIClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Custom extends Base {
    public Custom() {}

    String name;
    String number;
    String email;

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    String card_type;
    String expiry_date;

    public List<String> listCustomColumn() {
        List<String> listCustomColumn = new ArrayList<>();
        listCustomColumn.add("fullName");
        listCustomColumn.add("number");
        listCustomColumn.add("email");
        listCustomColumn.add("cardType");
        listCustomColumn.add("expiryDate");

        return listCustomColumn;
    }
    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        if (getQuantity() != null) {queryParams.put("_quantity", getQuantity());}
        if (getLocale() != null) {queryParams.put("_locale", getLocale());}
        if (getSeed() != null) {queryParams.put("_seed", getSeed());}
        if (getName() != null) {queryParams.put("fullName", getName());}
        if (getNumber() != null) {queryParams.put("number", getNumber());}
        if (getEmail() != null) {queryParams.put("email", getEmail());}
        if (getCard_type() != null) {queryParams.put("cardType", getCard_type());}
        if (getExpiry_date() != null) {queryParams.put("expiryDate", getExpiry_date());}
        return queryParams;
    }
}
