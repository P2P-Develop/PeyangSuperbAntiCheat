package ml.peya.plugins.Utils;

import ml.peya.plugins.PeyangSuperbAntiCheat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * ランダムな単語を取得するクラス
 */
public class RandomWordUtils
{

    //登録スキン数
    private static final int __MaxLine = 1384;

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
            return bufferedReader.lines()
                    .skip(new Random(bound).nextInt(__MaxLine - 1))
                    .findFirst()
                    .orElse("");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
