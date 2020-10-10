package ml.peya.plugins.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 時間をStringとかから変換
 */
public class TimeParser
{

    static String regex = "^[0-9]+((year|y)|(month|mo)|(day|d)|(hour|h)|(minute|min|m)|(second|sec|s))(s)?$";

    /**
     * 114514d 334mo みたいのを変換
     *
     * @param args 文字セット
     * @return 変換したやつ
     */
    public static Date convert(String... args)
    {
        Calendar c = Calendar.getInstance();

        for (String arg : args)
        {
            Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(arg);

            if (!m.find())
                continue;

            String unit = m.group(1);

            int num;
            try
            {
                num = Integer.parseInt(arg.replace(unit, "").replace("s", ""));
            }
            catch (Exception ignored)
            {
                continue;
            }

            switch (unit.toLowerCase())
            {
                case "years":
                case "year":
                case "y":
                    c.add(Calendar.YEAR, num);
                    break;
                case "months":
                case "month":
                case "mo":
                    c.add(Calendar.MONTH, num);
                    break;
                case "days":
                case "day":
                case "d":
                    c.add(Calendar.DAY_OF_MONTH, num);
                    break;
                case "hours":
                case "hour":
                case "h":
                    c.add(Calendar.HOUR, num);
                    break;
                case "minutes":
                case "minute":
                case "min":
                case "m":
                    c.add(Calendar.MINUTE, num);
                    break;
                case "seconds":
                case "second":
                case "sec":
                case "s":
                    c.add(Calendar.SECOND, num);
                    break;
            }
        }

        return c.getTime();
    }

    /**
     * DateからStringに変換
     *
     * @param date DATE!!!!
     * @return 変換したやつ
     */
    public static String convertFromDate(Date date)
    {
        final long diff = date.getTime() - new Date().getTime();

        return diff / (24 * 60 * 60 * 1000) +
                MessageEngine.get("base.day") +
                diff / (60 * 60 * 1000) % 24 +
                MessageEngine.get("base.hour") +
                diff / (60 * 1000) % 60 +
                MessageEngine.get("base.minutes") +
                diff / 1000 % 60 +
                MessageEngine.get("base.seconds");
    }
}
