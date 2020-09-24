package ml.peya.plugins.Utils;

import ml.peya.plugins.Enum.EnumCheatType;
import ml.peya.plugins.Enum.EnumSeverity;
import ml.peya.plugins.Moderate.BanAnalyzer;
import ml.peya.plugins.Moderate.CheatTypeUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;
import static ml.peya.plugins.Variables.config;

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

        nextBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(get("book.words.next")).create()));
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
     * @param types       判定タイプ。
     * @param sender      イベントsender。
     */
    public static void showText(String id, String uuid, String issueById, String issueByUuid, BigDecimal dateInt, ArrayList<EnumCheatType> types, CommandSender sender)
    {
        ComponentBuilder hover = new ComponentBuilder(get("book.clickable"));

        sender.spigot()
                .sendMessage(new ComponentBuilder("    " + get("book.text.issueBy", pair("id", issueById)))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()))
                        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, issueByUuid))
                        .create());

        sender.spigot()
                .sendMessage(new ComponentBuilder("    " + get("book.text.issueTo", pair("id", id)))
                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()))
                        .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid))
                        .create());

        sender.sendMessage("    " + get("book.text.dateTime", pair("time", new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(dateInt.longValue())))));

        sender.sendMessage("    " + get("book.text.reason", pair("reason", types.parallelStream()
                .map(type -> "        " + type.getText() + "\n")
                .collect(Collectors.joining()))));

        HashMap<String, Object> serv = new HashMap<>();
        serv.put("color", SeverityLevels.getSeverity(types)
                .getColor());
        serv.put("level", SeverityLevels.getSeverity(types)
                .getText());
        sender.sendMessage(get("book.text.severity", serv));
    }

    /**
     * viewした際のライン
     *
     * @param id        プレイヤーID。
     * @param issueById 報告した人管理ID。
     * @param types     判定タイプ。
     * @param mngid     管理ID2。
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
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(get("book.click.openAbout")).create()))
                .append("   ");

        if (sender instanceof Player && sender.hasPermission("psac.drop"))
        {
            b.append(get("book.click.delete"))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/psac drop " + mngid))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(get("book.click.deleteReport")).create()));
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
        final String prefix = ChatColor.YELLOW + "Level " + severity.getColor();
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
        return new ComponentBuilder(get("kick.broadcastWd"));
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
        map.put("uuid", player.getUniqueId()
                .toString());

        return getBroadCastWdDetectionText()
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(get("kick.broadcastAdmin", map)).create()));
    }

    /**
     * 通常テスト時の最終結果を組み立てる。
     *
     * @param name   プレイヤー名。
     * @param VL     算出されたVL。
     * @param kickVL VLキック基準値。
     * @return 組み立てたやつ。
     */
    public static ComponentBuilder textTestRep(String name, int VL, int kickVL)
    {
        if (config.getBoolean("message.lynx"))
            return new ComponentBuilder(get("message.auraCheck.bot.lynx", pair("hit", VL)));

        return new ComponentBuilder(get("base.prefix") + "\n")
                .append(get("message.auraCheck.result.prefix", pair("name", name)))
                .append("\n")
                .append(get("message.auraCheck.result.vl", pair("vl", String.valueOf(VL))))
                .append("\n")
                .append(get("message.auraCheck.result.vlGraph"))
                .append("\n")
                .append(OptGraphGenerator.genGraph(VL, kickVL))
                .append("\n")
                .append(get("message.auraCheck.result.result", pair("result", VL >= kickVL ? get("message.auraCheck.result.words.kick"): get("message.auraCheck.result.words.ok"))));
    }

    /**
     * AuraPanicBotの結果を組み立てる。
     *
     * @param name PlayerName
     * @param vl   そのまんま
     * @return 完成後。
     */
    public static ComponentBuilder textPanicRep(String name, int vl)
    {
        if (config.getBoolean("message.lynx"))
            return new ComponentBuilder("");
        return new ComponentBuilder(get("base.prefix"))
                .append("\n")
                .append(get("message.auraCheck.result.prefix", pair("name", name)))
                .append("\n")
                .append(get("message.auraCheck.result.vl", pair("vl", String.valueOf(vl))));
    }

    /**
     * BanまたはKickのデータ入手やフォーマットに使用する。
     *
     * @param ban  Bansらしい
     * @param type 判定タイプ？
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
