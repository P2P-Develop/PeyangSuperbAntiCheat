package ml.peya.plugins.Utils;

import ml.peya.plugins.Enum.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;

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

    public static void sendTable(String id, String uuid, String issueById, String issueByUuid, int dateInt, ArrayList<EnumCheatType> types, CommandSender sender)
    {
        Date date = new Date(dateInt);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");


        ComponentBuilder hover = new ComponentBuilder( "クリックして\n");
        hover.color(net.md_5.bungee.api.ChatColor.AQUA);
        hover.append("チャットに貼り付け")
                .color(net.md_5.bungee.api.ChatColor.AQUA);

        StringBuilder reasonText = new StringBuilder();

        for (EnumCheatType type: types)
            reasonText.append("        ").append(type.getText()).append("\n");

        ComponentBuilder b1 = new ComponentBuilder("    " + getColor("報告者", issueById));
        b1.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b1.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, issueByUuid));
        sender.spigot().sendMessage(b1.create());

        ComponentBuilder b2 = new ComponentBuilder("    " + getColor("対象者", id));
        b2.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
        b2.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid));
        sender.spigot().sendMessage(b2.create());

        sender.sendMessage("    " + getColor("報告日時", formatter.format(date)));

        sender.sendMessage("    " + getColor("報告理由", "\n" +reasonText.toString()));

        sender.sendMessage(ChatColor.AQUA + "    脅威判定: " + getSeverity(types).getColor() + getSeverity(types).getText());


    }

    private static String getColor(String prefix, String value)
    {
        return ChatColor.AQUA +  prefix + ": " + ChatColor.GREEN + value;
    }

    public static ComponentBuilder getLine(String id, String issueById, ArrayList<EnumCheatType> types, String mngid)
    {
        ComponentBuilder hover = new ComponentBuilder( "クリックして\n");
        hover.color(net.md_5.bungee.api.ChatColor.GREEN);
        hover.append("詳細を表示")
                .color(net.md_5.bungee.api.ChatColor.GREEN);

        EnumSeverity severity = getSeverity(types);

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
        return b;
    }

    public static EnumSeverity getSeverity(ArrayList<EnumCheatType> types)
    {
        switch(types.size())
        {
            case 2:
                return EnumSeverity.NORMAL;
            case 3:
            case 4:
                return EnumSeverity.PRIORITY;
            case 5:
            case 6:
            case 7:
                return EnumSeverity.SEVERE;
            default:
                return EnumSeverity.LOW;
        }
    }

    public static ComponentBuilder getNextPrevButtonText(TextComponent prev, TextComponent next, boolean prevFlag, boolean nextFlag)
    {
        TextComponent uBar = new TextComponent("----");
        uBar.setColor(net.md_5.bungee.api.ChatColor.AQUA);
        ComponentBuilder builder = new ComponentBuilder(prevFlag ? prev: uBar);
        builder.append("-----------------------")
                .color(net.md_5.bungee.api.ChatColor.AQUA);
        builder.append(nextFlag ? next :  uBar);
        return builder;
    }
}
