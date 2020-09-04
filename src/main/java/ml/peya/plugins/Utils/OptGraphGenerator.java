package ml.peya.plugins.Utils;

import org.bukkit.*;

import java.util.stream.*;

/**
 * グラフじぇねれーと
 */
public class OptGraphGenerator
{
    /**
     * p2pなんちゃらと同じなんですが！？！？！？！？
     *
     * @param VL  VL。
     * @param max 最大。
     * @return グラフのやつ。
     */
    private static int calcVLGraph(int VL, int max)
    {
        return Math.toIntExact(Math.round(10.0 / (double) max * (double) VL));
    }

    /**
     * 生成するみたい。
     *
     * @param VL  VL。
     * @param max 最大。
     * @return 生成するらしい。
     */
    static String genGraph(int VL, int max)
    {
        int genVL = calcVLGraph(VL, max);

        StringBuilder builder = new StringBuilder("[");

        IntStream.range(1, 11).parallel().forEachOrdered(i -> {
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
