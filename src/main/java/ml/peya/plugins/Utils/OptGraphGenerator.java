package ml.peya.plugins.Utils;

import org.bukkit.*;

import java.util.stream.*;

public class OptGraphGenerator
{
    private static int calcVLGraph(int VL, int max)
    {
        double tendNum = 10.0 / (double) max;

        double VlMeta = tendNum * (double) VL;

        return Math.toIntExact(Math.round(VlMeta));
    }

    static String genGraph(int VL, int max)
    {
        int genVL = calcVLGraph(VL, max);

        StringBuilder builder = new StringBuilder("[");

        IntStream.range(1, 11).forEachOrdered(i -> {
            if (VL >= max && i == 10)
                builder.append(ChatColor.WHITE).append("|");
            else if (VL == 0 && i == 1)
            {
                builder.append(ChatColor.WHITE);
                builder.append("|");
            }
            if (i == genVL)
                builder.append(ChatColor.WHITE).append("|");
            else if (i < 5)
                builder.append(ChatColor.GREEN).append("=");
            else if (i < 8)
                builder.append(ChatColor.YELLOW).append("=");
            else
                builder.append(ChatColor.RED).append("=");
        });

        builder.append(ChatColor.WHITE).append("]");
        return builder.toString();
    }
}
