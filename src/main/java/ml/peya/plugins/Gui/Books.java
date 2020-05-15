package ml.peya.plugins.Gui;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

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

        component.append(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD +  "レポートを提出")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + " " + tmpReasonText + " bybooks"));

        component.append(ChatColor.RED + "レポートをキャンセル")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ""));

        meta.spigot().addPage(component.create());
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack getShowBook(String id, String uuid, String issueById, String issueByUuid, int dateInt, ArrayList<EnumCheatType> types)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        Date date = new Date(dateInt);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        ComponentBuilder hover = new ComponentBuilder( "クリックしてチャットに");
        hover.color(net.md_5.bungee.api.ChatColor.AQUA);
        hover.append("UUID")
                .color(net.md_5.bungee.api.ChatColor.GREEN);
        hover.append("を貼り付け")
                .color(net.md_5.bungee.api.ChatColor.AQUA);

        ComponentBuilder unixTime = new ComponentBuilder(getLine("UNIX秒", String.valueOf(dateInt)));

        HoverEvent hoverEvt = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create());

        StringBuilder reason = new StringBuilder();
        for(EnumCheatType type: types)
            reason.append("        ").append(ChatColor.BLUE).append(type.getText()).append("\n");

        ComponentBuilder b = new ComponentBuilder( ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "チートレポート");
        b.append("\n");
        b.append(ChatColor.GRAY + formatter.format(date));
        b.append("\n");
        b.append(getLine("報告対象者", id))
                .event(hoverEvt)
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid));
        b.append(getLine("報告者", issueById))
                .event(hoverEvt)
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, issueByUuid));
        b.append(getLine("報告日時", formatter.format(date)))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, unixTime.create()));
        b.append(ChatColor.DARK_RED + "重大度レベル: " + SeverityLevelUtils.getSeverity(types).getColor() + SeverityLevelUtils.getSeverity(types).getText());
        b.append(getLine("報告理由", reason.toString()));
        meta.spigot().addPage(b.create());
        book.setItemMeta(meta);
        return book;
    }

    private static String getLine(String prefix, String value)
    {
        return ChatColor.AQUA + ChatColor.BOLD.toString() + prefix + ChatColor.GREEN + ": " + ChatColor.BLUE.toString() + value + "\n";
    }
}
