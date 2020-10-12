package ml.peya.plugins.Utils;

import org.bukkit.ChatColor;

import java.util.stream.IntStream;

/**
 * グラフじぇねれーと
 */
public class OptGraphGenerator
{
    /**
     * 10分のhogeを算出
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
    public static String genGraph(int VL, int max)
    {
        StringBuilder builder = new StringBuilder("[");

        IntStream.range(1, 11)
            .forEachOrdered(i ->
            {
                if (VL >= max && i == 10 || VL == 0 && i == 1)
                    builder.append(ChatColor.WHITE).append("|");

                if (i == calcVLGraph(VL, max))
                    builder.append(ChatColor.WHITE).append("|");
                else if (i < 5)
                    builder.append(ChatColor.GREEN).append("=");
                else if (i < 8)
                    builder.append(ChatColor.YELLOW).append("=");
                else
                    builder.append(ChatColor.RED).append("=");
            });

        return builder.append(ChatColor.WHITE).append("]").toString();
    }
}
