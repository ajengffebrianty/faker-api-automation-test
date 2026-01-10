package model;

import java.util.HashMap;
import java.util.Map;

public class Image extends Base {
    String type;
    String width;
    String height;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Image  (){}

    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        if (getQuantity() != null) {queryParams.put("_quantity", getQuantity());}
        if (getLocale() != null) {queryParams.put("_locale", getLocale());}
        if (getSeed() != null) {queryParams.put("_seed", getSeed());}
        if (getType() != null) {queryParams.put("_type", getType());}
        if (getWidth() != null) {queryParams.put("_width", getWidth());}
        if (getHeight() != null) {queryParams.put("_height", getHeight());}

        return queryParams;
    }
}
