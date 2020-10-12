# PeyangSuperbAntiCheat(PSAC) BungeeCord Integration Tutorial

## 概要

PSAC は~~なんとなく~~BungeeCord に対応している！！！！！  
この家庭内廃棄物では PSAC の BungeeCord のつなぎ方を説明しようとがんばっています。  
README を読みたい方は[>>こ ↑ こ ↓<<](README-ja.md) \([English](README-en.md)\)

## メリット

このプラグインを BungeeCord につなげるメリットは、ないようであります。実はあったりするんです。しらんけど。  
まぁ下見ろや...見て頂きたい所存でございますくださいお願い申し上げます。

### 最強メリット一覧

-   レポートをほかのサーバーでもじゃんじゃん受け取れる！
-   BAN を他のサーバーでも共有できる！
-   なんかわからないけどすごい！
-   最強！

    TODO: 順次追加

### つなぎ方

1. すべてのサーバに PeyangSuperbAntiCheat を入れる。
2. BungeeCord にもプラグインを入れる。\(ダウンロードは[>>こちら<<](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/releases) \)
3. つなげたいサーバ全てのコンフィグをいじくる
4. おしり

### 注意事項

#### コンフィグの設定方法 (Spigot|bukkit|Paper)

`database.HogeHogePath` を編集します。
このパスは、相対パスと絶対パスの双方を利用できます。
もしディレクトリ階層が

```
servers/
├─ database.db
├─ database2.db
├─ server1/
│  ├─ plugins/
│  │  ├─ PSAC.jar
│  │  ├─ PeyangSuperbAntiCheat/
│  │  │  ├─ config.yml
│  ├─ foo
│  ├─ bar
│  ├─ Bukkit.jar
├─ server2/
│  ├─ plugins/
│  │  ├─ PSAC.jar
│  │  ├─ PeyangSuperbAntiCheat/
│  │  │  ├─ config.yml
│  ├─ foo
│  ├─ bar
│  ├─ Bukkit.jar
```

などの場合、設定パスは、plugins から数えて、`../../../database.db`となります。  
また、1 つのサーバの`eyePath`を、`../../../database.db`とした場合、
PSAC を連携させるすべてのサーバの eyePath を`../../../database.db`にしてください。

#### コンフィグの設定方法(BungeeCord|Waterfall)

はい。
上と一緒です。

##### Mysql を使いたいって?かんたんさ！

[SQL.md](https://github.com/P2P-Develop/PeyangSuperbAntiCheat/tree/develop/docs/SQL-ja.md) をミて後はいっしょ！かんたん！

##### は?SQLite を使いたいって?

さぁ、[このプラグイン](https://www.spigotmc.org/resources/sqlite-for-bungeecord.57191/update?update=344657)を入れよう！
あとはいっしょ！

## え?わからない?じゃぁ最強の[SQL.md](https://github.com/P2P-Develop/PeyangSuperbAntiCheat/tree/develop/docs/SQL-ja.md) をミてみよう！
