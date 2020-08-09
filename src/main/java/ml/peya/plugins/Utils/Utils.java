package ml.peya.plugins.Utils;

import com.fasterxml.jackson.databind.*;
import ml.peya.plugins.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

public class Utils
{
    /**
     * プレイヤーをランダムに架空で作るよ！
     *
     * @return jsonNodeをレスポンスのまんま返すよ！
     */
    public static JsonNode getRandomUser()
    {
        try
        {
            HttpsURLConnection connection;
            connection = (HttpsURLConnection) new URL("https://randomuser.me/api/").openConnection();
            connection.setReadTimeout(500);
            connection.connect();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK)
            {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
                {
                    StringBuilder builder = new StringBuilder();
                    String readed = reader.readLine();
                    while (readed != null)
                    {
                        builder.append(readed);
                        readed = reader.readLine();
                    }
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readTree(builder.toString());
                }

            }
            else
                PeyangSuperbAntiCheat.logger.info("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            errorNotification(getStackTrace(e));
        }

        return null;
    }

    /**
     * 権限持ってるやつが通知できる奴。
     *
     * @param id ID。
     */
    public static void adminNotification(String id)
    {
        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.hasPermission("psac.reportntf")).forEachOrdered(player -> {
            player.sendMessage(MessageEngine.get("report.submited"));
            ComponentBuilder hover = new ComponentBuilder("/psac show " + id);
            hover.color(net.md_5.bungee.api.ChatColor.AQUA);
            ComponentBuilder builder = new ComponentBuilder(MessageEngine.get("report.click"));
            builder.append("[" + ChatColor.YELLOW + ChatColor.BOLD + "CLICK" + ChatColor.WHITE + "]")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac show " + id));
            player.spigot().sendMessage(builder.create());
        });
    }

    /**
     * 上のオーバーロード。
     *
     * @param name    PlayerName。
     * @param id      ID。
     * @param reasons 事由。
     */
    public static void adminNotification(String name, String id, String[] reasons)
    {
        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.hasPermission("psac.reportntf")).forEachOrdered(player -> {
            ComponentBuilder hover = new ComponentBuilder("/psac show " + id);
            hover.color(net.md_5.bungee.api.ChatColor.AQUA);
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("reason", String.join(", ", reasons));
            TextComponent builder = new TextComponent(MessageEngine.get("report.lynx.submited", map));
            builder.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac show " + id));
            builder.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
            player.spigot().sendMessage(builder);
        });
    }

    /**
     * エラー起こっちゃったふえええぇぇっていう時の通知。adminにしか届かん。
     *
     * @param stacktrace 送り付けるスタックトレース。
     */
    public static void errorNotification(String stacktrace)
    {
        Bukkit.getOnlinePlayers().parallelStream().filter(player -> player.hasPermission("psac.error")).forEachOrdered(player -> {
            player.sendMessage(ChatColor.GREEN + "[" +
                    ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                    ChatColor.GREEN + "] " +
                    ChatColor.RED + "プレイヤーレポートで予期しないエラーが発生しました！");
            ComponentBuilder builder = new ComponentBuilder(ChatColor.YELLOW + "カーソルを合わせて確認してください！");
            builder.append("[" + ChatColor.YELLOW + ChatColor.BOLD + "カーソルを合わせる" + ChatColor.WHITE + "]")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(stacktrace.replace("\r", "\n").replace("\t", "    ")).create()));
            player.spigot().sendMessage(builder.create());
        });
    }

    /**
     * スタックトレースを...入手する？
     *
     * @param e   スタックトレース...らしい。
     * @param <T> ジェネリクスわーい
     * @return InnerExceptionみたいなの返すらしーよ！
     */
    public static <T extends Exception> String getStackTrace(T e)
    {
        StringWriter str = new StringWriter();
        PrintWriter w = new PrintWriter(str);
        e.printStackTrace(w);
        w.flush();
        return str.toString();
    }
}
