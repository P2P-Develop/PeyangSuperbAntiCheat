package ml.peya.plugins.Bukkit.Utils;

import ml.peya.plugins.Bukkit.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

/**
 * ランダムな単語を生成するクラス。
 */
public class RandomWordUtils
{

    private static final String __FilePath = "/wordsx256.txt";
    private static final int __MaxLine = 1384;

    /**
     * ファイルからランダムな単語を取得します。
     *
     * @return 生成した単語。
     */
    public static String getRandomWord()
    {
        Random random = new Random();
        return getRandomWord(random.nextInt());
    }

    /**
     * ファイルからランダムな単語を取得します。
     *
     * @param bound ランダムなシード値。ランダム性を強化します。
     * @return 生成した単語。
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
