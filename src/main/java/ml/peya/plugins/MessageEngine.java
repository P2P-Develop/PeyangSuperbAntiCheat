package ml.peya.plugins;

import org.bukkit.*;
import org.bukkit.configuration.file.*;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public class MessageEngine
{
    public static HashMap<String, Object> hsh(String path, Object obj)
    {
        HashMap<String, Object> map = new HashMap<>();

        map.put(path, obj);
        return map;
    }

    public static String get(String key)
    {
        return get(key, new HashMap<>());
    }

    public static String get(String key, HashMap<String, Object> format)
    {

        try
        {
            InputStream in = PeyangSuperbAntiCheat.class.getResourceAsStream("/message.yml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);
            return format((String) config.getValues(true).get(key), format);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(String text, HashMap<String, Object> format)
    {
        HashMap<String, ChatColor> map = getColor();

        for (String key : map.keySet())
            text = text.replace(key, map.get(key).toString());


        for (String key : format.keySet())
            text = text.replace("%%" + key + "%%", String.valueOf(format.get(key)));


        return text;
    }

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
