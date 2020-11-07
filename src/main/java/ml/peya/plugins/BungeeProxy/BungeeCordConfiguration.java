package ml.peya.plugins.BungeeProxy;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeeCordConfiguration
{

    /**
     * ファイル
     */
    private final File file;

    /**
     * こんふぃぐ
     */
    private Configuration configuration;

    /**
     * 固有インスタンス
     *
     * @param file 対象ファイル名。
     */
    public BungeeCordConfiguration(String file)
    {
        this.file = new File(PeyangSuperbAntiCheatProxy.getPlugin().getDataFolder(), file);
        this.configuration = null;
    }

    /**
     * デフォルトをコピー
     *
     * @throws IOException 何らかのエラーが発生した時。
    */
    public void saveDefaultConfig() throws IOException
    {
        if (file.exists())
            return;

        file.getParentFile().mkdirs();

        try (InputStream stream = PeyangSuperbAntiCheatProxy.getPlugin().getResourceAsStream(file.getName()))
        {
            Files.copy(stream, file.toPath());
        }
    }

    /**
     * コンフィグをロード。
     *
     * @throws IOException exception
     */
    public void loadConfig() throws IOException
    {
        if (!file.exists())
            saveDefaultConfig();
        this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    /**
     * コンフィグのリロード
     *
     * @throws IOException exception
     */
    public void reloadConfig() throws IOException
    {
        this.configuration = null;
        loadConfig();
    }

    /**
     * コンフィグのセーブ
     *
     * @throws IOException exception
     */
    public void saveConfig() throws IOException
    {
        if (this.configuration == null) {
            //コンフィグがロードされていないと考えられるため、コンフィグに変更が無いと仮定して何もせず終了する
            return;
        }
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
    }

    public Configuration getConfig() throws IOException
    {
        if (this.configuration == null)
            loadConfig();

        return this.configuration;
    }
}
