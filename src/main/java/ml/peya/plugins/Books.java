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
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report " + player.getName() + " " + tmpReasonText + " $__BOOKS__;"))
                .append(MessageEngine.get("reportbook.cancel"))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report $$cancel$$"));

        meta.spigot().addPage(component.create());
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack getShowBook(String id, String uuid, String issueById, String issueByUuid, BigDecimal dateInt, ArrayList<EnumCheatType> types)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        ComponentBuilder b = new ComponentBuilder(MessageEngine.get("book.text.report"))
                .append("\n")
                .append(ChatColor.GRAY + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(dateInt.longValue())))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextBuilder.getLine("UNIX秒", String.valueOf(dateInt))).create())).append("\n")
                .append(MessageEngine.get("book.text.issueTo", MessageEngine.hsh("id", id)))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MessageEngine.get("book.text.uuid", MessageEngine.hsh("uuid", uuid))).create()))
                .append("\n");

        ComponentBuilder b1 = new ComponentBuilder(new TextComponent(b.create()))
                .append(MessageEngine.get("book.text.issueBy", MessageEngine.hsh("id", issueById)))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MessageEngine.get("book.text.uuid", MessageEngine.hsh("uuid", issueByUuid))).create()))
                .append("\n");

        ComponentBuilder b2 = new ComponentBuilder(new TextComponent(b1.create()));

        HashMap<String, Object> map = new HashMap<>();
        map.put("color", SeverityLevelUtils.getSeverity(types).getColor());
        map.put("level", SeverityLevelUtils.getSeverity(types).getText());

        Arrays.asList(MessageEngine.get("book.text.severity", map), "\n", MessageEngine.get("book.text.reason", MessageEngine.hsh("reason", types.parallelStream().map(type -> "\n           " + ChatColor.BLUE + type.getText()).collect(Collectors.joining()))), "\n").parallelStream().forEachOrdered(b2::append);
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

        builder.append("\n")
                .append("\n");

        int count = 0;

        for (String id : mods.keySet())
        {
            builder.append(ChatColor.RED + id + ChatColor.GRAY + ": " + ChatColor.BLUE + mods.get(id))
                    .append("\n");
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
        BookMeta meta = (BookMeta) book.getItemMeta();
        return book.getType() == Material.WRITTEN_BOOK && meta.hasTitle() && meta.getTitle().equals("-") && meta.hasAuthor() && meta.getAuthor().equals("AntiCheat Dev") && meta.hasLore() && meta.getLore().size() == 1 && meta.getLore().get(0).equals(ChatColor.GRAY + ChatColor.ITALIC.toString() + "PSAC Book");
    }
}
