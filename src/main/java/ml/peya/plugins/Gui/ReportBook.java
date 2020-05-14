package ml.peya.plugins.Gui;

import ml.peya.plugins.Enum.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;


public class ReportBook
{
    public static ItemStack getBook(Player player, EnumCheatType... types)
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
}
