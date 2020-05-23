package ml.peya.plugins.Utils;

import ml.peya.plugins.*;
import ml.peya.plugins.Utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.scheduler.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Create a "Virtual" book gui that doesn't require the user to have a book in their hand.
 * Requires ReflectionUtil class.
 * Built for Minecraft 1.9
 * @author Jed
 *
 */
public class BookUtil {
    private static boolean initialised = false;
    private static Method getHandle;
    private static Method openBook;

    static {
        try {
            getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
            openBook = ReflectionUtils.getMethod("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, "a", ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack"), ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand"));
            initialised = true;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            //Changed by Peyang BEGIN---
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            //End of change
            Bukkit.getServer().getLogger().warning("Cannot force open book!");
            initialised = false;
        }
    }
    public static boolean isInitialised(){
        return initialised;
    }
    /**
     * Open a "Virtual" Book ItemStack.
     * @param i Book ItemStack.
     * @param p Player that will open the book.
     * @return
     */
    public static boolean openBook(ItemStack i, Player p) {
        if (!initialised) return false;
        ItemStack held = p.getInventory().getItemInMainHand();
        //Changed Begin. by Peyang
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
                    ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
                }
            }
        };
        runnable.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 10L);


        BukkitRunnable runnable2 = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                p.getInventory().setItemInMainHand(held);
            }
        };
        runnable2.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), 20L);
        //Changed end
        return initialised;
    }

    private static void sendPacket(ItemStack i, Player p) throws ReflectiveOperationException {
        Object entityplayer = getHandle.invoke(p);
        Class<?> enumHand = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumHand");
        Object[] enumArray = enumHand.getEnumConstants();
        openBook.invoke(entityplayer, getItemStack(i), enumArray[0]);
    }

    public static Object getItemStack(ItemStack item) {
        try {
            Method asNMSCopy = ReflectionUtils.getMethod(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), "asNMSCopy", ItemStack.class);
            return asNMSCopy.invoke(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"), item);
        } catch (Exception e) {
            e.printStackTrace();
            //Changed by peyang BEGIN---
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            //END of Changed
        }
        return null;
    }

    /**
     * Set the pages of the book in JSON format.
     * @param metadata BookMeta of the Book ItemStack.
     * @param pages Each page to be added to the book.
     */
    @SuppressWarnings("unchecked")
    public static void setPages(BookMeta metadata, List<String> pages) {
        List<Object> p;
        Object page;
        try {
            p = (List<Object>) ReflectionUtils.getField(ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"), true, "pages").get(metadata);
            for (String text : pages) {
                page = ReflectionUtils.invokeMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer").newInstance(), "a", text);
                p.add(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Changed by peyang BEGIN---
            ReportUtils.errorNotification(ReportUtils.getStackTrace(e));
            //END of Changed
        }
    }
}