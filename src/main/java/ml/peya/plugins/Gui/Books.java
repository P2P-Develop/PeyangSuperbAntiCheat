package ml.peya.plugins.Gui;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.math.*;
import java.text.*;
import java.util.*;

public class Books
{

    public static ItemStack getReportBook(Player player, EnumCheatType... types)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        StringBuilder tmpReasonText = new StringBuilder();
        for (EnumCheatType type: types)
            tmpReasonText.append(type.isSelected() ? type.getSysName() + " ": "");
        ComponentBuilder component = new ComponentBuilder( "どんなチートを使っていましたか?");
        component.append("\n");

        for (EnumCheatType type: types)
        {
            String text = "◎ " + type.getText() + "\n";
            if (type.isSelected())
                text = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + text;
            component.append(text)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " +  player.getName() + " " + tmpReasonText + " " + type.getSysName() + " \\"));
        }

        component.append("\n\n");


        meta.setTitle("-");
        meta.setAuthor("AntiCheat Dev");

        component.append(ChatColor.DARK_GREEN + ChatColor.BOLD.toString() +  "レポートを提出")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + " " + tmpReasonText + " bybooks"));

        component.append(ChatColor.RED + "レポートをキャンセル")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""));

        meta.spigot().addPage(component.create());
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack getShowBook(String id, String uuid, String issueById, String issueByUuid, BigDecimal dateInt, ArrayList<EnumCheatType> types)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        Date date = new Date(dateInt.longValue());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        ComponentBuilder hover = new ComponentBuilder( "UUID: ")
                .color(net.md_5.bungee.api.ChatColor.AQUA)
                .append(uuid)
                .color(net.md_5.bungee.api.ChatColor.GREEN);

        ComponentBuilder unixTime = new ComponentBuilder(getLine("UNIX秒", String.valueOf(dateInt)));


        ComponentBuilder hover2 = new ComponentBuilder( "UUID: ")
                .color(net.md_5.bungee.api.ChatColor.AQUA)
                .append(issueByUuid)
                .color(net.md_5.bungee.api.ChatColor.GREEN);
        HoverEvent hoverEvt2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover2.create());

        HoverEvent hoverEvt = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create());

        StringBuilder reason = new StringBuilder();
        for(EnumCheatType type: types)
            reason.append("\n           ").append(ChatColor.BLUE).append(type.getText());

        ComponentBuilder b = new ComponentBuilder( ChatColor.DARK_GREEN + ChatColor.BOLD.toString() + "チートレポート");
        b.append("\n");
        b.append(ChatColor.GRAY + formatter.format(date))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, unixTime.create()));
        b.append("\n");
        b.append(getLine("対象者", id))
                .event(hoverEvt);
        b.append("\n");
        ComponentBuilder b1 = new ComponentBuilder(new TextComponent(b.create()));

        b1.append(getLine("報告者", issueById))
                .event(hoverEvt2);
        b1.append("\n");

        ComponentBuilder b2 = new ComponentBuilder(new TextComponent(b1.create()));

        b2.append(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "重大度: " + SeverityLevelUtils.getSeverity(types).getColor() + SeverityLevelUtils.getSeverity(types).getText() + "\n");
        b2.append("\n");
        b2.append(getLine("報告理由", reason.toString()));
        b2.append("\n");
        meta.spigot().addPage(b2.create());
        book.setItemMeta(meta);
        return book;
    }

    private static String getLine(String prefix, String value)
    {
        return ChatColor.AQUA + ChatColor.BOLD.toString() + prefix + ChatColor.GREEN + ": " + ChatColor.BLUE.toString() + value;
    }
}
