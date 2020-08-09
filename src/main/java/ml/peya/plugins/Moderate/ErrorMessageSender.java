package ml.peya.plugins.Moderate;

import ml.peya.plugins.*;
import org.bukkit.command.*;

/**
 * エラーメッセージ送り付けるやつ。
 */
public class ErrorMessageSender
{
    /**
     * メソッドまとめたやつ。
     *
     * @param sender イベントsender。
     * @param perm   チェックする権限。
     * @return 処理が正常に終了したかどうか。
     */
    public static boolean unPermMessage(CommandSender sender, String perm)
    {
        if (sender.hasPermission(perm))
            return false;
        sender.sendMessage(MessageEngine.get("error.notHavePermission"));
        return true;
    }

    /**
     * 長さがやばかった時のメッセージのメソッド。
     *
     * @param sender イベントsender。
     * @param args   length図るやつ。
     * @param min    最小。
     * @param max    最大。
     * @return (エラー発令)処理が正常に終了したかどうか。
     */
    public static boolean invalidLengthMessage(CommandSender sender, String[] args, int min, int max)
    {
        if (args.length < min || args.length > max)
        {
            sender.sendMessage(MessageEngine.get("error.invalidArgument"));
            return true;
        }

        return false;
    }
}
