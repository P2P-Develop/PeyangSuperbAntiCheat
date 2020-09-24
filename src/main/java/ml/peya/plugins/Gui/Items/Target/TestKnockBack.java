package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.IItems;
import ml.peya.plugins.Gui.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.MessageEngine.pair;

/**
 * TestKB
 */
public class TestKnockBack implements IItems
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
        player.performCommand("testkb " + target);
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
        ItemStack stack = new ItemStack(Material.SLIME_BALL);

        ItemMeta meta = stack.getItemMeta();

        meta.setDisplayName(get("item.execute", pair("command", "TestKnockBack")));

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
        return "TEST_KB";
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
