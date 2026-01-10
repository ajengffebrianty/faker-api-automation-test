package model;

import java.util.HashMap;
import java.util.Map;

public class Person extends Base {
    String gender;
    String birthday_start;
    String birthday_end;

    public Person() {}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday_start() {
        return birthday_start;
    }

    public void setBirthday_start(String birthday_start) {
        this.birthday_start = birthday_start;
    }

    public String getBirthday_end() {
        return birthday_end;
    }

    public void setBirthday_end(String birthday_end) {
        this.birthday_end = birthday_end;
    }

    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        if (getQuantity() != null) {queryParams.put("_quantity", getQuantity());}
        if (getLocale() != null) {queryParams.put("_locale", getLocale());}
        if (getSeed() != null) {queryParams.put("_seed", getSeed());}
        if (getGender() != null) {queryParams.put("_gender", getGender());}
        if (getBirthday_start() != null) {queryParams.put("_birthday_start", getBirthday_start());}
        if (getBirthday_end() != null) {queryParams.put("_birthday_end", getBirthday_end());}

        return queryParams;
    }
}
