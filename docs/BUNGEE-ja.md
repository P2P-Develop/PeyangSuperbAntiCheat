# PeyangSuperbAntiCheat(PSAC) BungeeCord Tutorial

## 概要

PSACはBungeeCordに対応している！！！！！  
PSACのBungeeCordのつなぎ方です。  
普通のREADMEを読みたい方は[>>こちら<<](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/README-ja.md) \([English](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/README-en.md)\)

## メリット
このプラグインをBungeeCordにつなげるメリットは、ないようであります。  
下見ろや,,,ください  

### 最強メリット一覧
+ レポートをほかサーバーでも受け取れる！  
TODO: 順次追加

### つなぎ方
+ 0. すべてのサーバにPeyangSuperbAntiCheatを入れる。  
+ 2. BungeeCordにもプラグインを入れる。\(ダウンロードは[>>こちら<<](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/releases) \)
+ 3. つなげたいサーバ全ての設定をいじくる
+ 4. おわり


### 注意事項
#### コンフィグの設定方法
`database.HogeHogePath` を編集します。  
このパスは、**相対パスのみ**対応しており、絶対パスは利用することができません。許せ。
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
などの場合、設定パスは、pluginsから数えて、`../../databaseX.db`となります。
\(Mysqlは現在対応作業中です。\)