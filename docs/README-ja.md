PeyangSuperbAntiCheat
======
Bukkit/Spigot/PaperMCのプラグインです。

1.12.2で動作確認/絶賛開発中です。<br>
このプラグインは、**チートレポート管理** / **チートテスト**プラグインです。<br>
また、ハックと言う読み方ではサーバー自体のハッキング（クラッキング）との意味が曖昧なため、**チート**として扱っています。<br>

ご理解のほど、よろしくお願いします。

↑ごめん＾＾；


### 注意
__~~このプラグインはほぼネタプラグインd（殴~~__ **[削除済み]** **[削除済み]** **[削除済み]**

このプラグインでは、常時2個Detectopmで+4個タイマーが動いています（2015/10/21 16:29現在）<br>~~某有名系Googleブラウザー同様~~、メモリー、CPUともに大食いです。<br>余裕のあるサーバーで実行することを推奨します。

このプラグインでは超原始的な本の実装方法を使用しています。ご了承しろやください。

管理IDに関するコマンドは`psac.mod`を持つプレイヤーも使用できますが、ヘルプに表示されません。

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

こんなものをつかってるぷれいやーがいるよ！みてみて！」<br>
というコマンドです。<br>
あくまでも「みてみて！」と報告するだけで、BANをするコマンドではありません。

* スタッフ側

プレイヤーからのレポートは、2形式で表示されます。<br>
「\[PeyangSuperbAntiCheat\] プレイヤーがレポートを提出しました！クリックしてレポートを確認してください！ \[CLICK\]」<br>
または、<br>
「\[STAFF\] \[ADMIN\] Fishy: Report of \<PlayerName\> \<理由1\>, \[理由2\]...」<br>
の形で表示されます。コンフィグを編集することにより変更できます。

このモードは、<details><summary>████</summary>Kam7</details>によって**リークされた**Hypixel Lynx Modとの互換性を持たせる事ができるモードです。<br>
このModは、HypixelでBANされる可能性があります。<br>
このModを適用したままHypixelには**絶対に**行かないでください。

### 使用法
* /wdr \<PlayerName\>

この状態で実行すると、報告事由を選択できる本が開きます。<br>
この本に表示されている報告事由をクリックすると、報告内容として事由が追加されます。

![#008000](https://via.placeholder.com/15/008000/000000?text=+)**レポートを送信**をクリックで送信するか、<br>
![#008000](https://via.placeholder.com/15/ff0000/000000?text=+)**レポートを放棄**クリックで破棄します。 

* /wdr \<PlayerName\> \<理由1\> \[理由2\]…

このコマンドは、本を使用せずに、コマンドのみで報告できます。<br>
本を開かないため、**全くもって意味を感じられませんが**、コンソールからでも報告できます。<br>
__このコマンドの事由には、エイリアスを使用できます。下記の表をみろ…*ください*。__

### 報告に使用できる事由一覧
本で表示される順に書きます（たぶん）
| 理由 | エイリアス | 簡単な説明 |
|:-:|:-:|-|
| Fly | flight | プレイヤーがクリエイティブモード以外でも空を飛べるようにできるチートです。 |
| KillAura | killaura, aura, ka | 範囲に入った人をより早く攻撃できるチートです。 |
| AutoClicker | autoclicker, ac, autoclick | 自動で左クリックができるチートです（高橋名人などのツールやマウスなどについているマクロでもAutoClickerに値します） |
| Speed | speed, bhop, timer | ありえない速度で歩けるようになるチートです（BunnyHopやTimerはSpeedに値します） |
| AntiKnockback | akb, velocity, antikb | ノックバックを軽減するチートです。 |
| Reach | reach | 通常より攻撃できる距離が伸びるチートです。 |
| Dolphin | dolphin | イルカのように、水を自動で泳ぐチートです。 |
* 報告をスパムに利用されることを避けるため、同じ人から同じ人への報告はできません。

### 権限
* psac.report

初期では全員が持っている権限です。<br>
この権限を剥奪されたプレイヤーは報告ができなくなります。

---

### /aurabot

### エイリアス
* /testaura
* /auratest
* /killauratest

### 説明
このコマンドを実行すると、プレイヤーの周りに、プレイヤーの周囲を一定の速度で回る、NPCを召喚します。<br>
このNPCに対して、一定の回数以上ヒットした場合、そのプレイヤーをキックします。

### 使用法
* /aurabot \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーに、回転NPCを召喚します。

### 権限
* psac.aurabot

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
* psac.aurapanic

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

\<PlayerName\>に指定されたプレイヤーのBAN履歴を表示します。<br>
\-aをつけると、BANとキックをすべて表示します。あとは察してください。

### 権限
* psac.bans

---

### /testkb

### エイリアス
* /testknockback
* /kbtest
* /knockbacktest

### 説明
指定されたプレイヤーに、**見えない矢**をぶち込みます。<br>
そのノックバックでAntiKBか どうか判定して、どうぞ。

### 使用法
* /testkb \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーに矢をぶち込みます。

### 権限
* psac.testkb

---

### /pull
指定したプレイヤーを引き寄せます。

### エイリアス
* /s

### 説明
指定したプレイヤーを実行者に引き寄せます。<br>
コンソールからは実行できません。

### 使用法
* /pull \<PlayerName\>

### 権限
* psac.pull

---

### /target
指定したプレイヤーをターゲットとして追跡します。

### 説明
このコマンドを実行すると、いくつかのアイテムが手に入ります。<br>
このアイテムをクリックすることにより、プレイヤーにNPCを出したり、<br>
まぁ、色々できるマクロのようなもんだ。<br>
ドロップすると全部消えるよ！

### 使用法
* /target \<PlayerName\>

### 権限
* psac.target

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

このプラグインコマンドのヘルプを表示します。<br>
`psac.mod`または、`psac.admin`権限を持っている人には、以下のヘルプも表示されます。
 
* /psac view \[ページ数\]

プレイヤーが提出したレポートを確認できます。<br>
危険度順に5件ずつ表示されます。

* /psac show \<管理ID\>

プレイヤーが提出したレポートの詳細を表示します。<br>
このコマンドをプレイヤーから実行する本が開き、報告の詳細を確認できます。<br>
コンソールから実行した場合、コンソールにそのまま表示されます。

* /psac drop \<管理ID\>

プレイヤーが提出した報告を**完全**に破棄します。<br>
**削除ログは一切記録されません。破棄する場合には十分ご注意ください。**

* /psac kick \<PlayerName\> \[test\]

\<PlayerName\>で指定したプレイヤーをキックします。<br>
第2引数に`test`を指定すると、テストモードとしてキックされます。

### 権限（上から順に）
* psac.help
* psac.view
* psac.show
* psac.drop
* psac.kick

---

### 権限について
権限は最低限コマンドに1つ割り当てられています。<br>
その他、細かく調整することができます。
| 権限 | 割り当てられているコマンド/動作 | その他説明 | デフォルト | グループ |
|:-:|:-:|:-:|:-:|:-:|
| psac.member | サーバーメンバー用の権限です。 | | true | |
| psac.report | /wdr \(report\) | レポートができます。 | true | psac.member |
| psac.report | /psac help | このプラグインの（メンバー）ヘルプを見ることができます。 | true | psac.member |
| psac.notification | プレイヤーが対処されたとき、通知が飛ばされます。 | | true | psac.member |
| psac.regular | 定期メッセージが流れます。 | | true | psac.member |
| ----------------- | --------------------------------------------------- | --------------------------------------------------------- | ---------- | ----------- |
| psac.mod | プレイヤーのキックや、テストをできます。 | | op | |
| psac.kick | /psac kick | プレイヤーをキックできます。 | op | psac.mod |
| psac.aurapanic | /aurapanic \<\PlyerName\> | プレイヤーに回るNPCを送りつけることができます。 | op | psac.mod |
| psac.aurabot | /aurabot \<PlayerName\> | プレイヤーの背後に貼り付く、NPCを召喚します。 | op | psac.mod |
| psac.testkb | /testkb \<PlayerName\> | プレイヤーに見えない弓を放ち、ノックバックを確かめます。 | op | psac.mod |
| psac.viewnpc | 対象プレイヤー以外に、NPCを見ることができます。 | | op | psac.mod |
| psac.view | /view | レポートを表示できます。 | op | psac.mod |
| psac.show | /show \<ManagementID\> | レポートを詳細表示します。 | op | psac.mod |
| psac.bans | /bans \[-a \\| kick \| bans\] | プレイヤーのBAN履歴を参照します。 | op | psac.mod |
| psac.ntfadmin | PEYANG CHEAT DETECTION に、名前を含みます。 | | op | psac.mod |
| psac.reportntf | プレイや～がレポートを送信したとき通知が届きます。 | | op | psac.mod |
| psac.pull | プレイヤーを自分に引き寄せます。 | | op | psac.mod |
| ----------------- | --------------------------------------------------- | --------------------------------------------------------- | ---------- | ----------- |
| psac.admin | レポートの削除や、サーバーの根幹に関わる権限です。 | | false | |
| psac.drop | /psac drop \<ManagementID\> | プレイヤーからのレポートを跡形も残らずに消します。 | false | psac.admin |
| psac.error | エラーが発生したとき、通知されます。| | false | psac.admin |

### \<管理ID\>について
\<管理ID\>は、プレイヤーが報告を提出した際に、自動的に割当られる32文字の英数文字列です。<br>
このIDは、コンソールから `/psr view`をした際に表示されます。<br>
プレイヤーからも管理IDに関連したコマンドを実行できますが、**意味は全くもってないでしょう。**

### キックについて
このプラグインでは誤検出などのためにプレイヤーが誤BANされることを危惧しています。<br>
そのため、プラグインが自動でプレイヤーをBANすることは**絶対に**ございません（たぶん）

### ブロードキャストメッセージについて
プレイヤーが自動でキックされるとき、以下のブロードキャストメッセージが流れます。<br>
**「\[PEYANG CHEAT DETECTION\] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。」**<br>
**「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」**<br>
このメッセージは、チートを自動検出した時のメッセージです。<br>
スタッフによるキックの場合は、<br>
**「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」**<br>
だけが流れます。

### キック理由について
キック事由は3種類に別れています。

### PEYANG CHEAT DETECTION
このプラグインがチートを自動で検知した場合のメッセージです。

### KICKED BY STAFF
スタッフによるキックコマンドが実行された場合のメッセージです。

### PEYANG CHEAT TEST
このプラグインのテストメッセージです。<br>
デバッグ用に使ったり、**お遊びでつかってやってくれぇ**

### NPC について
現NPCは、[@randomapi](https://twitter.com/randomapi)によるAPI「[RandomUserGenerator](https://randomuser.me/)」を使用して、<br>
ランダムなユーザー名を持つプレイヤーを召喚しています。<br>
スキンはランダムですが、現段階では、コンフィグに登録されUUIDのスキンを参照して、ランダム表示されます。

### コンフィグについて
このプラグインでは、以下のコンフィグを使用しています。適当に変えてください。
| 設定名 | デフォルト値 | 説明 |
|:-:|:-:|-|
| database.path | ./eye.db | レポート情報などを保存する、SQLiteデータベースの置き場所です。 |
| database.logPath | ./log.db | キック情報などを保存する、SQLiteデータベースの置き場所です。 |
| npc.seconds | 6 | NPCがプレイヤーの周りを回る秒数です。 |
| npc.time | 0.25 | NPCが回る速さです。感覚で調整してください。 |
| npc.range | 2.1 | NPCが回る半径です。ロマンが全て。デフォルトが最高。 |
| npc.wave | true | NPCが波を描くようにして回るかどうかです。 |
| npc.waveMin | 1.0 | NPCが波を描くようにして回る最低ラインです。 |
| npc.panicRange | 1.5 | Panic NPCがプレイヤーの背後をへばりつく相対的な高さでえす。 |
| npc.kill | 3 | 10秒間プレイヤーをキルされていて、なおかつこの数を超えた場合は、NPCを召喚します。 |
| kick.delay | 2 | プレイヤーをキックするまでの遅延です。ブロードキャストメッセージが流れた瞬間から数えられます。 |
| kick.lightning | true | プレイヤーがキックされるときに、（ダメージを受けない）雷を落とすかどうかです。 |
| kick.defaultKick | 40 | NPCがこの値以上殴られた場合にキックします。学習済データが見つからない場合はこの値が優先されます。 |
| message.lynx | true | Lynx Modと互換性を持たせるかどうかです。 |
| autoMessage.enabled | true | 定期メッセージの有効または無効を切り替えます。 |
| autoMessage.time | 15 | 定期メッセージの時間周期です。分で指定します。 |
| skins | \(UUID...\) | NPCに適用するスキンです。この中からランダムで選ばれます。 |

### 人工知能もどきについて
このプラグインでは、開発名「クソ雑魚ナメクジゴミ人工知能」とよばれる、人工知能**もどき**があります。<br>
実際のチートを用いて学習させることにより、キックの精度が向上する…（**と思って**作ったものです）<br>
~~まぁ平均の平均の平均…をとるだけの単純な機構なんですけどね。~~

↑ハハッ ＾＾；

### message.ymlについて
PeyangSuperbAntiCheat.jarを`mvn package`でビルドすると、`mvn shade`が自動実行されます（たぶん）<br>
その時、message.ymlとかもくっついてきます。<br>
それがPeyangSuperbAntiCheat.jarのなかに同梱されています。<br>
message.ymlをいじくり倒すことで、いろいろできますが、まぁ…うん。後はすべて察しろ。

### バグ等
バグ等は、[**こ↑こ↓**](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues)にて受け付けております。<br>
見つけたら報告お願い申し上げますだなも（？）

### [**こ↑こ↓**](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues)場所の担当者
日本語：[ぺやんぐ](https://github.com/peyang-Celeron)←開発者<br>
~~日本語校正してない人：[Lemonade19x](https://github.com/Lemonade19x)←自称ﾌﾟﾛｸﾞﾗﾏｰ&ここのコミット100回くらいやり直した人♡~~<br>
英語：[Potato1682](https://github.com/Potato1682)←ここのコミット~~3回くらいやり直した~~人♡<br>

### 謝辞
このプラグインは、以下のライブラリ / API を使用しています。<br>
[RandomApi/RandomUserGenerator](https://randomuser.me/)<br>
[brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)<br>
[dmulloy2/ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) [(\*)](https://dev.bukkit.org/projects/protocollib)<br>
[jedk1/BookUtil.java](https://www.spigotmc.org/threads/resource-bookutil-1-8-1-9.131549/)<br>
[DarkBlade12/ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)<br>
[P2P-Develop/PeyangSuperLibrary](https://github.com/P2P-Develop/PeyangSuperLibrary)
