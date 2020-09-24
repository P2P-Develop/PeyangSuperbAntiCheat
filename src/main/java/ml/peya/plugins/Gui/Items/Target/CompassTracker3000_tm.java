// Compass Tracker 3000™
//
// © 2020 Peyang.
//
// This source code licensed by GNU General Public License 3.0.

package ml.peya.plugins.Gui.Items.Target;

import ml.peya.plugins.Gui.IItems;
import ml.peya.plugins.Gui.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static ml.peya.plugins.Utils.MessageEngine.get;

/**
 * CompassTracker 3000™ ソース ファイル
 * 本製品をコンパイルした一般向けの本製品のダウンロードはリリースからどうぞ。
 * ※本製品は不定期にサポートを行っております。お客様のご利用により何らかの不具合が生じた場合はGitHub Issueにてお申し付けください。
 * 本製品はMITライセンスに準拠します(だったら製品じゃないよね)。
 */
public class CompassTracker3000_tm implements IItems
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
        player.performCommand("tpto " + target);
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
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(get("item.compass"));
        meta.setLore(Item.getLore(this, target));
        item.setItemMeta(meta);
        return item;
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
        return "TRACKER";
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
