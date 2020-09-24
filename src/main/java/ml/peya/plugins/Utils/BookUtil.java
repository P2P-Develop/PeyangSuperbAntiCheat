package ml.peya.plugins.Utils;

import ml.peya.plugins.PeyangSuperbAntiCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Create a "Virtual" book gui that doesn't require the user to have a book in their hand.
 * Requires ReflectionUtil class.
 * Built for Minecraft 1.9
 *
 * @author Jed
 */
public class BookUtil
{
    private static boolean initialised;
    private static Method getHandle;
    private static Method openBook;

    static
    {
        try
        {
            getHandle = ReflectionUtils
                    .getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
            openBook = ReflectionUtils
                    .getMethod("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, "a", ReflectionUtils.PackageType.MINECRAFT_SERVER
                            .getClass("ItemStack"), ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand"));
            initialised = true;
        }
        catch (ReflectiveOperationException e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
            Bukkit.getServer().getLogger().warning("Cannot force open book!");
            initialised = false;
        }
    }

    public static boolean isInitialised()
    {
        return initialised;
    }

    /**
     * Open a "Virtual" Book ItemStack.
     *
     * @param i Book ItemStack.
     * @param p Player that will open the book.
     */
    public static void openBook(ItemStack i, Player p)
    {
        if (!initialised) return;
        ItemStack held = p.getInventory().getItemInMainHand(); //アイテムバックアップ
        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                p.getInventory().setItemInMainHand(i);
                try
                {
                    sendPacket(i, p);
                }
                catch (ReflectiveOperationException e)
                {
                    e.printStackTrace();
                    Utils.errorNotification(Utils.getStackTrace(e));
                }
                this.cancel();
            }
        };
        runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10L);

        BukkitRunnable runnable2 = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                p.getInventory().setItemInMainHand(held);
                this.cancel();
            }
        };
        runnable2.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20L);
    }

    private static void sendPacket(ItemStack i, Player p) throws ReflectiveOperationException
    {
        openBook.invoke(getHandle.invoke(p), getItemStack(i), ReflectionUtils.PackageType.MINECRAFT_SERVER
                .getClass("EnumHand").getEnumConstants()[0]);
    }

    public static Object getItemStack(ItemStack item)
    {
        try
        {
            return ReflectionUtils.getMethod(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY
                    .getClass("CraftItemStack"), "asNMSCopy", ItemStack.class)
                    .invoke(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY
                            .getClass("CraftItemStack"), item);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }
        return null;
    }

    /**
     * Set the pages of the book in JSON format.
     *
     * @param metadata BookMeta of the Book ItemStack.
     * @param pages    Each page to be added to the book.
     */
    public static void setPages(BookMeta metadata, List<String> pages)
    {
        try
        {
            for (String text : pages)
            {
                Collections.singletonList(ReflectionUtils.getField(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY
                        .getClass("CraftMetaBook"), true, "pages")
                        .get(metadata))
                        .add(ReflectionUtils.invokeMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER
                                .getClass("IChatBaseComponent$ChatSerializer")
                                .newInstance(), "a", text));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Utils.errorNotification(Utils.getStackTrace(e));
        }
    }
}