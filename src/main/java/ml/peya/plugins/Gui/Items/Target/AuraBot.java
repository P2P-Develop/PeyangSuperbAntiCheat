package ml.peya.plugins.Gui.Items.Target;

import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import ml.peya.plugins.Gui.Item;
import ml.peya.plugins.Gui.*;
import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.lang.reflect.*;
import java.util.*;

public class AuraBot implements IItems
{

    @Override
    public void run(Player player, String target)
    {
        player.performCommand("aurabot " + target);
    }

    @Override
    public ItemStack getItem(String target)
    {

        ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(MessageEngine.get("item.execute", MessageEngine.hsh("command", "AuraBot")));

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
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
        }

        stack.setItemMeta(meta);
        return stack;
    }

    @Override
    public boolean canSpace()
    {
        return false;
    }

    @Override
    public String getExecName()
    {
        return "AURA_BOT";
    }

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

    @Override
    public Type getType()
    {
        return Type.TARGET;
    }
}
