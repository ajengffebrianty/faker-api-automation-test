package model;

import java.util.HashMap;
import java.util.Map;

public class Product extends Base{
    String priceMin;
    String priceMax;
    String taxes;
    String categories;

    public Product() {}

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        if (getQuantity() != null) {queryParams.put("_quantity", getQuantity());}
        if (getLocale() != null) {queryParams.put("_locale", getLocale());}
        if (getSeed() != null) {queryParams.put("_seed", getSeed());}
        if (getPriceMin() != null) {queryParams.put("_price_min", getPriceMin());}
        if (getPriceMax() != null) {queryParams.put("_price_max", getPriceMax());}
        if (getTaxes() != null) {queryParams.put("_taxes", getTaxes());}
        if (getCategories() != null) {queryParams.put("_categories_type", getCategories());}

        return queryParams;
    }
}
