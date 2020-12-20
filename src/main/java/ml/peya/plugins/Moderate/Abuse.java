package ml.peya.plugins.Moderate;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 記録の一意化などを図る関数のセット。
 */
public class Abuse
{
    /**
     * ランダムなIDを生成します。
     *
     * @param len IDの桁数。
     * @return 生成したID。
     */
    public static String genRandomId(int len)
    {
        return RandomStringUtils.randomAlphanumeric(len);
    }
}
