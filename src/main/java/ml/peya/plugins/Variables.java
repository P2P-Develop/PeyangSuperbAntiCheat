package ml.peya.plugins;

import com.comphenix.protocol.*;
import com.zaxxer.hikari.*;
import ml.peya.plugins.DetectClasses.*;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Learn.*;
import ml.peya.plugins.Moderate.*;
import org.bukkit.configuration.file.*;
import org.bukkit.scheduler.*;

import java.util.*;
import java.util.logging.*;

/**
 * 変数群
 */
public class Variables
{
    static final int __BSTATS_PLUGIN_ID = 8084;
    public static Logger logger = Logger.getLogger("PeyangSuperbAntiCheat");
    public static FileConfiguration config;
    public static String databasePath;
    public static String banKickPath;
    public static String trustPath;
    public static DetectingList cheatMeta;
    public static KillCounting counting;
    public static ProtocolManager protocolManager;
    public static Item item;
    public static Tracker tracker;
    public static HashMap<UUID, HashMap<String, String>> mods;
    public static long time = 0L;
    public static int banLeft;
    public static int learnCountLimit;
    public static int learnCount;
    public static NeuralNetwork network;
    public static HikariDataSource eye;
    public static HikariDataSource banKick;
    public static HikariDataSource trust;
    public static boolean isAutoMessageEnabled;
    public static boolean isTrackEnabled;
    public static BukkitRunnable autoMessage;
    public static BukkitRunnable trackerTask;
    static PeyangSuperbAntiCheat plugin;
}
