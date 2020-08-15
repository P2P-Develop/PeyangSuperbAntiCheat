package ml.peya.plugins.BungeeStructure;

import java.lang.reflect.*;
import java.util.*;

public class CommandManager
{
    private final ArrayList<Class<? extends CommandExecutor>> commands;

    public CommandManager()
    {
        commands = new ArrayList<>();
    }

    public <T extends CommandExecutor> void registerCommand(T t)
    {
        this.commands.add(t.getClass());
    }

    public void runCommand(String command)
    {
        runCommand(command, "");
    }

    public void runCommand(String command, String server)
    {
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(command.split(" ")));
        if (commands.size() == 0)
            return;
        String label = commands.remove(0);
        String[] args = commands.toArray(new String[0]);
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
                return args;
            }

            @Override
            public String getServer()
            {
                return server;
            }
        };

        for (Class<? extends CommandExecutor> cls : this.commands)
        {
            try
            {
                for (Method method : cls.getMethods())
                {
                    if (method.getAnnotation(Command.class).label().equals(label))
                        method.invoke(cls, commandComponent);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
