package ml.peya.plugins.Utils;

import org.bukkit.*;

public class OptGraphGenerator
{
    private static int calcVLGraph(int VL, int max)
    {
        return Math.toIntExact(Math.round((10.0 / (double) max) * (double) VL));
    }

    static String genGraph(int VL, int max)
    {
        int genVL = calcVLGraph(VL, max);

        StringBuilder builder = new StringBuilder("[");

        for (int i = 1; i < 11; i++)
        {
            if (VL >= max && i == 10)
                builder.append(ChatColor.WHITE).append("|");
            else if (VL == 0 && i == 1)
                builder.append(ChatColor.WHITE).append("|");
            if (i == genVL)
                builder.append(ChatColor.WHITE).append("|");
            else if (i < 5)
                builder.append(ChatColor.GREEN).append("=");
            else if (i < 8)
                builder.append(ChatColor.YELLOW).append("=");
            else
                builder.append(ChatColor.RED).append("=");
        }

        builder.append(ChatColor.WHITE).append("]");
        return builder.toString();
    }
}
