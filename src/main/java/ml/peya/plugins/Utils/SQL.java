package ml.peya.plugins.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SQL
{
    public static void insert(Connection connection, String database, Object... value) throws SQLException
    {
        StringBuilder p = new StringBuilder();

        Arrays.stream(value)
                .parallel()
                .forEach(s -> {
                    if (!p.toString().isEmpty())
                        p.append(", ");
                    p.append("?");
                });

        String sql = "INSERT INTO " + database + " VALUES (" +
                p.toString() +
                ")";

        try(PreparedStatement s = connection.prepareStatement(sql))
        {
            AtomicInteger count = new AtomicInteger();
            Arrays.stream(value)
                    .forEachOrdered(o -> {
                        try
                        {
                            s.setObject(count.incrementAndGet(), o);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                            Utils.errorNotification(Utils.getStackTrace(e));
                        }
                    });
            s.execute();
        }
    }

    public static void delete(Connection connection, String database, HashMap<String, ?> map) throws SQLException
    {
        StringBuilder p = new StringBuilder();

        map.keySet()
                .parallelStream()
                .forEach(s -> {
                    if (!p.toString().isEmpty())
                        p.append(", ");
                    p.append(s).append("=?");
                });

        String sql = "DELETE FROM " + database + " WHERE " + p.toString();

        if (p.toString().isEmpty())
            sql = "DELETE FROM " + database;

        try(PreparedStatement s = connection.prepareStatement(sql))
        {
            AtomicInteger count = new AtomicInteger();
            map.values()
                    .forEach(o -> {
                        try
                        {
                            s.setObject(count.incrementAndGet(), o);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                            Utils.errorNotification(Utils.getStackTrace(e));
                        }
                    });
            s.execute();
        }
    }

    public static void exec(Connection connection, String sql, Object... values) throws SQLException
    {
        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            AtomicReference<SQLException> exception = new AtomicReference<>();
            AtomicInteger integer = new AtomicInteger();

            Arrays.stream(values)
                    .forEachOrdered(o -> {
                        try
                        {
                            statement.setObject(integer.getAndIncrement(), o);
                        }
                        catch (SQLException throwable)
                        {
                            exception.set(throwable);
                        }
                    });

            if (exception.get() != null)
                throw exception.get();

            statement.execute();

        }
    }
}
