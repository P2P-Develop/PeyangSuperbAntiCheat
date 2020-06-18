package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;

import java.math.*;

public class CommandAutoMessage implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!Permission.unPermMessage(sender, "psr.automsg"))
            return true;

        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.AQUA + "-----" +
                    ChatColor.GREEN + "[" +
                    ChatColor.BLUE + "PeyangSuperbAntiCheat" +
                    ChatColor.GREEN + "]" +
                    ChatColor.AQUA + "-----");
            sender.sendMessage(ChatColor.AQUA + "項目名" + ChatColor.WHITE + "：" + ChatColor.GREEN + "現在値");
            sender.sendMessage(TextBuilder.getLine("enable", PeyangSuperbAntiCheat.isAutoMessageEnabled ? "True": "False"));
            sender.sendMessage(TextBuilder.getLine("time", PeyangSuperbAntiCheat.time + ChatColor.RED.toString() + "(分)"));
            sender.sendMessage(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Tips：/automsg toggle で有効/無効を切り替えることができます。");
            return true;
        }
        else if (args.length == 1 && args[0].equals("toggle"))
        {
            Bukkit.dispatchCommand(sender, "automessage enable " + !PeyangSuperbAntiCheat.isAutoMessageEnabled);
            return true;
        }

        if (args.length != 2)
        {
            sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：引数の数が不正です。/psr help でヘルプを見てください。");
            return true;
        }

        String path = args[0].toLowerCase();
        String value = args[1];

        switch (path)
        {
            case "enable":
                if (!value.toLowerCase().equals("true") && !value.toLowerCase().equals("false"))
                {
                    sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：" + value + "は、TrueでもFalseでもありません！");
                    return true;
                }
                PeyangSuperbAntiCheat.isAutoMessageEnabled = Boolean.parseBoolean(value.toLowerCase());
                PeyangSuperbAntiCheat.config.set("autoMessage.enabled", PeyangSuperbAntiCheat.isAutoMessageEnabled);
                PeyangSuperbAntiCheat.getPlugin().saveConfig();
                PeyangSuperbAntiCheat.getPlugin().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "成功：定期メッセージを、" +
                        (PeyangSuperbAntiCheat.isAutoMessageEnabled ? "有効": "無効") +
                        "化し1ました！");

                if (!PeyangSuperbAntiCheat.isAutoMessageEnabled)
                {
                    if (RunnableUtil.isStarted(PeyangSuperbAntiCheat.autoMessage))
                        PeyangSuperbAntiCheat.autoMessage.cancel();
                }
                else
                    if (!RunnableUtil.isStarted(PeyangSuperbAntiCheat.autoMessage))
                        PeyangSuperbAntiCheat.autoMessage.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 20 * (PeyangSuperbAntiCheat.time * 60));
                    return true;
            case "time":
                long time;
                try
                {
                    time = Long.parseLong(value);
                }
                catch (Exception e)
                {
                    sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString()+ "エラー：" + value + " は、有効な数字ではありません！");
                    return true;
                }

                if (time == 0L)
                    time = 1L;
                PeyangSuperbAntiCheat.time = time;

                PeyangSuperbAntiCheat.config.set("autoMessage.time", PeyangSuperbAntiCheat.time);
                PeyangSuperbAntiCheat.getPlugin().saveConfig();
                PeyangSuperbAntiCheat.getPlugin().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "成功：定期メッセージの時間を、" + BigDecimal.valueOf(time).toPlainString() + " 分にセットしました！");

                if (PeyangSuperbAntiCheat.isAutoMessageEnabled)
                {
                    if (RunnableUtil.isStarted(PeyangSuperbAntiCheat.autoMessage))
                        PeyangSuperbAntiCheat.autoMessage.cancel();

                    PeyangSuperbAntiCheat.autoMessage.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 20 * (time * 60));
                }

                return true;
            default:
                sender.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "エラー：設定項目が見つかりませんでした！");
                return true;
        }
    }

}
