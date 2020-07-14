package ml.peya.plugins.Utils;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Moderate.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.math.*;
import java.text.*;
import java.util.*;

public class TextBuilder
{
    private static TextComponent getPrevNextButton(int bind, String button)
    {
        TextComponent nextBtn = new TextComponent(ChatColor.GREEN + "(" +
                ChatColor.AQUA + button +
                ChatColor.GREEN + ")");
        nextBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac view " + bind));
        ComponentBuilder nextHover = new ComponentBuilder(MessageEngine.get("book.words.next"));

        nextBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, nextHover.create()));
        return nextBtn;
    }


    public static TextComponent getNextButton(int next)
    {
        return getPrevNextButton(next, "=>");
    }

    public static TextComponent getPrevButton(int previous)
    {
        return getPrevNextButton(previous, "<=");
    }

    public static void showText(String id, String uuid, String issueById, String issueByUuid, BigDecimal dateInt, ArrayList<EnumCheatType> types, CommandSender sender)
    {
        Date date = new Date(dateInt.longValue());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");


        ComponentBuilder hover = new ComponentBuilder(MessageEngine.get("book.clickable"));

        StringBuilder reasonText = new StringBuilder();

        for (EnumCheatType type: types)
            reasonText.append("        ").append(type.getText()).append("\n");

        ComponentBuilder b1 = new ComponentBuilder("    " + MessageEngine.get("book.text.issueBy", MessageEngine.hsh("id", issueById)));
        b1.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b1.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, issueByUuid));
        sender.spigot().sendMessage(b1.create());

        ComponentBuilder b2 = new ComponentBuilder("    " + MessageEngine.get("book.text.issueTo", MessageEngine.hsh("id", id)));
        b2.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b2.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid));
        sender.spigot().sendMessage(b2.create());

        sender.sendMessage("    " + MessageEngine.get("book.text.dateTime", MessageEngine.hsh("time", formatter.format(date))));

        sender.sendMessage("    " + MessageEngine.get("book.text.reason", MessageEngine.hsh("reason", reasonText.toString())));

        HashMap<String, Object> serv = new HashMap<>();
        serv.put("color", SeverityLevelUtils.getSeverity(types).getColor());
        serv.put("level", SeverityLevelUtils.getSeverity(types).getText());
        sender.sendMessage(MessageEngine.get("book.text.severity", serv));
    }


    public static ComponentBuilder getLine(String id, String issueById, ArrayList<EnumCheatType> types, String mngid, CommandSender sender)
    {
        ComponentBuilder hover = new ComponentBuilder(MessageEngine.get("book.click.openAbout"));

        ComponentBuilder dropHover = new ComponentBuilder( MessageEngine.get("book.click.deleteReport"));

        EnumSeverity severity = SeverityLevelUtils.getSeverity(types);

        ComponentBuilder b = new ComponentBuilder("");

        b.append(ChatColor.GREEN + id);
        b.append("   ");

        b.append(ChatColor.BLUE + issueById);
        b.append("   ");

        b.append(severity.getText())
                .color(severity.getColor())
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac show " + mngid))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b.append("   ");
        if (sender instanceof Player && sender.hasPermission("psac.drop"))
        {
            b.append(MessageEngine.get("book.click.delete"))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac drop " + mngid))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, dropHover.create()));

        }
        else
            b.append(ChatColor.YELLOW + mngid);

        return b;
    }

    public static String getLine(String prefix, String value)
    {
        return ChatColor.AQUA + prefix + ChatColor.WHITE + "：" + ChatColor.GREEN + value;
    }

    public static String getSeverityLevel(EnumSeverity severity)
    {
        String prefix = ChatColor.YELLOW + "Level " + severity.getColor();
        switch (severity)
        {
            case FINE:
                return prefix + "1";
            case FINER:
                return prefix + "2";
            case FINEST:
                return prefix + "3";
            case NORMAL:
                return prefix + "4";
            case PRIORITY:
                return prefix + ChatColor.BOLD + "5";
            case REQUIRE_FAST:
                return prefix + ChatColor.BOLD + "6";
            case SEVERE:
                return prefix + ChatColor.BOLD + "7";
            default:
                return prefix + ChatColor.GRAY + "Unknown";
        }
    }

    public static ComponentBuilder getNextPrevButtonText(TextComponent prev, TextComponent next, boolean prevFlag, boolean nextFlag)
    {
        TextComponent uBar = new TextComponent("----");
        uBar.setColor(net.md_5.bungee.api.ChatColor.AQUA);
        ComponentBuilder builder = new ComponentBuilder(prevFlag ? prev: uBar);
        builder.append("------------------------")
                .color(net.md_5.bungee.api.ChatColor.AQUA);
        builder.append(nextFlag ? next :  uBar);
        return builder;
    }

    public static ComponentBuilder getBroadCastWdDetectionText()
    {
        return new ComponentBuilder(MessageEngine.get("kick.broadcastWd"));
    }

    public static ComponentBuilder getBroadCastWdDetectionText(Player player)
    {
        ComponentBuilder component = getBroadCastWdDetectionText();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", player.getName());
        map.put("uuid", player.getUniqueId().toString());

        ComponentBuilder hover = new ComponentBuilder(MessageEngine.get("kick.broadcastAdmin", map));
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create());
        component.event(event);
        return component;
    }

    public static ComponentBuilder textTestRep(String name, int VL, int kickVL)
    {
        if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            return new ComponentBuilder(MessageEngine.get("message.auraCheck.bot.lynx", MessageEngine.hsh("hit", VL)));

        ComponentBuilder builder = new ComponentBuilder(MessageEngine.get("base.prefix") + "\n");

        builder.append(MessageEngine.get("message.auraCheck.result.prefix", MessageEngine.hsh("name", name)));
        builder.append("\n");
        builder.append(MessageEngine.get("message.auraCheck.result.vl", MessageEngine.hsh("vl", String.valueOf(VL))));
        builder.append("\n");
        builder.append(MessageEngine.get("message.auraCheck.result.vlGraph"));
        builder.append("\n");
        builder.append(OptGraphGenerator.genGraph(VL, kickVL));
        builder.append("\n");

        String result = VL >= kickVL ? MessageEngine.get("message.auraCheck.result.words.kick"): MessageEngine.get("message.auraCheck.result.words.ok");

        builder.append(MessageEngine.get("message.auraCheck.result.result", MessageEngine.hsh("result", result)));

        return builder;
    }

    public static ComponentBuilder textPanicRep(String name, int vl)
    {
        if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            return new ComponentBuilder("");
        ComponentBuilder builder = new ComponentBuilder(MessageEngine.get("base.prefix"));
        builder.append("\n");
        builder.append(MessageEngine.get("message.auraCheck.result.prefix", MessageEngine.hsh("name", name)));
        builder.append("\n");
        builder.append(MessageEngine.get("message.auraCheck.result.vl", MessageEngine.hsh("vl", String.valueOf(vl))));
        return builder;
    }


    public static ComponentBuilder getTextBan(BanAnalyzer.Bans ban, BanAnalyzer.Type type)
    {
        Date date = new Date(ban.getDate());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        ComponentBuilder builder = new ComponentBuilder(ChatColor.YELLOW + (type == BanAnalyzer.Type.KICK ? "Kick": "Ban"));
        builder.append(" - " + formatter.format(date));
        StringBuilder reasonSet = new StringBuilder();
        for (String reason: ban.getReason().split(", "))
        {
            EnumCheatType tp = CheatTypeUtils.getCheatTypeFromString(reason);
            if (tp == null)
                reasonSet.append(reason).append(", ");
            else
                reasonSet.append(tp.getText());
        }

        if (reasonSet.toString().endsWith(", "))
            reasonSet.setLength(reasonSet.length() - 2);

        builder.append(ChatColor.WHITE + " " + ChatColor.ITALIC + reasonSet.toString());

        return builder;
    }

}
