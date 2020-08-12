package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

/**
 * Pull
 */
public class Lead implements IItems
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
        player.performCommand("pull " + target);
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
        ItemStack stack = new ItemStack(Material.LEASH);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(MessageEngine.get("item.lead"));
        meta.setLore(Item.getLore(this, target));
        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * インベントリに空きスペースがあるかどうかを確認する関数のオーバーライド。この関数は使わないため実装は不要。
     *
     * @return 実装は不要なためfalse。
     */
    @Override
    public boolean canSpace()
    {
        return false;
    }

    /**
     * どのようなIDであるか取得する。詳細はPSACドキュメントを参照。
     *
     * @return このアイテムの実行ID。
     */
    @Override
    public String getExecName()
    {
        return "PULL";
    }

    /**
     * どのようなタイプであるか取得する。
     *
     * @return ほんとはTARGET_2だった。二ページ目なだけでTARGETとは変わらない。
     */
    @Override
    public Type getType()
    {
        return Type.TARGET_2;
    }
}
