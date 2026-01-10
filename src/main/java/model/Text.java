package model;

import java.util.HashMap;
import java.util.Map;

public class Text extends Base{
    String characters;
    public Text() {
    }

    public String getCharacters() {
        return characters;
    }

    public void setCharacters(String characters) {
        this.characters = characters;
    }

    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        if (getQuantity() != null) {queryParams.put("_quantity", getQuantity());}
        if (getLocale() != null) {queryParams.put("_locale", getLocale());}
        if (getSeed() != null) {queryParams.put("_seed", getSeed());}
        if (getCharacters() != null) {queryParams.put("_characters", getCharacters());}
        return queryParams;
    }
}
