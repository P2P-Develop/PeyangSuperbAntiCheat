package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import org.apache.commons.lang3.tuple.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

/**
 * メッセージテンプレートを管理するクラス。
 * message.ymlのメッセージデータをパースして取得しやすくする。
 */
public class MessageEngine
{
    /**
     * %%name%%などカラーコード以外に関係する動的な参照データをハッシュマップとして結びつける。
     *
     * @param path 動的データタイプ。
     * @param obj  結びつけるオブジェクト。
     *             get(String, HashMap)との併用で使用する。
     * @return 結びつけたハッシュマップ。
     */
    public static HashMap<String, Object> pair(String path, Object obj)
    {
        HashMap<String, Object> map = new HashMap<>();

        map.put(path, obj);
        return map;
    }

    public static String get(String key)
    {
        return get(key, new HashMap<>());
    }

    /**
     * メッセージテンプレートのキーに対してテンプレートを返すメソッド。こいつとhshを共用する。
     *
     * @param key    参照するメッセージテンプレート。
     * @param format hsh()するやつ。
     * @return 参照できたやつ。
     */
    public static String get(String key, HashMap<String, Object> format)
    {
        try (InputStreamReader reader = new InputStreamReader(PeyangSuperbAntiCheat.class.getResourceAsStream("/message.yml"), StandardCharsets.UTF_8))
        {
            return format((String) YamlConfiguration.loadConfiguration(new BufferedReader(reader)).getValues(true).get(key), format);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * フォーマットしたいよぉふえぇっていう時にうってつけ
     *
     * @param text   Before
     * @param format After
     * @return AFTER^2
     */
    public static String format(String text, HashMap<String, Object> format)
    {
        HashMap<String, ChatColor> map = getColor();

        for (String key : map.keySet())
            text = text.replace(key, map.get(key).toString());

        for (String key : format.keySet())
            text = text.replace("%%" + key + "%%", String.valueOf(format.get(key)));

        return text;
    }

    /**
     * いい感じにEnumのChatColorに変更してくれるやつ。マジでハイなピクセルに寄せてくるな。
     *
     * @return 変換した後のはっしゅまっぷ。
     */
    private static HashMap<String, ChatColor> getColor()
    {
        return Stream.of(
                Pair.of("%%black%%", ChatColor.BLACK),
                Pair.of("%%dark_blue%%", ChatColor.DARK_BLUE),
                Pair.of("%%dark_green%%", ChatColor.DARK_GREEN),
                Pair.of("%%dark_aqua%%", ChatColor.DARK_AQUA),
                Pair.of("%%dark_red%%", ChatColor.DARK_RED),
                Pair.of("%%dark_purple%%", ChatColor.DARK_PURPLE),

                Pair.of("%%gold%%", ChatColor.GOLD),
                Pair.of("%%gray%%", ChatColor.GRAY),
                Pair.of("%%blue%%", ChatColor.BLUE),
                Pair.of("%%green%%", ChatColor.GREEN),
                Pair.of("%%aqua%%", ChatColor.AQUA),
                Pair.of("%%red%%", ChatColor.RED),
                Pair.of("%%light_purple%%", ChatColor.LIGHT_PURPLE),
                Pair.of("%%yellow%%", ChatColor.YELLOW),
                Pair.of("%%white%%", ChatColor.WHITE),

                Pair.of("%%obfuscated%%", ChatColor.MAGIC),
                Pair.of("%%bold%%", ChatColor.BOLD),
                Pair.of("%%strikethrough%%", ChatColor.STRIKETHROUGH),
                Pair.of("%%italic%%", ChatColor.ITALIC),
                Pair.of("%%reset%%", ChatColor.RESET)
        ).collect(Collectors.toMap(Pair::getLeft, Pair::getRight, (a, b) -> b, HashMap::new));
    }
}
