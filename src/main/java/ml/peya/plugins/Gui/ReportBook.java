package ml.peya.plugins.Gui;

import ml.peya.plugins.Enum.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

public class ReportBook
{
    public static ItemStack getBook(Player player, EnumCheatType... types)
    {
        ItemStack book = new ItemStack(Material.KNOWLEDGE_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        ArrayList<String> pages = new ArrayList<>();
        StringBuilder tmpReasonText = new StringBuilder();
        for (EnumCheatType type: types)
        {
            String text = "●" + type.getText();
            if (type.isSelected())
                text = ChatColor.GREEN + text;
            TextComponent component = new TextComponent(text);
            String reasonText = type.isSelected() ? type.getText(): "";
            tmpReasonText.append(reasonText).append(" ");
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + tmpReasonText + "\\"));
            pages.add(component.getText());
        }
        pages.add("");
        pages.add("");

        TextComponent submit = new TextComponent(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "SUBMIT REPORT");
        submit.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + tmpReasonText));
        pages.add(Arrays.toString(pages.toArray()));
        meta.setDisplayName("-");
        book.setItemMeta(meta);
        return book;
    }
}
