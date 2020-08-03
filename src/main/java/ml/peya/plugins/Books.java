package ml.peya.plugins;

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
import java.util.stream.*;

public class Books
{

    public static ItemStack getReportBook(Player player, EnumCheatType... types)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        String tmpReasonText = Arrays.stream(types).parallel().map(type -> type.isSelected() ? type.getSysName() + " ": "").collect(Collectors.joining());
        ComponentBuilder component = new ComponentBuilder(MessageEngine.get("reportbook.cheat"));
        component.append("\n");

        for (EnumCheatType type : types)
        {
            String text = " ⦾ " + type.getText() + "\n";
            if (type.isSelected())
                text = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + text;
            component.append(text)
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + " " + tmpReasonText + " " + type.getSysName() + " \\"));
        }

        component.append("\n\n");

        meta.setTitle("-");
        meta.setAuthor("AntiCheat Dev");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + ChatColor.ITALIC.toString() + "PSAC Book"));

        component.append(MessageEngine.get("reportbook.submit"))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + " " + tmpReasonText + " $__BOOKS__;"));

        component.append(MessageEngine.get("reportbook.cancel"))
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

        ComponentBuilder hover = new ComponentBuilder(MessageEngine.get("book.text.uuid", MessageEngine.hsh("uuid", uuid)));

        ComponentBuilder unixTime = new ComponentBuilder(TextBuilder.getLine("UNIX秒", String.valueOf(dateInt)));


        ComponentBuilder hover2 = new ComponentBuilder(MessageEngine.get("book.text.uuid", MessageEngine.hsh("uuid", issueByUuid)));
        HoverEvent hoverEvt2 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover2.create());

        HoverEvent hoverEvt = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create());

        String reason = types.parallelStream().map(type -> "\n           " + ChatColor.BLUE + type.getText()).collect(Collectors.joining());

        ComponentBuilder b = new ComponentBuilder(MessageEngine.get("book.text.report"));
        b.append("\n");
        b.append(ChatColor.GRAY + formatter.format(date))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, unixTime.create()));
        b.append("\n");
        b.append(MessageEngine.get("book.text.issueTo", MessageEngine.hsh("id", id)))
                .event(hoverEvt);
        b.append("\n");
        ComponentBuilder b1 = new ComponentBuilder(new TextComponent(b.create()));

        b1.append(MessageEngine.get("book.text.issueBy", MessageEngine.hsh("id", issueById)))
                .event(hoverEvt2);
        b1.append("\n");

        ComponentBuilder b2 = new ComponentBuilder(new TextComponent(b1.create()));

        HashMap<String, Object> map = new HashMap<>();
        map.put("color", SeverityLevelUtils.getSeverity(types).getColor());
        map.put("level", SeverityLevelUtils.getSeverity(types).getText());

        b2.append(MessageEngine.get("book.text.severity", map));
        b2.append("\n");
        b2.append(MessageEngine.get("book.text.reason", MessageEngine.hsh("reason", reason)));
        b2.append("\n");
        meta.setTitle("-");
        meta.setAuthor("AntiCheat Dev");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + ChatColor.ITALIC.toString() + "PSAC Book"));
        meta.spigot().addPage(b2.create());
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack getModsBook(Player player, HashMap<String, String> mods)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        ComponentBuilder builder = new ComponentBuilder(MessageEngine.get("message.mods.title", MessageEngine.hsh("name", player.getName())));

        builder.append("\n");
        builder.append("\n");

        int count = 0;

        for (String id : mods.keySet())
        {
            String version = mods.get(id);
            builder.append(ChatColor.RED + id + ChatColor.GRAY + ": " + ChatColor.BLUE + version);
            builder.append("\n");
            count++;
            if (count < 10)
                continue;

            count = 0;
            meta.spigot().addPage(builder.create());
            builder = new ComponentBuilder("");
        }

        meta.spigot().addPage(builder.create());

        meta.setTitle("-");
        meta.setAuthor("AntiCheat Dev");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + ChatColor.ITALIC.toString() + "PSAC Book"));

        book.setItemMeta(meta);
        return book;
    }

    public static boolean hasPSACBook(ItemStack book)
    {
        if (book.getType() != Material.WRITTEN_BOOK)
            return false;
        BookMeta meta = (BookMeta) book.getItemMeta();

        if (!meta.hasTitle() || !meta.getTitle().equals("-") || !meta.hasAuthor() || !meta.getAuthor().equals("AntiCheat Dev"))
            return false;

        return meta.hasLore() && meta.getLore().size() == 1 && meta.getLore().get(0).equals(ChatColor.GRAY + ChatColor.ITALIC.toString() + "PSAC Book");
    }
}
