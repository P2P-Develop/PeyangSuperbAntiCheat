package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

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
        try
        {
            return format((String) YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(PeyangSuperbAntiCheat.class.getResourceAsStream("/message.yml"), StandardCharsets.UTF_8))).getValues(true).get(key), format);
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
        HashMap<String, ChatColor> map = new HashMap<>();

        map.put("%%black%%", ChatColor.BLACK);
        map.put("%%dark_blue%%", ChatColor.DARK_BLUE);
        map.put("%%dark_green%%", ChatColor.DARK_GREEN);
        map.put("%%dark_aqua%%", ChatColor.DARK_AQUA);
        map.put("%%dark_red%%", ChatColor.DARK_RED);
        map.put("%%dark_purple%%", ChatColor.DARK_PURPLE);
        map.put("%%gold%%", ChatColor.GOLD);
        map.put("%%gray%%", ChatColor.GRAY);
        map.put("%%dark_gray%%", ChatColor.DARK_GRAY);
        map.put("%%blue%%", ChatColor.BLUE);
        map.put("%%green%%", ChatColor.GREEN);
        map.put("%%aqua%%", ChatColor.AQUA);
        map.put("%%red%%", ChatColor.RED);
        map.put("%%light_purple%%", ChatColor.LIGHT_PURPLE);
        map.put("%%yellow%%", ChatColor.YELLOW);
        map.put("%%white%%", ChatColor.WHITE);

        map.put("%%obfuscated%%", ChatColor.MAGIC);
        map.put("%%bold%%", ChatColor.BOLD);
        map.put("%%strikethrough%%", ChatColor.STRIKETHROUGH);
        map.put("%%italic%%", ChatColor.ITALIC);
        map.put("%%reset%%", ChatColor.RESET);

        return map;
    }
}
