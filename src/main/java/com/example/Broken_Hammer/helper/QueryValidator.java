package com.example.Broken_Hammer.helper;

import com.example.Broken_Hammer.filter.QueryFilter;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryValidator {
    private static final Logger logger = Logger.getLogger(QueryFilter.class);
    private static final Map<String, String> SQPMap = new HashMap<>();

    static {
        SQPMap.put("page", "^[0-9]*$");
        SQPMap.put("order", "\\b(asc|desc)\\b");
        SQPMap.put("sort", "\\b(date|performance_status|payment_status|price)\\b");
        SQPMap.put("sort_by", "\\b(orders_amount|rating)\\b");
        SQPMap.put("performance", "\\b(1|2|3|4)\\b");
        SQPMap.put("payment", "\\b(1|2|3)\\b");
        SQPMap.put("worker", "\\d+");
        SQPMap.put("orderID", "\\d+");
    }

    public static boolean queryStringValidator(Map<String, String> queryString) {

        for (String queryKey : queryString.keySet()) {
            boolean exist = false;
            for (String sqp : SQPMap.keySet()) {

                if (queryKey.equals(sqp)) {
                    exist = true;
                    Pattern pattern = Pattern.compile(SQPMap.get(sqp));
                    Matcher matcher = pattern.matcher(queryString.get(queryKey));

                    if (!matcher.find()) {
                        logger.error("Wrong value " + queryString.get(queryKey) + " for parameter " + queryKey + " was typed!");
                        return false;
                    }
                }
            }
            if (!exist) {
                logger.error("Parameter " + queryKey + " does not exist!");
                return false;
            }
        }
        return true;
    }
}
