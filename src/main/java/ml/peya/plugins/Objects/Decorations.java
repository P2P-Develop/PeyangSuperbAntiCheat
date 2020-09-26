package ml.peya.plugins.Objects;

import develop.p2p.lib.WaveCreator;
import ml.peya.plugins.PeyangSuperbAntiCheat;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * でこれーしょん☆！
 */
public class Decorations
{
    /**
     * 雷
     *
     * @param player 餌食
     */
    public static void lighting(Player player)
    {
        player.getWorld().strikeLightningEffect(player.getLocation());
    }

    /**
     * Pit想像しろ
     *
     * @param player  餌食
     * @param seconds 秒数
     */
    public static void flame(Player player, int seconds)
    {
        final int[] sec = {0};
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (sec[0] > (seconds))
                    this.cancel();

                player.getWorld().spawnParticle(
                        Particle.FLAME,
                        player.getLocation().add(0, 0.5, 0), 30, 0, 0, 0, 0.65
                );

                sec[0] += 5;
            }
        }.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0L, 5L);
    }

    /**
     * パーティクルを出すよ！
     *
     * @param location だすとこ
     * @param size     だすりょー
     */
    public static void particle(Location location, int size)
    {
        particle(location, size, Particle.CRIT_MAGIC);
    }

    /**
     * パーティクルを出すよ！
     *
     * @param location だすとこ
     * @param size     だすりょー
     * @param particle 種類
     */
    public static void particle(Location location, int size, Particle particle)
    {
        location.getWorld().spawnParticle(particle, location, size, 0, 0, 0, 0.001); //XXX: 誰だよこんな引数多く設計したやつ
    }

    /**
     * パーティクルを出すよ！
     *
     * @param location だすとこ
     */
    public static void particle(Location location)
    {
        particle(location, 5);
    }

    /**
     * 線を引くよ！
     *
     * @param path 開始位置
     * @param to   終了位置
     */
    public static void line(Location path, Location to)
    {
        line(path, to, Particle.ENCHANTMENT_TABLE);
    }

    /**
     * 可変長引数でいっこの関数で線を引くよ！
     *
     * @param lines 引数をタプルとして入れれるよ！
     */
    @SafeVarargs
    public static void line(final ImmutablePair<Location, Location>... lines)
    {
        Arrays.stream(lines)
                .forEachOrdered(line -> line(line.getLeft(), line.getRight()));
    }

    /**
     * 線を引くよ！
     *
     * @param path 開始位置
     * @param to   終了位置
     * @param p    パーティクル
     */
    public static void line(Location path, Location to, Particle p)
    {
        Vector vP = path.toVector();
        for (double d = 0; path.distance(to) > d; d += 0.2)
        {
            vP.add(to.toVector()
                    .clone()
                    .subtract(vP)
                    .normalize()
                    .multiply(0.2));
            particle(vP.toLocation(path.getWorld()), 1, p);
        }
    }

    /**
     * えん
     *
     * @param center 真ん中の位置
     * @param count  カウント！
     * @param radius はんけー
     */
    public static void circle(Location center, int count, double radius)
    {
        particle(new Location(
                center.getWorld(),
                particle_x(count, radius) + center.getX(),
                center.getY(),
                particle_z(count, radius) + center.getZ()
        ));
    }

    /**
     * 可変長引数でいっこの関数で実行できるえん
     *
     * @param circles 引数をタプルとして入れれるよ！
     */
    @SafeVarargs
    public static void circle(final Triple<Location, Integer, Double>... circles)
    {
        Arrays.stream(circles)
                .forEachOrdered(circle -> circle(circle.getLeft(), circle.getMiddle(), circle.getRight()));
    }

    /**
     * えん
     *
     * @param center   真ん中の位置
     * @param count    カウント！
     * @param radius   はんけー
     * @param particle ぱーてぃくる
     */
    public static void circle(Location center, int count, double radius, Particle particle)
    {
        particle(new Location(
                center.getWorld(),
                particle_x(count, radius) + center.getX(),
                center.getY(),
                particle_z(count, radius) + center.getZ()
        ), 5, particle);
    }

    /**
     * まほーじん！！
     *
     * @param player  餌食
     * @param seconds 秒数
     */
    public static void magic(Player player, int seconds)
    {
        final int[] count = {0};
        WaveCreator wave = new WaveCreator(0.8, 1.8, 0.1);
        final Location center = player.getLocation();

        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (double i = 0; i < Math.PI * 2; i++)
                {
                    circle(center.clone()
                            .add(0, 0.9, 0), count[0], 3, Particle.CRIT);

                    circle(center.add(0, 0.7, 0), count[0], 2.7, Particle.ENCHANTMENT_TABLE);

                    circle(
                            Triple.of(center.clone().add(0, wave.get(0.01, false), 0), count[0], wave.getStatic()),
                            Triple.of(center.clone().add(0, wave.get(0.01, false), 0), count[0], wave.getStatic()),
                            Triple.of(center.clone().add(3.2, 0.7, 3.2), count[0], 1.5),
                            Triple.of(center.clone().add(-3.2, 0.7, -3.2), count[0], 1.5),
                            Triple.of(center.clone().add(-3.2, 0.7, 3.2), count[0], 1.5),
                            Triple.of(center.clone().add(3.2, 0.7, -3.2), count[0], 1.5)
                    );

                    circle(center.clone()
                            .add(0, 1.5, 0), count[0], 5, Particle.SPELL_WITCH);

                    count[0]++;
                }

                line(
                        ImmutablePair.of(center.clone().add(3, 0.7, 0), center.clone().add(-1.5, 0.7, 2.3)),
                        ImmutablePair.of(center.clone().add(-1.5, 0.7, 2.3), center.clone().add(-1.5, 0.7, -2.3)), // 三角
                        ImmutablePair.of(center.clone().add(3, 0.7, 0), center.clone().add(-1.5, 0.7, -2.3)),
                        ImmutablePair.of(center.clone().add(-3, 0.7, 0), center.clone().add(1.5, 0.7, -2.3)),
                        ImmutablePair.of(center.clone().add(1.5, 0.7, -2.3), center.clone().add(1.5, 0.7, 2.3)), // 三角 (反転)
                        ImmutablePair.of(center.clone().add(-3, 0.7, 0), center.clone().add(1.5, 0.7, 2.3))
                );
            }
        };

        runnable.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0L, 1L);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                runnable.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), seconds);

    }

    /**
     * ガーディアンビーム
     *
     * @param player 餌食
     * @param sec    秒数
     */
    public static void laser(Player player, int sec)
    {

        final double[] time = {0.0};

        final double radius = 2.5;

        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                final Location c = player.getLocation()
                        .clone();

                IntStream.range(0, 10)
                        .parallel()
                        .forEachOrdered(i -> line(c, new Location(c.getWorld(), particle_x(time[0], radius) + c
                                .getX(), 5.0 + c.getY(), particle_z(time[0], radius) + c.getZ()), Particle.TOWN_AURA));
                time[0] += Math.E;
            }
        };

        runnable.runTaskTimer(PeyangSuperbAntiCheat.getPlugin(), 0L, 1L);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                runnable.cancel();
            }
        }.runTaskLater(PeyangSuperbAntiCheat.getPlugin(), sec);
    }

    /**
     * パーティクルのZ。
     *
     * @param time   時間。
     * @param radius 半径。
     * @return 位置。
     */
    private static double particle_z(double time, double radius)
    {
        return Math.sin(time) * radius * Math.cos(Math.PI / 180 * 360.0);
    }

    /**
     * パーティクルのX
     *
     * @param time   時間。
     * @param radius 半径。
     * @return 位置。
     */
    private static double particle_x(double time, double radius)
    {
        return Math.cos(time) * radius;
    }
}
