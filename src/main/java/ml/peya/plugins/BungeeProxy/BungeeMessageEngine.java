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
     * 文字列と {@link ChatColor} の1対1対応
     */
    private static HashMap<String, ChatColor> colorMap;

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

        colorMap.put("%%black%%", ChatColor.BLACK);
        colorMap.put("%%dark_blue%%", ChatColor.DARK_BLUE);
        colorMap.put("%%dark_green%%", ChatColor.DARK_GREEN);
        colorMap.put("%%dark_aqua%%", ChatColor.DARK_AQUA);
        colorMap.put("%%dark_red%%", ChatColor.DARK_RED);
        colorMap.put("%%dark_purple%%", ChatColor.DARK_PURPLE);
        colorMap.put("%%gold%%", ChatColor.GOLD);
        colorMap.put("%%gray%%", ChatColor.GRAY);
        colorMap.put("%%dark_gray%%", ChatColor.DARK_GRAY);
        colorMap.put("%%blue%%", ChatColor.BLUE);
        colorMap.put("%%green%%", ChatColor.GREEN);
        colorMap.put("%%aqua%%", ChatColor.AQUA);
        colorMap.put("%%red%%", ChatColor.RED);
        colorMap.put("%%light_purple%%", ChatColor.LIGHT_PURPLE);
        colorMap.put("%%yellow%%", ChatColor.YELLOW);
        colorMap.put("%%white%%", ChatColor.WHITE);

        colorMap.put("%%obfuscated%%", ChatColor.MAGIC);
        colorMap.put("%%bold%%", ChatColor.BOLD);
        colorMap.put("%%strikethrough%%", ChatColor.STRIKETHROUGH);
        colorMap.put("%%italic%%", ChatColor.ITALIC);
        colorMap.put("%%reset%%", ChatColor.RESET);

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
        String replaced = text;

        for (String key : colorMap.keySet())
            replaced = replaced.replace(key, colorMap.get(key).toString());

        for (String key : format.keySet())
            replaced = replaced.replace("%%" + key + "%%", String.valueOf(format.get(key)));

        return replaced;
    }
}
