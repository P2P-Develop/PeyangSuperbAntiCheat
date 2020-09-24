package ml.peya.plugins.Gui.Items.Target.Page2;

import ml.peya.plugins.Gui.IItems;
import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.Items.Target.BackButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * ページ1に戻るやつ
 */
public class BackToPage1 implements IItems
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
        player.performCommand("target " + target + " 1");
    }

    /**
     * アイテムを取得する関数のオーバーライド。どのようなアイテムを返すか、どのような動きをするか、などと言った詳細をこの関数で設定し、アイテムとして返す。
     *
     * @param target ターゲットが誰であるか。
     * @return 関数内の処理によって設定されたアイテム。
     */
    @Override
    public ItemStack getItem(String target)
    {
        ItemStack stack = new BackButton().getItem(target);
        stack.setType(Material.ARROW);
        ItemMeta meta = stack.getItemMeta();
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
        return "BACK_TARGET_1";
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
