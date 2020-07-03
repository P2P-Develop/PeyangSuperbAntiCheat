package ml.peya.plugins;

import com.comphenix.protocol.*;
import ml.peya.plugins.Enum.*;
import ml.peya.plugins.*;
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
        ComponentBuilder component = new ComponentBuilder(MessageEngihe.get("reportbook.cheat"));
        component.append("\n");

        for (EnumCheatType type: types)
        {
            String text = " ⦾ " + type.getText() + "\n";
            if (type.isSelected())
                text = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + text;
            component.append(text)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " +  player.getName() + " " + tmpReasonText + " " + type.getSysName() + " \\"));
        }

        component.append("\n\n");


        meta.setTitle("-");
        meta.setAuthor("AntiCheat Dev");

        component.append(MessageEngihe.get("reportbook.submit"))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + " " + tmpReasonText + " bybooks"));

        component.append(MessageEngihe.get("reportbook.cancel"))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report $$cancel$$"));

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

        ComponentBuilder hover = new ComponentBuilder(MessageEngihe.get("book.text.uuid", MessageEngihe.hsh("uuid", uuid)));

        ComponentBuilder unixTime = new ComponentBuilder(TextBuilder.getLine("UNIX秒", String.valueOf(dateInt)));


        ComponentBuilder hover2 = new ComponentBuilder( MessageEngihe.get("book.text.uuid", MessageEngihe.hsh("uuid", issueByUuid)));
        HoverEvent hoverEvt2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover2.create());

        HoverEvent hoverEvt = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create());

        StringBuilder reason = new StringBuilder();
        for(EnumCheatType type: types)
            reason.append("\n           ").append(ChatColor.BLUE).append(type.getText());

        ComponentBuilder b = new ComponentBuilder( MessageEngihe.get("book.text.report"));
        b.append("\n");
        b.append(ChatColor.GRAY + formatter.format(date))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, unixTime.create()));
        b.append("\n");
        b.append(MessageEngihe.get("book.text.issueTo", MessageEngihe.hsh("id", id)))
                .event(hoverEvt);
        b.append("\n");
        ComponentBuilder b1 = new ComponentBuilder(new TextComponent(b.create()));

        b1.append(MessageEngihe.get("book.text.issueBy", MessageEngihe.hsh("id", issueById)))
                .event(hoverEvt2);
        b1.append("\n");

        ComponentBuilder b2 = new ComponentBuilder(new TextComponent(b1.create()));

        HashMap<String, Object> map = new HashMap<>();
        map.put("color", SeverityLevelUtils.getSeverity(types).getColor());
        map.put("level", SeverityLevelUtils.getSeverity(types).getText());

        b2.append(MessageEngihe.get("book.text.severity", map));
        b2.append("\n");
        b2.append(MessageEngihe.get("book.text.reason", MessageEngihe.hsh("reason", reason.toString())));
        b2.append("\n");
        meta.spigot().addPage(b2.create());
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack getModsBook(Player player, HashMap<String, String> mods)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        ComponentBuilder builder = new ComponentBuilder(MessageEngihe.get("message.mods.title", MessageEngihe.hsh("name", player.getName())));

        builder.append("\n");
        builder.append("\n");

        for (String id: mods.keySet())
        {
            String version = mods.get(id);
            builder.append(ChatColor.RED + id + ChatColor.GRAY + ": " + ChatColor.BLUE + version);
            builder.append("\n");
        }

        meta.spigot().addPage(builder.create());
        book.setItemMeta(meta);
        return book;
    }

}
