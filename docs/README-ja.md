PeyangSuperbAntiCheat
======
Bukkit/Spigot/PaperMCのプラグインです。

1.12.2で動作確認/絶賛開発中です。
このプラグインは、**チートレポート管理** / **チートテスト**プラグインです。

また、ハックと言う読み方ではサーバー自体のハッキング（クラッキング）との意味が曖昧なため、**チート**として扱っています。

ご理解のほど、よろしくお願いします。

↑ごめん＾＾；

### インストール方法
* 適当にダウンロードします。
* [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)[(\*)](https://dev.bukkit.org/projects/protocollib) をpluginsフォルダーに入れます。  
* このプラグインをpluginsフォルダーに入れます。
* 再起動して、適用してください。


### コマンド
* /wdr

Hypixelの**~~パクリ~~**コマンドです。

### エイリアス
* /peyangreport
* /pcr
* /rep
* /report
* /wdr

### 説明
* プレイヤー側

こんなものをつかってるぷれいやーがいるよ！みてみて！」  
というコマンドです。  
あくまでも「みてみて！」と報告するだけで、BANをするコマンドではありません。

* スタッフ側

プレイヤーからのレポートは、2形式で表示されます。  
「\[PeyangSuperbAntiCheat\] プレイヤーがレポートを提出しました！クリックしてレポートを確認してください！ \[CLICK\]」  
または、  
「\[STAFF\] \[ADMIN\] Fishy: Report of \<PlayerName\> \<理由1\>, \[理由2\]...」  
の形で表示されます。コンフィグを編集することにより変更できます。  

このモードは、<details><summary>████</summary>Kam7</details>によって**リークされた**Hypixel Lynx Modとの互換性を持たせる事ができるモードです。  
このModは、HypixelでBANされる可能性があります。  
このModを使用したままHypixelには**絶対に**行かないでください。

### 使用法
* /wdr \<PlayerName\>

この状態で実行すると、報告事由を選択できる本が\(**自動的に**\)開きます。  
この本に表示されている報告事由をクリックすると、報告内容として事由が追加されます。  
- 「<span style="color: dark_green; font-weight: bold;">レポートを送信</span>」をクリックで送信するか、  
- 「<span style="color: red; font-weight: bold;">レポートをキャンセル</span>」をクリックでキャンセルします。  

**注意：このプラグインでは超原始的な本の実装方法を使用しています。ご了承しろやください。**

* /wdr \<PlayerName\> \<理由1\> \[理由2\]…

このコマンドは、本を使用せずに、コマンドのみで報告できます。  
本を開かないため、**全くもって意味を感じられませんが**、コンソールからでも報告できます。  
__このコマンドの事由には、エイリアスを使用できます。下記の表をみろ…*ください*。__

### 報告に使用できる事由一覧
本で表示される順に書きます（たぶん）
| 理由 | エイリアス | 簡単な説明 |
|:-:|:-:|-|
| Fly | flight | プレイヤーが空を飛べるようにできるチートです。 |
| KillAura | killaura, aura, ka | 範囲に入った人をより早く攻撃できるチートです。 |
| AutoClicker | autoclicker, ac, autoclick | 自動で左クリックができるチートです（高橋名人などのツールやマウスなどについているマクロでもAutoClickerに値します） |
| Speed | speed, bhop, timer | ありえない速度で歩けるようになるチートです（BunnyHopやTimerはSpeedに値します） |
| AntiKnockback | akb, velocity, antikb | ノックバックを軽減するチートです。 |
| Reach | reach | 通常より攻撃できる距離が伸びるチートです。 |
| Dolphin | dolphin | イルカのように、水を自動で泳ぐチートです。 |
* 報告をスパムに利用されることを避けるため、同じ人から同じ人への報告はできません。

### 権限
* psr.report

初期では全員が持っている権限です。  
この権限を剥奪されたプレイヤーは報告が出来なくなります。

---

### /aurabot

### エイリアス
* /testaura
* /auratest
* /killauratest

### 説明
このコマンドを実行すると、プレイヤーの周りに、プレイヤーの周囲を一定の速度で回る、NPCを召喚します。  
このNPCに対して、一定の回数以上ヒットした場合、そのプレイヤーをキックします。

### 使用法
* /aurabot \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーに、回転NPCを召喚します。

### 権限
* psr.mod
* psr.admin

---

### /acpanic

### エイリアス
* /testpanic
* /panictest
* /aurapanictest

### 説明
このコマンドを実行すると、プレイヤーの背後にへばりつくようにNPCを指定された秒数召喚します。

### 使用法
* /acpanic \<PlayerName\>

\<PlayerName\> に指定されたプレイヤーの背後にNPCを召喚します。

### 権限
* psr.mod
* psr.admin

---

### /bans

### エイリアス
* /banlist
* /playerbans
* /banlookup

### 説明
**このプラグインに残っている**、プレイヤーのキック（BAN）履歴を表示します。

### 使用法
* /bans \[-a | ban | kick\] \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーのBAN履歴を表示します。  
\-aをつけると、BANとキックをすべて表示します。あとは察してください。

### 権限
* psr.mod
* psr.admin

---

### /psac

### エイリアス
* /peyangsuperbanticheat
* /psr
* /wdadmin
* /anticheat

### 説明
このプラグインのメインコマンドです。引数をつけることによって動作します。

### 引数一覧
* /psac help

このプラグインコマンドのヘルプを表示します。  
```psac.mod```または、```psac.admin```権限を持っている人には、以下のヘルプも表示されます。
 
* /psac view \[ページ数\]

プレイヤーが提出したレポートを確認できます。  
危険度順に5件ずつ表示されます。
  
* /psac show \<管理ID\>

プレイヤーが提出したレポートの詳細を表示します。  
このコマンドをプレイヤーから実行すると、本が開き、報告の詳細を確認できます。  
コンソールから実行した場合、コンソールにそのまま表示されます。
  
* /psac drop \<管理ID\>

プレイヤーが提出した報告を**完全**に破棄します。  
**削除ログは一切記録されません。破棄する場合には十分ご注意ください。**  
*（コマンド操作ログは記録されるかも（たぶん））*

* /psac kick \<PlayerName\> \[test\]

\<PlayerName\>で指定したプレイヤーをキックします。  
第2引数に```test```を指定すると、テストモードとしてキックされます。  

### 権限
* psr.mod
* psr.admin

---

### \<管理ID\>について
\<管理ID\>は、プレイヤーが報告を提出した際に、自動的に割当られる32文字の英数文字列です。  
このIDは、コンソールから ``` /psr view ``` をした際に表示されます。  
プレイヤーからも管理IDに関連したコマンドを実行できますが、**意味は全くもってないでしょう。**

### 注意：管理IDに関するコマンドは```psac.mod```を持つプレイヤーも使用できますが、ヘルプに表示されません。

### キックについて
このプラグインでは誤検出などのためにプレイヤーが誤BANされることを危惧しています。  
そのため、プラグインが自動でプレイヤーをBANすることは**絶対に**ございません（たぶん）

### ブロードキャストメッセージについて
プレイヤーが自動でキックされるとき、以下のブロードキャストメッセージが流れます。  

「\[PEYANG CHEAT DETECTION\] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。」  
「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」  
  
このメッセージは、チートを自動検出した時のメッセージです。  
スタッフによるキックの場合は、「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」だけが流れます。  

### キック理由について
キック事由は3種類に別れています。

### PEYANG CHEAT DETECTION
このプラグインがチートを自動で検知した場合のメッセージです。

### KICKED BY STAFF
スタッフによるキックコマンドが実行された場合のメッセージです。

### PEYANG CHEAT TEST
このプラグインのテストメッセージです。デバッグ用に使ったり、**お遊びでつかってやってくれぇ**

### NPC について
現NPCは、[@randomapi](https://twitter.com/randomapi)によるAPI「[RandomUserGenerator](https://randomuser.me/)」を使用して、  
ランダムなユーザー名をもつプレイヤーを召喚して、動作しています。  
スキンはランダムですが、現段階では、コンフィグに登録されUUIDのスキンを参照して、ランダム表示されます。

### コンフィグについて
このプラグインでは、以下のコンフィグを使用しています。適当に変えてください。
| 設定名 | デフォルト値 | 説明 |
|:-:|:-:|-|
| database.path | ./eye.db | レポート情報などを保存する、SQLiteデータベースの置き場所です。 |
| database.logPath | ./log.db | キック情報などを保存する、SQLiteデータベースの置き場所です。 |
| npc.seconds | 6                                                           | NPCがプレイヤーの周りを回る秒数です。 |
| npc.bump | 30.0                          | NPC が途中で落ちてしまったり、動きが止まってしまった場合などに、適度に増やして、どうぞ。 |
| npc.time | 0.25 | NPC が回る速さです。感覚で調整してください。 |
| npc.range | 2.1 | NPC が回る半径です。ロマンが全て。デフォルトが最高。 |
| npc.panicRange | 1.5 | Panic NPC がプレイヤーの背後をへばりつく相対的な高さでえす。 |
| npc.kill | 3                             | 10秒間にプレイヤーがキルされたときに、プレイヤーがレポートされているとき、この数を超えた場合は、NPCを召喚します。  | 
| kick.delay | 2                   | プレイヤーをキックするまでの遅延です。ブロードキャストメッセージが流れた瞬間から数えられます。 |
| kick.lightning | true | プレイヤーがキックされるときに、（ダメージを受けない）雷を落とすかどうかです。 |
| kick.defaultKick | 40 | NPCがこの値以上殴られた場合にキックします。学習済データが見つからない場合はこの値が優先されます。 |
| message.lynx | true | Lynx Modと互換性を持たせるかどうかです。 |
| skins | \(UUID...\) | NPC に適用するスキンです。この中からランダムで選ばれます。 |

### 人工知能もどきについて
このプラグインでは、開発名「クソ雑魚ナメクジゴミ人工知能」とよばれる、人工知能**もどき**があります。  
実際のチートを用いて学習させることにより、キックの精度が向上する…（**と思って**作ったものです）
~~まぁ平均の平均の平均…をとるだけの単純な機構なんですけどね.~~

↑ハハッ ＾＾；

### 謝辞
このプラグインは、以下のライブラリ / API を使用しています。  
[RandomApi/RandomUserGenerator](https://randomuser.me/)  
[brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)  
[dmulloy2/ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) [(\*Bukkit)](https://dev.bukkit.org/projects/protocollib)  
[jedk1/BookUtil.java](https://www.spigotmc.org/threads/resource-bookutil-1-8-1-9.131549/)  
[DarkBlade12/ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)  
[P2P-Develop/PeyangSuperLibrary](https://github.com/P2P-Develop/PeyangSuperLibrary)  