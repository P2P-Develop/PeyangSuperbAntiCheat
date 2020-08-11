package ml.peya.plugins.Utils;

/**
 * AとBで提供される二パラメータ タプルを提供します。
 */
@Data(staticConstructor = "of")
public class Pair<A, B>
{
  /**
   * タプルのうち一つ目の値。Aの型とリンクしています。
   */
  private final A value1;
  /**
   * タプルのうち二つ目の値。Bの型とリンクしています。
   */
  private final B value2;
  
  /**
   * カプセル化されたvalue1を取得します。
   *
   * @returns Aの型とリンクしたvalue1の値。
   */
  public A getValue1()
  {
    return value1;
  }
  
  /**
   * カプセル化されたvalue2を取得します。
   *
   * @returns Bの型とリンクしたvalue2の値。
   */
  public B getValue2()
  {
    return value2;
  }
}
