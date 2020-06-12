package ml.peya.plugins.Utils;

import java.util.*;

public class LeetConverter
{

    public static String convert(String str)
    {
        String target = str;

        Random random = new Random();

        if (!random.nextBoolean())
            return str;

        HashMap<String, String> leet = getLeets();

        for (String keys: leet.keySet())
        {
            String value = leet.get(keys);

            target = target.replace(keys.toUpperCase(), value.toUpperCase());
            target = target.replace(keys.toLowerCase(), value.toLowerCase());
        }

        return target;
    }

    private static HashMap<String, String> getLeets()
    {
        HashMap<String, String> leets = new HashMap<>();
        leets.put("a", "4");
        leets.put("e", "4");
        leets.put("g", "4");
        leets.put("i", "4");
        leets.put("o", "4");
        leets.put("s", "4");
        leets.put("t", "4");
        return leets;
    }
}
