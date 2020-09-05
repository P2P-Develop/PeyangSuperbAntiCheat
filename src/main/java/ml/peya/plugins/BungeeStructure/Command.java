package ml.peya.plugins.BungeeStructure;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command
{
    String label();
}
