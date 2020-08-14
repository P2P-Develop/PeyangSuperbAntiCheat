package ml.peya.plugins.Bukkit.Gui.Items.Target;

import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import ml.peya.plugins.Bukkit.Gui.Item;
import ml.peya.plugins.Bukkit.Gui.*;
import ml.peya.plugins.Bukkit.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * 回るBot
 */
public class AuraBotItem implements IItems
{
    /**
     * このクラスのフィールドを取得します。
     *
     * @param target    this.
     * @param name      名前。
     * @param fieldType フィールドの種類。
     * @param index     インデックス。
     * @param <T>       どんな型も入るよ！
     * @return 取得できたフィールド。
     */
    private static <T> Field getField(Class<?> target, String name, Class<T> fieldType, int index)
    {
        for (final Field field : target.getDeclaredFields())
        {
            if ((name == null || field.getName().equals(name)) &&
                    fieldType.isAssignableFrom(field.getType()) &&
                    index-- <= 0)
            {
                field.setAccessible(true);
                return field;
            }
        }

        if (target.getSuperclass() != null)
            return getField(target.getSuperclass(), name, fieldType, index);
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }

    /**
     * イベント発動時の処理をオーバーライドします。
     *
     * @param player 実行しているプレイヤー。
     * @param target ターゲット。
     */
    @Override
    public void run(Player player, String target)
    {
        player.performCommand("aurabot " + target);
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

        ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(MessageEngine.get("item.execute", MessageEngine.pair("command", "AuraBot")));

        GameProfile profile = new GameProfile(UUID.randomUUID(), "AURA_BOT");

        String __DOG_SKIN = "ewogICJ0aW1lc3RhbXAiIDogMTU5MzUwOTE0MTgxNiwKICAicHJvZmlsZUlkIiA6ICI0YTc5MmE2YTVkYTI0ODUyODM4N2JkY2YyNjdjNjhkYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJBY3R1YWxseUp1c3RhZG9nIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2UwMzQ2YTQ4YjI4YjEzYWU2ZjkyZWQxOGI3MDRhZmFkY2IzMGIzMGJkZTFjYTk4Yzk4OGM1OTE1MjJiZTYyMzgiCiAgICB9CiAgfQp9";
        String __SIGNATURE = "fuFok5TwMcvFBKhU+SeIgVUSugsAhqw/4kTF4ErZ5I/726goNMte6fDJmwy1rKSHsvRxtlZboNpJD96pRUOOLnVFXqaVkxEDJoH0VHvtTTcabw3EPMgC0HbjYxiC2X07Kwjrt/FlA1xezX2G6pd5/cvOnDwFBxDz0Na7adraJOclMlSaC/Yuo80IetIJUPjXkwUqeOFzQsZmokI8Bi4c34SbTzB93o2xVhv4Vft4A9oFxc1nik3uDFBqT59Slh9+vWdjpsF2gXLUkegpHJnZIaX/sG8u1MXurLlpvkwbm0/8u9PC6iAIOMuNReT+y0+nhulKEMp9rY0H36FIgqotJyCe3FboWwQfdrQh4UKwsSTUn46KyOFSmN2Ig6a1NyXSuHcFQQ+o3CRiByZwVuzphChvp/2rD3Dx1LsDw5zGgo72+KUQ9UZji+OswRfrLFhOKbzctwadFGp77JabmKMPAcGB2gv7bIeT0/NnzMLoGPC0VAxJAvF4nk3z/ywTAr0MtsZ6X5BDPHC0AWQIpz575GhGrjw/QdjIjncd+Ye1a993faSIsCvTam4amzORj7Vx705sqaepzweQXsDrY3S0EBAGaBe0nkItA+aTDCNGmKEyijZIYIaw05DoJKnrVeQUclPmtLHzBMnqfkY42vecF9W2pkDuBfA3Ev5VzoQ3HB0=";
        profile.getProperties().put("textures", new Property("textures", __DOG_SKIN, __SIGNATURE));

        meta.setLore(Item.getLore(this, target));

        Class<?> head = meta.getClass();

        try
        {
            getField(head, "profile", GameProfile.class, 0).set(meta, profile);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }

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
        return "AURA_BOT";
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
