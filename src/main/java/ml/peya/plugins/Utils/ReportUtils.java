package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.io.*;
import java.util.*;

public class ReportUtils
{
    public static void adminNotification(String id)
    {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("psac.reportntf")).forEachOrdered(player -> {
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

    public static void adminNotification(String name, String id, String[] reasons)
    {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("psac.reportntf")).forEachOrdered(player -> {
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

    public static void errorNotification(String stacktrace)
    {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("psac.error")).forEachOrdered(player -> {
            player.sendMessage(ChatColor.GREEN + "[" +
                    ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                    ChatColor.GREEN + "] " +
                    ChatColor.RED + "プレイヤーレポートでエラーが発生しました！");
            ComponentBuilder hover = new ComponentBuilder(stacktrace.replace("\r", "\n").replace("\t", "    "));
            ComponentBuilder builder = new ComponentBuilder(ChatColor.YELLOW + "カーソルを合わせて確認してください！");
            builder.append("[" + ChatColor.YELLOW + ChatColor.BOLD + "カーソルを合わせる" + ChatColor.WHITE + "]")
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
            player.spigot().sendMessage(builder.create());
        });
    }

    public static <T extends Exception> String getStackTrace(T e)
    {
        StringWriter str = new StringWriter();
        PrintWriter w = new PrintWriter(str);
        e.printStackTrace(w);
        w.flush();
        return str.toString();

    }
}
