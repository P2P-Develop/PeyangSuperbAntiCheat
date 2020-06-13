package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import ml.peya.plugins.Enum.*;
import net.md_5.bungee.api.chat.*;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.math.*;
import java.nio.*;
import java.text.*;
import java.util.*;

public class TextBuilder
{
    public static TextComponent getNextButton(int next)
    {
        TextComponent nextBtn = new TextComponent(ChatColor.GREEN + "(" +
                ChatColor.AQUA + "=>" +
                ChatColor.GREEN + ")");
        nextBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psr view " + next));
        ComponentBuilder nextHover = new ComponentBuilder(ChatColor.AQUA + "次へ行く");

        nextBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, nextHover.create()));
        return nextBtn;
    }

    public static TextComponent getPrevButton(int previous)
    {

        TextComponent prevBtn = new TextComponent(ChatColor.GREEN + "(" +
                ChatColor.AQUA + "<=" +
                ChatColor.GREEN + ")");
        prevBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psr view " + previous));
        ComponentBuilder prevHover = new ComponentBuilder(ChatColor.AQUA + "前へ戻る");

        prevBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, prevHover.create()));

        return prevBtn;
    }

    public static void showText(String id, String uuid, String issueById, String issueByUuid, BigDecimal dateInt, ArrayList<EnumCheatType> types, CommandSender sender)
    {
        Date date = new Date(dateInt.longValue());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");


        ComponentBuilder hover = new ComponentBuilder( "クリックして\n");
        hover.color(net.md_5.bungee.api.ChatColor.AQUA);
        hover.append("チャットに貼り付け")
                .color(net.md_5.bungee.api.ChatColor.AQUA);

        StringBuilder reasonText = new StringBuilder();

        for (EnumCheatType type: types)
            reasonText.append("        ").append(type.getText()).append("\n");

        ComponentBuilder b1 = new ComponentBuilder("    " + TextBuilder.getColor("報告者", issueById));
        b1.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b1.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, issueByUuid));
        sender.spigot().sendMessage(b1.create());

        ComponentBuilder b2 = new ComponentBuilder("    " + TextBuilder.getColor("対象者", id));
        b2.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b2.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid));
        sender.spigot().sendMessage(b2.create());

        sender.sendMessage("    " + TextBuilder.getColor("報告日時", formatter.format(date)));

        sender.sendMessage("    " + TextBuilder.getColor("報告理由", "\n" +reasonText.toString()));

        sender.sendMessage(ChatColor.AQUA + "    脅威判定：" + SeverityLevelUtils.getSeverity(types).getColor() + SeverityLevelUtils.getSeverity(types).getText());
    }

    public static String getColor(String prefix, String value)
    {
        return ChatColor.AQUA +  prefix + ": " + ChatColor.GREEN + value;
    }

    public static ComponentBuilder getLine(String id, String issueById, ArrayList<EnumCheatType> types, String mngid, CommandSender sender)
    {
        ComponentBuilder hover = new ComponentBuilder( "クリックして\n");
        hover.color(net.md_5.bungee.api.ChatColor.GREEN);
        hover.append("詳細を表示")
                .color(net.md_5.bungee.api.ChatColor.GREEN);

        ComponentBuilder dropHover = new ComponentBuilder( "クリックして\n");
        dropHover.color(net.md_5.bungee.api.ChatColor.GREEN);
        dropHover.append("レポートを削除")
                .color(net.md_5.bungee.api.ChatColor.GREEN);

        EnumSeverity severity = SeverityLevelUtils.getSeverity(types);

        ComponentBuilder b = new ComponentBuilder("");

        b.append(id)
                .color(net.md_5.bungee.api.ChatColor.AQUA)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psr show " + mngid))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b.append("   ");

        b.append(issueById)
                .color(net.md_5.bungee.api.ChatColor.GREEN)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psr show " + mngid))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b.append("   ");

        b.append(severity.getText())
                .color(severity.getColor())
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psr show " + mngid))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b.append("   ");
        if (sender instanceof Player)
        {
            b.append(ChatColor.WHITE + "[" + ChatColor.YELLOW + ChatColor.BOLD + "削除" + ChatColor.WHITE + "]")
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psr drop " + mngid))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, dropHover.create()));

        }
        else
            b.append(ChatColor.YELLOW + mngid);

        return b;
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
        return new ComponentBuilder("[PEYANG CHEAT DETECTION]\n"+
                net.md_5.bungee.api.ChatColor.RED + net.md_5.bungee.api.ChatColor.BOLD.toString() +
                "ハッキング、または不適切な発言によってゲームからプレイヤーが削除\n" +
                net.md_5.bungee.api.ChatColor.RED + net.md_5.bungee.api.ChatColor.BOLD.toString() +
                "されました。");
    }

    public static ComponentBuilder getBroadCastWdDetectionText(Player player)
    {
        ComponentBuilder component = getBroadCastWdDetectionText();

        ComponentBuilder hover = new ComponentBuilder(getLine("プレイヤー", player.getName()) + "\n" +
                                                getLine("UUID", player.getUniqueId().toString()));
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create());
        component.event(event);
        return component;
    }

    public static String getLine(String prefix, String value)
    {
        return ChatColor.AQUA + ChatColor.BOLD.toString() + prefix + ChatColor.GREEN + "：" + ChatColor.BLUE.toString() + value;
    }


    public static ComponentBuilder textTestRep(String name, int VL, int kickVL)
    {
        ComponentBuilder builder = new ComponentBuilder(ChatColor.AQUA + "-----" +
                ChatColor.GREEN + "[" +
                ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                ChatColor.GREEN + "]" +
                ChatColor.AQUA + "-----\n");

        builder.append(ChatColor.GREEN + name + "さんのKillAuraチェック結果");
        builder.append("\n");
        builder.append(getLine("VL", String.valueOf(VL)));
        builder.append("\n");
        builder.append(String.format("%s0%%   %s50%%  %s100%%", EnumChatFormat.GREEN, EnumChatFormat.YELLOW, EnumChatFormat.RED));
        builder.append("\n");
        builder.append(OptGraphGenerator.genGraph(VL, kickVL));
        builder.append("\n");
        builder.append(ChatColor.AQUA + "結果：" +
                (VL >= kickVL ? (EnumChatFormat.RED + "強制退出"): (EnumChatFormat.GREEN + "問題なし")));
        return builder;
    }

    public static ComponentBuilder textPanicRep(String name, int vl)
    {
        ComponentBuilder builder = new ComponentBuilder(ChatColor.AQUA + "-----" +
                ChatColor.GREEN + "[" +
                ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                ChatColor.GREEN + "]" +
                ChatColor.AQUA + "-----\n");

        builder.append(ChatColor.GREEN + name + "さんのKillAuraチェック結果");
        builder.append("\n");
        builder.append(ChatColor.GREEN + "VL：" + ChatColor.YELLOW + vl);
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
