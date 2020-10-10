package ml.peya.plugins.BungeeProxy;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class BungeeMessageEngine
{
    /**
     * コンフィグ
     */
    private static Configuration config;

    /**
     * 初期化確認
     */
    private static boolean isInitialized = false;

    /**
     * 初期化
     */
    public static void initialize()
    {
        try (InputStreamReader reader = new InputStreamReader(PeyangSuperbAntiCheatProxy.class.getResourceAsStream("/message.yml"), StandardCharsets.UTF_8))
        {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(reader);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            isInitialized = false;
        }

        isInitialized = true;
    }

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
        if (!isInitialized)
        {
            initialize();
            return get(key, format);
        }

        return format((String) config.get(key), format);
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

        String replaced = text;

        for (String key : map.keySet())
            replaced = replaced.replace(key, map.get(key).toString());

        for (String key : format.keySet())
            replaced = replaced.replace("%%" + key + "%%", String.valueOf(format.get(key)));

        return replaced;
    }

    /**
     * 特定の文字セットをEnumのChatColorに変更するためのリストをマップとして返すやつ。
     *
     * @return 変換した後のはっしゅまっぷ。
     */
    private static HashMap<String, ChatColor> getColor()
    { //BungeeCordなのでPairつかえない
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
