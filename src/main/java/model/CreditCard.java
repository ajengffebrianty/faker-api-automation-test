package model;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.Map;

public class CreditCard extends Base {
    public CreditCard() {}

    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();
        if (getQuantity() != null) {queryParams.put("_quantity", getQuantity());}
        if (getLocale() != null) {queryParams.put("_locale", getLocale());}
        if (getSeed() != null) {queryParams.put("_seed", getSeed());}

        return queryParams;
    }
}
