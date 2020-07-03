package ml.peya.plugins.Gui;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;

public interface IItems
{
    void run(Player player, String target);

    ItemStack getItem(String target);

    boolean canSpace();

    String getExecName();

    Type getType();

    enum Type
    {
        MAIN("MAIN"),
        TARGET("TARGET");
        String name;
        Type(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public String getRaw()
        {
            return name.toLowerCase();
        }

        public static Type toType(String type)
        {
            return Type.valueOf(type.toUpperCase());
        }
    }
}
