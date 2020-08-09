package ml.peya.plugins.Utils;

import ml.peya.plugins.Enum.*;
import ml.peya.plugins.Moderate.*;
import ml.peya.plugins.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.math.*;
import java.text.*;
import java.util.*;
import java.util.stream.*;

/**
 * チャット送信とかに使用するやつを組み立てます。MessageEngineのフロントエンドみたいな。
 */
public class TextBuilder
{
    /**
     * ボタン類をなんとなく組み立ててくれます。
     *
     * @param bind   なんこれ
     * @param button ボタン...?
     * @return 完成後
     */
    private static TextComponent getPrevNextButton(int bind, String button)
    {
        TextComponent nextBtn = new TextComponent(ChatColor.GREEN + "(" +
                ChatColor.AQUA + button +
                ChatColor.GREEN + ")");
        nextBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac view " + bind));

        nextBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MessageEngine.get("book.words.next")).create()));
        return nextBtn;
    }

    /**
     * 上のメソッドを分けたやつ。
     *
     * @param next ボタン
     * @return 完成後
     */
    public static TextComponent getNextButton(int next)
    {
        return getPrevNextButton(next, "=>");
    }

    /**
     * 上のメソッドを分けたやつ2nd。
     *
     * @param previous ボタン
     * @return 完成後
     */
    public static TextComponent getPrevButton(int previous)
    {
        return getPrevNextButton(previous, "<=");
    }

    /**
     * 多すぎだろJavadoc書く人の気持ちにもなれよごｒｒｒ(ry
     *
     * @param id          管理ID。
     * @param uuid        プレイヤーUUID。
     * @param issueById   報告した人管理ID。
     * @param issueByUuid 報告した人のUUID。
     * @param dateInt     UNIX時間。
     * @param types       罪状。
     * @param sender      イベントsender。
     */
    public static void showText(String id, String uuid, String issueById, String issueByUuid, BigDecimal dateInt, ArrayList<EnumCheatType> types, CommandSender sender)
    {
        ComponentBuilder hover = new ComponentBuilder(MessageEngine.get("book.clickable"));

        ComponentBuilder b1 = new ComponentBuilder("    " + MessageEngine.get("book.text.issueBy", MessageEngine.hsh("id", issueById)))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()))
                .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, issueByUuid));
        sender.spigot().sendMessage(b1.create());

        ComponentBuilder b2 = new ComponentBuilder("    " + MessageEngine.get("book.text.issueTo", MessageEngine.hsh("id", id)))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create())).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid));
        sender.spigot().sendMessage(b2.create());

        sender.sendMessage("    " + MessageEngine.get("book.text.dateTime", MessageEngine.hsh("time", new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(dateInt.longValue())))));

        sender.sendMessage("    " + MessageEngine.get("book.text.reason", MessageEngine.hsh("reason", types.parallelStream().map(type -> "        " + type.getText() + "\n").collect(Collectors.joining()))));

        HashMap<String, Object> serv = new HashMap<>();
        serv.put("color", SeverityLevels.getSeverity(types).getColor());
        serv.put("level", SeverityLevels.getSeverity(types).getText());
        sender.sendMessage(MessageEngine.get("book.text.severity", serv));
    }

    /**
     * showとかに使うんじゃね？しらんけど。
     *
     * @param id        管理ID。
     * @param issueById 報告した人管理ID。
     * @param types     罪状。
     * @param mngid     管理ID2nd。
     * @param sender    イベントsender。
     * @return 完成後。
     */
    public static ComponentBuilder getLine(String id, String issueById, ArrayList<EnumCheatType> types, String mngid, CommandSender sender)
    {
        EnumSeverity severity = SeverityLevels.getSeverity(types);

        ComponentBuilder b = new ComponentBuilder("")
                .append(ChatColor.GREEN + id)
                .append("   ")
                .append(ChatColor.BLUE + issueById)
                .append("   ")
                .append(severity.getText())
                .color(severity.getColor())
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac show " + mngid))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MessageEngine.get("book.click.openAbout")).create()))
                .append("   ");
        if (sender instanceof Player && sender.hasPermission("psac.drop"))
        {
            b.append(MessageEngine.get("book.click.delete"))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac drop " + mngid))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MessageEngine.get("book.click.deleteReport")).create()));
        }
        else
            b.append(ChatColor.YELLOW + mngid);

        return b;
    }

    /**
     * 謎にオーバーロード。
     *
     * @param prefix [PSAC]ってやつ。
     * @param value  値...?
     * @return かーんせーい！
     */
    public static String getLine(String prefix, String value)
    {
        return ChatColor.AQUA + prefix + ChatColor.WHITE + "：" + ChatColor.GREEN + value;
    }

    /**
     * レベル返す。
     *
     * @param severity レベル。
     * @return レベル返す。
     */
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

    /**
     * ボタンゲットする。
     *
     * @param prev     前に戻るボタン
     * @param next     次に進むボタン
     * @param prevFlag ボタン使える？
     * @param nextFlag ボタン使える2nd？
     * @return 完成後。
     */
    public static ComponentBuilder getNextPrevButtonText(TextComponent prev, TextComponent next, boolean prevFlag, boolean nextFlag)
    {
        TextComponent uBar = new TextComponent("----");
        uBar.setColor(net.md_5.bungee.api.ChatColor.AQUA);
        return new ComponentBuilder(String.valueOf(prevFlag ? prev: uBar))
                .append("------------------------")
                .color(net.md_5.bungee.api.ChatColor.AQUA)
                .append(String.valueOf(nextFlag ? next: uBar));
    }

    /**
     * メッセージ変換する。
     *
     * @return 変換後。
     */
    public static ComponentBuilder getBroadCastWdDetectionText()
    {
        return new ComponentBuilder(MessageEngine.get("kick.broadcastWd"));
    }

    /**
     * 上のオーバーロード。
     *
     * @param player 罪を犯しかけたプレイヤー。
     * @return メッセージを結果として組み立てたやつ。
     */
    public static ComponentBuilder getBroadCastWdDetectionText(Player player)
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", player.getName());
        map.put("uuid", player.getUniqueId().toString());

        ComponentBuilder component = getBroadCastWdDetectionText();
        component.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(MessageEngine.get("kick.broadcastAdmin", map)).create()));
        return component;
    }

    public static ComponentBuilder textTestRep(String name, int VL, int kickVL)
    {
        if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            return new ComponentBuilder(MessageEngine.get("message.auraCheck.bot.lynx", MessageEngine.hsh("hit", VL)));

        return new ComponentBuilder(MessageEngine.get("base.prefix") + "\n")
                .append(MessageEngine.get("message.auraCheck.result.prefix", MessageEngine.hsh("name", name)))
                .append("\n")
                .append(MessageEngine.get("message.auraCheck.result.vl", MessageEngine.hsh("vl", String.valueOf(VL))))
                .append("\n")
                .append(MessageEngine.get("message.auraCheck.result.vlGraph"))
                .append("\n")
                .append(OptGraphGenerator.genGraph(VL, kickVL))
                .append("\n")
                .append(MessageEngine.get("message.auraCheck.result.result", MessageEngine.hsh("result", VL >= kickVL ? MessageEngine.get("message.auraCheck.result.words.kick"): MessageEngine.get("message.auraCheck.result.words.ok"))));
    }

    /**
     * なんこれ
     *
     * @param name PlayerName
     * @param vl   そのまんま
     * @return 完成後。
     */
    public static ComponentBuilder textPanicRep(String name, int vl)
    {
        if (PeyangSuperbAntiCheat.config.getBoolean("message.lynx"))
            return new ComponentBuilder("");
        return new ComponentBuilder(MessageEngine.get("base.prefix"))
                .append("\n")
                .append(MessageEngine.get("message.auraCheck.result.prefix", MessageEngine.hsh("name", name)))
                .append("\n")
                .append(MessageEngine.get("message.auraCheck.result.vl", MessageEngine.hsh("vl", String.valueOf(vl))));
    }

    /**
     * いやなにこれ
     *
     * @param ban  Bansらしい
     * @param type 罪状？
     * @return 完成後。
     */
    public static ComponentBuilder getTextBan(BanAnalyzer.Bans ban, BanAnalyzer.Type type)
    {
        StringBuilder reasonSet = new StringBuilder();
        Arrays.stream(ban.getReason().split(", ")).parallel().forEachOrdered(reason -> {
            EnumCheatType tp = CheatTypeUtils.getCheatTypeFromString(reason);
            if (tp == null)
                reasonSet.append(reason).append(", ");
            else
                reasonSet.append(tp.getText());
        });

        if (reasonSet.toString().endsWith(", "))
            reasonSet.setLength(reasonSet.length() - 2);

        return new ComponentBuilder(ChatColor.YELLOW + (type == BanAnalyzer.Type.KICK ? "Kick": "Ban"))
                .append(" - " + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(ban.getDate())))
                .append(ChatColor.WHITE + " " + ChatColor.ITALIC + reasonSet.toString());
    }

}
