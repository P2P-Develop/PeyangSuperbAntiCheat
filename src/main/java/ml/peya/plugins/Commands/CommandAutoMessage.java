package ml.peya.plugins.Commands;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.command.*;

import java.math.*;
import java.util.*;

public class CommandAutoMessage implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (ErrorMessageSender.unPermMessage(sender, "psr.automsg"))
            return true;

        if (args.length == 0)
        {
            sender.sendMessage(MessageEngihe.get("base.prefix"));


            HashMap<String, Object> map = new HashMap<>();
            map.put("enableValue", PeyangSuperbAntiCheat.isAutoMessageEnabled ? MessageEngihe.get("base.true"): MessageEngihe.get("base.false"));
            map.put("time", PeyangSuperbAntiCheat.time);

            sender.sendMessage(MessageEngihe.get("message.autoMsg.zeroLeng", map));
            return true;
        }
        else if (args.length == 1 && args[0].equals("toggle"))
        {
            Bukkit.dispatchCommand(sender, "automessage enable " + !PeyangSuperbAntiCheat.isAutoMessageEnabled);
            return true;
        }

        if (ErrorMessageSender.invalidLengthMessage(sender, args, 2, 2))
            return true;

        String path = args[0].toLowerCase();
        String value = args[1];

        switch (path)
        {
            case "enable":
                if (!value.toLowerCase().equals("true") && !value.toLowerCase().equals("false"))
                {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("value", value);
                    map.put("true", MessageEngihe.get("base.true"));
                    map.put("false", MessageEngihe.get("base.false"));
                    sender.sendMessage(MessageEngihe.get("error.automessage.notTrueOrFalse", map));
                    return true;
                }
                PeyangSuperbAntiCheat.isAutoMessageEnabled = Boolean.parseBoolean(value.toLowerCase());
                PeyangSuperbAntiCheat.config.set("autoMessage.enabled", PeyangSuperbAntiCheat.isAutoMessageEnabled);
                PeyangSuperbAntiCheat.getPlugin().saveConfig();
                PeyangSuperbAntiCheat.getPlugin().reloadConfig();

                sender.sendMessage(MessageEngihe.get("message.autoMsg.successTrueChange", MessageEngihe.hsh("value",
                        PeyangSuperbAntiCheat.isAutoMessageEnabled ? MessageEngihe.get("base.enable"): MessageEngihe.get("base.disable"))));

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
                    sender.sendMessage(MessageEngihe.get("error.automessage.notNumeric", MessageEngihe.hsh("value", value)));
                    return true;
                }

                if (time == 0L)
                    time = 1L;
                PeyangSuperbAntiCheat.time = time;

                PeyangSuperbAntiCheat.config.set("autoMessage.time", PeyangSuperbAntiCheat.time);
                PeyangSuperbAntiCheat.getPlugin().saveConfig();
                PeyangSuperbAntiCheat.getPlugin().reloadConfig();
                sender.sendMessage(MessageEngihe.get("message.autoMsg.successTimeChange", MessageEngihe.hsh("value", BigDecimal.valueOf(time).toPlainString())));

                if (PeyangSuperbAntiCheat.isAutoMessageEnabled)
                {
                    try
                    {
                        PeyangSuperbAntiCheat.autoMessage.cancel();
                    }
                    catch (Exception ignored) { }
                    PeyangSuperbAntiCheat.autoMessage = new AutoMessageTask();
                    PeyangSuperbAntiCheat.autoMessage.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0, 20 * (time * 60));
                }

                return true;
            default:
                sender.sendMessage(MessageEngihe.get("error.automessage.notFoundSettings"));

                return true;
        }
    }

}
