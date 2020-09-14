package ml.peya.plugins.BungeeStructure;

/**
 * コマンドメソッドを呼び出す際に
 * 与えられる引数
 */
public interface CommandComponent
{
    String getLabel();

    String[] getArgs();

    String getServer();
}
