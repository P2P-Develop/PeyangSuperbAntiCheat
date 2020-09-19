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
TODO: 順次追加

### つなぎ方
+ 0. すべてのサーバにPeyangSuperbAntiCheatを入れる。
+ 2. BungeeCordにもプラグインを入れる。\(ダウンロードは[>>こちら<<](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/releases) \)
+ 3. つなげたいサーバ全てのコンフィグをいじくる
+ 4. おわり


### 注意事項
#### コンフィグの設定方法
`database.HogeHogePath` を編集します。  
このパスは、**相対パスのみ**対応しており、絶対パスは利用することができません。が、心優しい英語翻訳担当の人が正規表現による分類を検討しています。  
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
などの場合、設定パスは、pluginsから数えて、`../../databaseX.db`となります。Xは連番で決まります。
\(Mysqlは現在対応作業中です。~~多分無理だと思う~~\)
