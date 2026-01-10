package model;

import java.util.HashMap;
import java.util.Map;

public class Address extends Base {
    String countryCode;

    public Address() {}

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        if (getQuantity() != null) {queryParams.put("_quantity", getQuantity());}
        if (getLocale() != null) {queryParams.put("_locale", getLocale());}
        if (getSeed() != null) {queryParams.put("_seed", getSeed());}
        if (getCountryCode() != null) {queryParams.put("_country_code", getCountryCode());}

        return queryParams;
    }
}
