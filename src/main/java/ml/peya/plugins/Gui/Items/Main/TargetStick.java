package ml.peya.plugins.Gui.Items.Main;

import ml.peya.plugins.Gui.IItems;
import ml.peya.plugins.Gui.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static ml.peya.plugins.Utils.MessageEngine.get;
import static ml.peya.plugins.Utils.PlayerUtils.getLookingEntity;

/**
 * ターゲットを再設定するユーティリティアイテム(ブレイズロッド)を管理します。
 */
public class TargetStick implements IItems
{
    /**
     * イベント発動時の処理をオーバーライドします。
     *
     * @param player メンチを切る側のプレイヤー。
     * @param target オーバーライドのために必要だと思われる。実際は必要ない。
     */
    @Override
    public void run(Player player, String target)
    {
        Player lookingPlayer = getLookingEntity(player);
        if (lookingPlayer == null)
        {
            player.sendMessage(get("error.notPlayerFoundInRange"));
            return;
        }
        player.performCommand("target " + lookingPlayer.getName());
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
        ItemStack stack = new ItemStack(Material.BLAZE_ROD);

        ItemMeta meta = stack.getItemMeta();

        meta.setLore(Item.getLore(this, target));

        meta.setDisplayName(get("item.targetStick"));

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
        return "TARGET_STICK";
    }

    /**
     * どのようなタイプであるか取得する。
     *
     * @return ほぼMAIN。大体MAIN。
     */
    @Override
    public Type getType()
    {
        return Type.MAIN;
    }

}
