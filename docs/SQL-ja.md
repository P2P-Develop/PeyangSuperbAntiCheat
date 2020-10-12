# PeyangSuperbAntiCheat SQL 設定

PSAC では、SQL 設定を config ファイルに設定します。
この設定は、以下の書式に決定されます。

```yml
#データベースのパス
#相対パスが利用できます。
database:
  #メソッド。sqliteなら org.sqlite.JDBC Mysqlなら com.mysql.jdbc.Driver
  method: "org.sqlite.JDBC"
  url: "jdbc:sqlite"

  path: "./eye.db"
  logPath: "./log.db"
  learnPath: "./learn.json"
  trustPath: "./trust.db"
```

|     設定名      |  デフォルト値   | 説明                 |
| :-------------: | :-------------: | -------------------- |
| database.method | org.sqlite.JDBC | SQL の設定(メソッド) |
|  database.url   |   jdbc:sqlite   | SQL の設定(URL)      |

## 設定パラメーター

### database.method

データベースのドライバー名です。  
クラス名を用いて設定します。

|  名前  |       クラス名        |
| :----: | :-------------------: |
| MySql  | com.mysql.jdbc.Driver |
| SQLite |    org.sqlite.JDBC    |

### database.url

データベースのアレ(?)です。
jdbc:hogehoge の形で設定します。

|  名前  |   クラス名    |
| :----: | :-----------: |
| MySql  | jdbc:sqlite:  |
| SQLite | jdbc:mysql:// |

### なんとか Path

SQLite を設定するときは簡単にできます。

#### SQLite

```yaml
#windows
hogePath: "C:\\Users\\Hoge\\DataBase\\hoge.db"

#Linux(Mac)
hogePath2: "/path/to/data/hoge2.db"

#両方(相対パス)
hogePath3: "../data/hoge2.db"
```

こんな感じにやってください。

#### MySQL

ホスト名:ポート番号/DB 名?user=ユーザ名&password=パスワード

```yaml
#例
hogePath: "localhost:3306/eyeData?user=peyang&password=PeyangIsGod"
```

はい。こんな感じです。かんたん！

# え?わからないって?じゃぁ[Issue](https://github.com/P2P-Develop/PeyangSuperbAntiCheat/issues)を建てよう！
