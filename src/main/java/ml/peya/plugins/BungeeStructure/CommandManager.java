package ml.peya.plugins.BungeeStructure;

import java.lang.reflect.*;
import java.util.*;

/**
 * コマンドを管理するクラス
 */
public class CommandManager
{
    /**
     * コマンド実行クラスリスト
     */
    private final ArrayList<Class<? extends CommandExecutor>> commands;

    /**
     * CommandManagerのコンストラクタ
     */
    public CommandManager()
    {
        commands = new ArrayList<>();
    }

    /**
     * コマンド登録
     *
     * @param t   コマンドクラス
     * @param <T> コマンドクラス ※要CommandExecutorの継承
     */
    public <T extends CommandExecutor> void registerCommand(T t)
    {
        this.commands.add(t.getClass());
    }

    /**
     * コマンド実行
     *
     * @param command コマンド
     */
    public void runCommand(String command)
    {
        runCommand(command, "");
    }

    /**
     * コマンド実行
     *
     * @param command コマンド
     * @param server  サーバネーム
     */
    public void runCommand(String command, String server)
    {
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(command.split(" ")));
        if (commands.size() == 0)
            return;
        String label = commands.remove(0);
        CommandComponent commandComponent = new CommandComponent()
        {
            @Override
            public String getLabel()
            {
                return label;
            }

            @Override
            public String[] getArgs()
            {
                return commands.toArray(new String[0]);
            }

            @Override
            public String getServer()
            {
                return server;
            }
        };

        this.commands.parallelStream().forEachOrdered(cls -> {
            try
            {
                for (Method method : cls.getMethods())
                {
                    if (method.getAnnotation(Command.class) == null)
                        continue;
                    if (method.getAnnotation(Command.class).label().equals(label))
                        method.invoke(cls.newInstance(), commandComponent);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
}
