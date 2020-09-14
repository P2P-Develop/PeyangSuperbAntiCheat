package ml.peya.plugins.BungeeStructure;

import java.lang.annotation.*;

/**
 * コマンド実行メソッドのアノテーション
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Command
{
    String label();
}
