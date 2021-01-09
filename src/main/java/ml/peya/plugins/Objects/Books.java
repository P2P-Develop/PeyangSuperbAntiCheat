package ml.peya.plugins.Objects;

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

import static ml.peya.plugins.Utils.MessageEngine.*;

/**
 * 本のアイテムをまとめたやつ
 */
public class Books
{
    /**
     * 報告用の本を生成します。
     *
     * @param player ぶち込む報告対象プレイヤー。
     * @param types  判定タイプ。
     * @return アイテム自体を返すのであとはgiveします。
     */
    public static ItemStack getReportBook(Player player, EnumCheatType... types)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        final String tmpReasonText = Arrays.stream(types)
            .parallel()
            .map(type -> type.isSelected() ? type.getSysName() + " ": "")
            .collect(Collectors.joining());
        ComponentBuilder component = new ComponentBuilder(get("reportbook.cheat"));
        component.append("\n");

        for (EnumCheatType type : types)
        {
            String text = " ⦾ " + type.getText() + "\n";
            if (type.isSelected())
                text = ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + text;

            component.append(text).event(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                "/report " + player.getName() + " " + tmpReasonText + " " + type.getSysName() + " \\"
            ));
        }

        component.append("\n\n");

        meta.setTitle("-");
        meta.setAuthor("AntiCheat Dev");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + ChatColor.ITALIC.toString() + "PSAC Book"));

        component.append(get("reportbook.submit"))
            .event(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                "/report " + player.getName() + " " + tmpReasonText + " -B"
            ))
            .append("\n")
            .append(get("reportbook.cancel"))
            .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/report $$cancel$$"));

        meta.spigot().addPage(component.create());
        book.setItemMeta(meta);
        return book;
    }

    /**
     * /psac showした時のいろいろをなんかやってくれます。
     *
     * @param id          管理ID。
     * @param uuid        プレイヤー UUID。
     * @param issueById   なんこれ
     * @param issueByUuid なんこれ2nd
     * @param dateInt     UNIX時間。
     * @param types       判定タイプ。
     * @return /psac showした時の詳細を書いた本。
     */
    public static ItemStack getShowBook(String id, String uuid, String issueById, String issueByUuid, BigDecimal dateInt, ArrayList<EnumCheatType> types)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        ComponentBuilder b2 = new ComponentBuilder(new TextComponent(new ComponentBuilder(new TextComponent(
            new ComponentBuilder(get("book.text.report"))
                .append("\n")
                .append(ChatColor.GRAY + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
                    .format(new Date(dateInt.longValue())))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextBuilder
                    .getLine("UNIX秒", String.valueOf(dateInt))).create()))
                .append("\n")
                .append(get("book.text.issueTo", pair("id", id)))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(get("book.text.uuid", pair("uuid", uuid)))
                    .create()))
                .append("\n")
                .create()))
            .append(get("book.text.issueBy", pair("id", issueById)))
            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(get("book.text.uuid", pair("uuid", issueByUuid)))
                .create()))
            .append("\n")
            .create()));

        HashMap<String, Object> map = new HashMap<>();
        map.put("color", SeverityLevels.getSeverity(types).getColor());
        map.put("level", SeverityLevels.getSeverity(types).getText());

        Arrays.asList(get("book.text.severity", map), "\n", get("book.text.reason", pair("reason", types
            .parallelStream()
            .map(type -> "\n           " + ChatColor.BLUE + type.getText())
            .collect(Collectors.joining()))), "\n")
            .parallelStream()
            .forEachOrdered(b2::append);
        meta.setTitle("-");
        meta.setAuthor("AntiCheat Dev");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + ChatColor.ITALIC.toString() + "PSAC Book"));
        meta.spigot().addPage(b2.create());
        book.setItemMeta(meta);
        return book;
    }

    /**
     * Q. これは PSAC の 本 です か？
     *
     * @param book 本。
     * @return A. ちがかったらfalseします。
     */
    public static boolean hasPSACBook(ItemStack book)
    {
        if (book.getType() != Material.WRITTEN_BOOK)
            return false; //Exception回避

        BookMeta meta = (BookMeta) book.getItemMeta();
        return meta.hasTitle() && meta.getTitle().equals("-") && meta.hasAuthor() && meta.getAuthor()
            .equals("AntiCheat Dev") && meta
            .hasLore() && meta.getLore().size() == 1 && meta.getLore().get(0)
            .equals(ChatColor.GRAY + ChatColor.ITALIC
                .toString() + "PSAC Book");
    }
}
