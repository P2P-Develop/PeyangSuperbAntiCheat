package ml.peya.plugins.Utils;

import ml.peya.plugins.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

/**
 * ランダムな語に関するやつ
 */
public class RandomWordUtils
{

    private static final String __FilePath = "/wordsx256.txt";
    private static final int __MaxLine = 1384;

    /**
     * ファイルからランダムな語を取得する
     *
     * @return 語
     */
    public static String getRandomWord()
    {
        Random random = new Random();
        return getRandomWord(random.nextInt());
    }

    /**
     * ファイルからランダムな語を取得する
     *
     * @param bound ランダムシード値
     * @return 語
     */
    public static String getRandomWord(int bound)
    {
        Random random = new Random(bound);

        int line = random.nextInt(__MaxLine - 1);

        try (InputStreamReader reader = new InputStreamReader(PeyangSuperbAntiCheat.class.getResourceAsStream(__FilePath), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader))
        {
            Optional<String> result = bufferedReader.lines().skip(line).findFirst();
            return result.orElse("");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
