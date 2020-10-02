package ml.peya.plugins.Moderate;

import org.apache.commons.lang.RandomStringUtils;

/**
 * やらかしたやつの末路
 */
public class Abuse
{
    /**
     * ランダムなIDを生成
     *
     * @param len 桁数
     * @return ID
     */
    public static String genRandomId(int len)
    {
        return RandomStringUtils.randomAlphanumeric(len);
    }

}
