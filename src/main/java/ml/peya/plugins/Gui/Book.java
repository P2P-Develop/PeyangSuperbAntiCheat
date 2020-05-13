package develop.p2p.plugin.Gui;

import develop.p2p.plugin.*;
import develop.p2p.plugin.Utils.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import java.lang.reflect.*;

/**
 * @author https://www.spigotmc.org/members/finnbon.37739/
 * https://www.spigotmc.org/threads/open-book-gui.131470/
 */
public class Book
{
    private static Method handle;
    private static Method open;
    private static boolean init;
    public static void init()
    {
        try
        {
            handle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getBundle");
            open = ReflectionUtils.getMethod("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER,
                    "a",
                    ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack"),
                    ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand"));
            init = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PeyangSuperbAntiCheat.logger.warning("Failed to Open book.");
            init = false;
        }
    }

    public static boolean openBook(ItemStack book, Player player)
    {
        if (!init)
            return false;
        ItemStack backUp = player.getInventory().getItemInMainHand();
        try
        {
            player.getInventory().setItemInMainHand(book);
            sendPackets(book, player);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            PeyangSuperbAntiCheat.logger.warning("Failed to Open book.");
            init = false;
        }

        player.getInventory().setItemInMainHand(backUp);
        return init;
    }

    private static void sendPackets(ItemStack item, Player player) throws ReflectiveOperationException
    {
        Object entityPlayer = handle.invoke(player);
        Class<?> enumHand = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand");
        Object[] enumArray = enumHand.getEnumConstants();
        open.invoke(entityPlayer, getItemStack(item), enumArray[0]);
    }

    private static Object getItemStack(ItemStack item) {
        try
        {
            Method asNMSCopy = ReflectionUtils.getMethod(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), "asNMSCopy", ItemStack.class);
            return asNMSCopy.invoke(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), item);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
