package ml.peya.plugins.Bukkit.Gui.Items.Target;

import ml.peya.plugins.Bukkit.Gui.Item;
import ml.peya.plugins.Bukkit.Gui.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import static ml.peya.plugins.Bukkit.Utils.MessageEngine.get;
import static ml.peya.plugins.Bukkit.Utils.MessageEngine.pair;

/**
 * BanList
 */
public class BanBook implements IItems
{
    /**
     * イベント発動時の処理をオーバーライドします。
     *
     * @param player 実行しているプレイヤー。
     * @param target ターゲット。
     */
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("bans -a " + target);
    }

    /**
     * アイテムを取得する関数のオーバーライド。どのようなアイテムを返すか、どのような動きをするか、などと言った詳細をこの関数で設定し、アイテムとして返す。
     *
     * @param target ターゲットが誰であるか。
     *
     * @return 関数内の処理によって設定されたアイテム。
     */
    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new ItemStack(Material.PAPER);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(get("item.banBook.title", pair("name", target)));
        meta.setLore(Item.getLore(this, target));
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * インベントリに空きスペースがあるかどうかを確認する関数のオーバーライド。この関数は使わないため実装は不要。
     *
     * @return 実装は不要なためtrue。
     */
    @Override
    public boolean canSpace()
    {
        return true;
    }

    /**
     * どのようなIDであるか取得する。詳細はPSACドキュメントを参照。
     *
     * @return このアイテムの実行ID。
     */
    @Override
    public String getExecName()
    {
        return "BANS";
    }

    /**
     * どのようなタイプであるか取得する。
     *
     * @return 実はTARGETだった。
     */
    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
