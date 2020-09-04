package ml.peya.plugins.Utils;

import ml.peya.plugins.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

/**
 * ランダムな単語を生成するクラス。
 */
public class RandomWordUtils
{
    /**
     * ファイルからランダムな単語を取得します。
     *
     * @return 生成した単語。
     */
    public static String getRandomWord()
    {
        return getRandomWord(new Random().nextInt());
    }

    /**
     * ファイルからランダムな単語を取得します。
     *
     * @param bound ランダムなシード値。ランダム性を強化します。
     * @return 生成した単語。
     */
    public static String getRandomWord(int bound)
    {
        try (InputStreamReader reader = new InputStreamReader(PeyangSuperbAntiCheat.class.getResourceAsStream("/wordsx256.txt"), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(reader))
        {
            return bufferedReader.lines().skip(new Random(bound).nextInt(1384 - 1)).findFirst().orElse("");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
