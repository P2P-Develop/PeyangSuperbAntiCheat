# PeyangSuperbAntiCheat(PSAC) BungeeCord Integration Tutorial

## 概要

PSACは~~なんとなく~~BungeeCordに対応している！！！！！  
この家庭内廃棄物ではPSACのBungeeCordのつなぎ方を説明しようとがんばっています。   
READMEを読みたい方は[>>こ↑こ↓<<](README-ja.md) \([English](README-en.md)\)

## メリット
このプラグインをBungeeCordにつなげるメリットは、ないようであります。実はあったりするんです。しらんけど。  
まぁ下見ろや...見て頂きたい所存でございますくださいお願い申し上げます。

### 最強メリット一覧
+ レポートをほかのサーバーでもじゃんじゃん受け取れる！  
+ BANを他のサーバーでも共有できる！
+ なんかわからないけどすごい！
+ 最強！
TODO: 順次追加

### つなぎ方
1. すべてのサーバにPeyangSuperbAntiCheatを入れる。
2. BungeeCordにもプラグインを入れる。\(ダウンロードは[>>こちら<<](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/releases) \)
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
などの場合、設定パスは、pluginsから数えて、`../../../database.db`となります。  
また、1つのサーバの`eyePath`を、`../../../database.db`とした場合、  
PSACを連携させるすべてのサーバのeyePathを`../../../database.db`にしてください。
~~\(Mysqlは現在対応作業中です。~~多分無理だと思う~~\)~~
MySQL対応しました。

#### コンフィグの設定方法(BungeeCord|Waterfall)
はい。  
上と一緒です。  

##### Mysqlを使いたいって?かんたんさ！  
[SQL.md](https://github.com/P2P-Develop/PeyangSuperbAntiCheat/tree/develop/docs/SQL-ja.md) をミて後はいっしょ！かんたん！
##### は?SQLiteを使いたいって?
さぁ、[このプラグイン](https://www.spigotmc.org/resources/sqlite-for-bungeecord.57191/update?update=344657)を入れよう！    
あとはいっしょ！

## え?わからない?じゃぁ最強の[SQL.md](https://github.com/P2P-Develop/PeyangSuperbAntiCheat/tree/develop/docs/SQL-ja.md) をミてみよう！