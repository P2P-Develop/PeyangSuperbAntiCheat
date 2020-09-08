PeyangSuperbAntiCheat
======


2020/07/12 08:16  
祝☆400コミット
  
2020/07/19 00:26  
祝☆500コミット
  
2020/08/01 22:58  
祝☆600コミット
  
2020/08/12 14:521  
祝☆700コミット

Bukkit/Spigot/PaperMCのプラグインだったような気がしないような感じがします。  
確信はございません。~~SUMANNA!HAHAHA!~~

1.12.2で動作確認/絶賛開発中です。「プログラムを書いてる」訳ではなく「開発」しています(~~勘違いを誘発する無駄な文章~~)  
このプラグインは、**チートレポート管理** / **チートテスト**プラグインです。  
また、ハックと言う読み方では、サーバー自体のハッキング（クラッキング）との意味が曖昧なため、**チート**として、扱っています。  

ご理解のほど、よろしくお願いしろ…ください。

↑ごめん＾＾；

大手サーバー/運営が常にいるサーバー向け。
([ぺやんぐ研究員の研究メモ](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/memo/memo01.txt)参照。

### 注意

__~~このプラグインはほぼネタプラグインd（殴~~__ **[削除済み]** **[削除済み]** **[削除済み]** **[権限なし]** **[編集済]**

また、このプラグインでは自動BANを~~推奨~~(1)していません。
(1) このプラグインの本当の目的は自動BANを推奨していないのでこの文章は適切ではありません。
そのためBAN関係のものは実装されていません。

このプラグインでは、常時2個DetectOPMで+4個タイマーが動いています（2015/10/21 16:29現在）※~~2020年です~~違います紀元前です
NPCが出てくるたび12個くらいタイマーが増えます。  
すぐ消えるけどね。JavaのVMによっては消えるのに時間がかかる時もあるけど。  
詳しくは[こ↑こ↓](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/PluginThreads.txt) 見て、どうぞ。  
~~某有名系Googleブラウザーと同様~~、メモリ、CPUともにフードファイターの100倍くらい大食いします。  
余裕のあるサーバーで実行することを推奨します。サーバーがぶっ壊れても知りません。~~どうせシングルスレッドだからぶっ壊れるんだろ←そうだよ！~~

このプラグインでは超原始的な本の実装方法を使用しています。ご了承しろやください。

### インストール方法
* 適当にCloneをします。

* Mavenでビルドします。
* [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)[(\*)](https://dev.bukkit.org/projects/protocollib) をpluginsフォルダーに入れます。
* このプラグインをpluginsフォルダーに入れます。
* 再起動して、適用してください。

### コマンド

* /wdr

Hypixelの**~~パクリ~~**コマンドです。  
実際意識して作ってます。

### エイリアス

* /peyangreport
* /pcr
* /rep
* /report
* /watchdogreport

### 説明

* プレイヤー側

こんなものをつかってるぷれいやーがいるよ！みてみて！」  
というコマンドです。  
あくまでも「みてみて！」と報告するだけで、BANをするコマンドではありません。

* スタッフ側

プレイヤーからのレポートは、2形式で、表示されます。  
「\[PeyangSuperbAntiCheat\] プレイヤーがレポートを提出しました！クリックしてレポートを確認してください！ \[CLICK\]」  
または、  
「\[STAFF\] \[ADMIN\] Fishy: Report of \<PlayerName\> \<理由1\>, \[理由2\]...」  
の形で表示されます。コンフィグを編集することにより、変更できます。

このモードは、<details><summary>████</summary>Kam7</details>によって**リークされた**Hypixel Lynx Modとの互換性を、持たせる事ができるモードです。  
このModは、Hypixelで、BANされる可能性があります。  
このModを適用したまま、Hypixelには**絶対に**行かないでください。  
BANされたとしても~~マジで~~知りません。ほんとうです。*ほんとうですからね？？？？*

### 使用法

* /wdr \<PlayerName\>

この状態で実行すると、報告事由を選択できる本が開きます。  
この本に表示されている、報告事由をクリックすると、報告内容として事由が追加されます。

![#008000](https://via.placeholder.com/15/008000/000000?text=+)**レポートを送信**をクリックで送信するか、  
![#008000](https://via.placeholder.com/15/ff0000/000000?text=+)**レポートを放棄**クリックで破棄します。 

* /wdr \<PlayerName\> \<理由1\> \[理由2\]…

このコマンドは、本を使用せずに、コマンドのみで報告できます。  
本を開かないため、**全くもって意味を感じられませんが**、コンソールからでも報告できます。  
まぁタイプ速度でイキりたい人は使っていいんじゃない？[自己責任](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/LICENSE)だけど。  
__このコマンドの事由には、エイリアスを使用できます。下記の表をみろ…*ください*。__

### 報告に使用できる事由一覧

本で表示される順に書きます（たぶん）

| 理由            | エイリアス                      | 簡単な説明                                                          |
|--:-:----------|--:-:-----------------------|----------------------------------------------------------------|
| Fly           | flight                     | プレイヤーがクリエイティブモード以外でも空を飛べるようにできるチートです。                          |
| KillAura      | killaura, aura, ka         | 範囲に入った人をより早く攻撃できるチートです。                                        |
| AutoClicker   | autoclicker, ac, autoclick | 自動で左クリックができるチートです（高橋名人などのツールやマウスなどについているマクロでもAutoClickerに値します） |
| Speed         | speed, bhop, timer         | ありえない速度で歩けるようになるチートです（BunnyHopやTimerはSpeedに値します）               |
| AntiKnockback | akb, velocity, antikb      | ノックバックを軽減するチートです。                                              |
| Reach         | reach                      | 通常より攻撃できる距離が伸びるチートです。                                          |
| Dolphin       | dolphin                    | イルカのように、水を自動で泳ぐチートです。                                          |


* 報告をスパムに利用されることを避けるため、同じ人から同じ人への報告はできません。

### 権限

* psac.report

初期では、全員が持っている権限です。  
この権限を剥奪されたプレイヤーは、報告できなくなります。

---

### /aurabot

### エイリアス

* /testaura
* /auratest
* /killauratest

### 説明
このコマンドを実行すると、プレイヤーの周りにプレイヤーの周囲を一定の速度で回るNPCを召喚します。  
このNPCに対して一定の回数以上ヒットした場合、そのプレイヤーを自動でキックします。
また、第二引数か第一引数のどちらかに-rオプションを付けた場合にはリーチモードとして召喚されます。
リーチモードはNPCをリーチ用の半径上に召喚するため、リーチのチェックも行うことができます。

### 使用法

* /aurabot \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーに、回転NPCを召喚します。

* /aurabot \<PlayerName\> \[-r\]

\<PlayerName\>に指定されたプレイヤーに、リーチモードとして回転NPCを召喚します。

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
また、第二引数か第一引数のどちらかに-rオプションを付けた場合、リーチモードとして召喚されます。
### 使用法

* /acpanic \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーの背後に、NPCを召喚します。

* /acpanic \<PlayerName\> \[-r\]

\<PlayerName\>に指定されたプレイヤーの背後に、リーチモードとしてNPCを召喚します。

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

\<PlayerName\>に指定されたプレイヤーの、BAN履歴を表示します。  
↑BANも記録するんで間違ってないです。  
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

指定されたプレイヤーに、**見えない矢**を**さりげなく**ぶち込みます。  
そのノックバックでVelocityかどうか判定して、どうぞ。  
判定できる...はず。~~しらんけど~~

### 使用法

* /testkb \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーに、矢をそこはかとなくぶち込みます。

### 権限

* psac.testkb

---

### /pull

指定したプレイヤーを引き寄せます。

### エイリアス

* /pul

### 説明

指定したプレイヤーを、実行者に引き寄せます。  
コンソールからは、実行できません。

### 使用法

* /pull \<PlayerName\>

### 権限

* psac.pull

---

### /mods

指定したプレイヤーのModを表示します。

### 説明

最強の浦沢をつかって、プレイヤーが導入しているForgeのModを、一覧表示します。  
応答はModのIDで返答されます。脳内補完して、どうぞ。  
Mod IDの変換は実装しま~~す~~ん。

### 使用法

* /mods \<PlayerName\>

### 権限

* psac.mod**s**
注意：mod**s** の **s** は、非常に重要です。いやマジで。

---

### /target

指定したプレイヤーをターゲットとして追跡します。

### 説明

このコマンドを実行すると、いくつかのアイテムが、手に入ります。  
このアイテムを、クリックする、ことにより、プレイヤーに、NPCを出し、たり、  
まぁ、色々できる、マ、ク、ロ、の、よ、う、な、も、ん、だ。  
ドロップすると全部消えるよ！

### 使用法

* /target \<PlayerName\>

### 権限

* psac.target

---

### /tracking

指定したプレイヤーを、ターゲットとして追跡します。

### エイリアス

* /track

### 説明
引数に指定したプレイヤーを追跡します。  
引数に何も指定しなかった場合は追跡が解除されます。

### 使用法

* /tracking \[PlayerName\]

### 権限

* psac.tracking

---

### /trust

指定したプレイヤーを、信用します。

### エイリアス

* /noscan
* /trustplayer

### 説明

引数に指定した、プレイヤーを信用します。  
信用されている場合は、信用を解除します。  
信用されたプレイヤーに対しては、psac.trust権限を持っているプレイヤーしかスキャンやテストを行えなくなります。  
信用するのにもpsac.trust権限が必要です。

### 使用法

* /trust \<PlayerName\>

### 権限

* psac.trust

---

### /userinfo

指定したプレイヤーに、自分、または、指定したプレイヤーを、テレポートさせます。

### エイリアス

* just /**userinfo**

### 説明
プレイヤーの情報を一覧表示します。  
[私](https://github.com/peyang-Celeron)としてはいらないと思いますが、  
LynxModがつくらないとうごかん！というので仕方ありません。  
普段はスタイリッシュで簡潔な情報が表示されます。  
Lynxモード\(-f\)が有効の場合、Rankなどの情報が最低限表示されます。  
めんどうですね。  
また、オフラインプレイヤーも対応しております。

### 使用法

* /userinfo \[-f\] \<PlayerName\>

### 権限

* psac.userinfo

---

### /silentteleport

指定したプレイヤーに、自分、または、指定したプレイヤーを、テレポートさせます。

### エイリアス

* /stp
* /tpto

### 説明
引数に指定したプレイヤーにテレポートします。  
2つ引数を指定した場合は、第1引数のプレイヤーが第2引数のプレイヤーにテレポートします。

### 使用法

* /stp \[PlayerName\] \<PlayerName\>

### 権限

* psac.silentteleport

---

### /psac

### エイリアス

* /peyangsuperbanticheat
* /psr
* /wdadmin
* /anticheat

### 説明

このプラグインのメインコマンドです。引数をつけることによって動作します。  

##### 引数をしっかり付けてください。しっかり...

### 引数一覧

* /psac help

このプラグインコマンドのヘルプを表示します。  
`psac.mod`または、`psac.admin`権限を持っている人には以下のヘルプも表示されます。
* /psac view \[ページ数\]

プレイヤーが提出した、レポートを確認できます。  
危険度順に、5件ずつ表示されます。

* /psac show \<管理ID\>

プレイヤーが提出した、レポートの詳細を、表示します。  
このコマンドを、プレイヤーから実行する、本が、開き、報告の詳細を、確認、できます。  
コンソールから、実行した、場、合、コ、ン、ソ、ー、ル、にそのまま、表示、されます。

* /psac drop \<管理ID\>

プレイヤーが提出した報告を**完全**に破棄します。  
**削除ログは一切記録されません。破棄する場合には十分ご注意ください。**

* /psac kick \<PlayerName\> \[test\]

\<PlayerName\>で指定したプレイヤーをキックします。  
第2引数に`test`を指定すると、テストモードとしてキックされます。

### 権限（上から順に）

* psac.help
* psac.view
* psac.show
* psac.drop
* psac.kick

---

### 権限について

権限は最低限コマンドに1つ割り当てられています。  
その他、細かく調整できます。しらんけど。

| 権限 | 割り当てられているコマンド | その他説明 | デフォルト | グループ |
|:-:|:-:|:-:|:-:|:-:|
| _**psac.member**_ | サーバーメンバー用の権限です。 | | true | |
| psac.report | /wdr \(report\) | レポートができます。 | true | psac.member |
| psac.report | /psac help | このプラグインの（メンバー）ヘルプを見ることができます。 | true | psac.member |
| psac.notification | プレイヤーが対処されたとき、通知が飛ばされます。 | | true | psac.member |
| psac.regular | 定期メッセージが流れます。 | | true | psac.member |
| _**psac.mod**_ | プレイヤーのキックやテストをできます。 | | op | |
| psac.kick | /psac kick | プレイヤーをキックできます。 | op | psac.mod |
| psac.aurapanic | /aurapanic \<\PlayerName\> | プレイヤーに回るNPCを送りつけることができます。 | op | psac.mod |
| psac.aurabot | /aurabot \<PlayerName\> | プレイヤーの背後に貼り付く、NPCを召喚します。 | op | psac.mod |
| psac.testkb | /testkb \<PlayerName\> | プレイヤーに見えない弓を放ち、ノックバックを確かめます。 | op | psac.mod |
| psac.viewnpc | 対象プレイヤー以外に、NPCを見ることができます。 | | op | psac.mod |
| psac.view | /view | レポートを表示できます。 | op | psac.mod |
| psac.show | /show \<ManagementID\> | レポートを詳細表示します。 | op | psac.mod |
| psac.bans | /bans \[-a \\| kick \| bans\] | プレイヤーのBAN履歴を参照します。 | op | psac.mod |
| psac.ntfadmin | PEYANG CHEAT DETECTION に、名前を含みます。 | | op | psac.mod |
| psac.reportntf | プレイヤーがレポートを送信したとき通知が届きます。 | | op | psac.mod |
| psac.pull | プレイヤーを自分に引き寄せます。 | | op | psac.mod |
| psac.chattarget | プレイヤーのチャットの左にマークが付きます。 | | op | psac.mod |
| psac.mods | プレイヤーのModを見ることができます。 | | op | psac.mod |
| psac.tracking | プレイヤー追跡に関する**コマンド**の権限です。 | | op | psac.mod |
| psac.userinfo | プレイヤーの情報を表示します。LynxモードがOnの場合は、一部増えます\(関係ない情報が\) | | op | psac.mod |
| _**psac.admin**_ | レポートの削除や、サーバーの根幹に関わる権限です。 | | false | |
| psac.drop | /psac drop \<ManagementID\> | プレイヤーからのレポートを跡形も残らずに消します。 | false | psac.admin |
| psac.error | エラーが発生したとき、通知されます。| | false | psac.admin |
| psac.trust | 信用されているプレイヤーでも問答無用で操作を行うことができます。 | | false | psac.admin |

### \<管理ID\>について

\<管理ID\>は、プレイヤーが報告を提出した際に、自動的に割当られる32文字の英数文字列です。  
このIDは、コンソールから `/psr view`をした際に表示されます。  
プレイヤーからも管理IDに関連したコマンドを実行できますが、**意味は全くもってないでしょう。** それでも導入するのがPSACｸｵﾘﾃｨ。

### キックについて

このプラグインでは誤検出などのためにプレイヤーが誤BANされることを危惧しています。  
そのため、プラグインが自動でプレイヤーをBANすることは**絶対に**ございません（たぶん）  
↑プラグインによってBANされたらIssueで~~教えろ~~ください。コラボレーターが開発者に殴り込みに行きます。

### ブロードキャストメッセージについて

プレイヤーが自動でキックされるとき、以下のブロードキャストメッセージが流れます。  
**「\[PEYANG CHEAT DETECTION\] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。」**  
**「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」**  
このメッセージは、チートを自動検出した時のメッセージです。  
スタッフによるキックの場合は、  
**「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」**  
だけが流れます。

### キック理由について

キック事由は3種類に分かれています。

### PEYANG CHEAT DETECTION

このプラグインがチートを自動で検知した場合のメッセージです。

### KICKED BY STAFF

スタッフによるキックコマンドが実行された場合のメッセージです。

### PEYANG CHEAT TEST

このプラグインのテストメッセージです。  
デバッグ用に使ったり、**お遊びでつかってやってくれぇ**。 

##### BANシステムはないから安心してね♡

### NPC について

現NPCは、同梱された英単語リストを使用して、  
ランダムなユーザー名を持つプレイヤーを召喚しています。  
スキンは、同梱された(現時点では)1400種類のスキンセットからランダムで選ばれます.


### コンフィグについて

このプラグインでは、以下のコンフィグを使用しています。適当に変えてください。

| 設定名 | デフォルト値 | 説明 |
|:-:|:-:|-|
| database.path | ./eye.db | レポート情報などを保存する、SQLiteデータベースの置き場所です。 |
| database.logPath | ./log.db | キック情報などを保存する、SQLiteデータベースの置き場所です。 |
| database.learnPath | ./learn.json | 学習によって値が変更された「重み」と学習回数を保存する、JSONファイルの置き場所です。 |
| database.learnPath | ./trust.db | 信用したプレイヤーを保存する、SQLiteデータベースの置き場所です。つまりこれ消せば信用データ消えます。やっぱ鯖を操る人最強。 |
| npc.seconds | 4 | NPCがプレイヤーの周りを回る秒数です。 |
| npc.time | 0.3 | NPCが回る速さです。感覚で調整してください。 |
| npc.range | 2.1 | NPCが回る半径です。ロマンが全て。デフォルトが最高。これがPSACｸｵﾘﾃｨ。 |
| npc.reachRange | 4.6 | NPCがリーチモードとしてぶん回る半径です。-rオプションがつくとこの値が使用されます。精度？なんそれおいしいの？ |
| npc.wave | true | NPCが波を描くようにして回るかどうかです。 |
| npc.waveMin | 1.0 | NPCが波を描くようにして回る最低ラインです。 |
| npc.panicRange | 1.5 | Panic NPCがプレイヤーの背後をへばりつく相対的な高さでえす。 |
| npc.panicReachRange | 4.6 | Panic NPCがプレイヤーの背後をリーチモードとしてへばりつく相対的な高さですよん。-rオｐ(ry。精ｄ(ry |
| npc.speed.wave | true | NPCのスピードをWaveで変更するかどうかです。 |
| npc.speed.waveRange | 0.03 | Waveで変更する範囲です。 |
| npc.learn | 0.3 | NPCの学習機能の学習係数を指定します。値が大きいほど処理は少なくなりますが精度が下がります。 |
| npc.vlLevel | 17 | NPCがnpc.learnCountより学習出来ていない場合にこの値を利用してVLを評価します。 |
| npc.learnCount | 15 | 学習機能がこの回数以上学習した場合にkickの評価を学習機能に譲渡します。 |
| npc.kill | 3 | 10秒間プレイヤーをキルされていて、なおかつこの数を上回った場合は、NPCを召喚します。 |
| kick.delay | 2 | プレイヤーをキックするまでの遅延です。ブロードキャストメッセージが流れた瞬間から数えられます。 |
| kick.defaultKick | 25 | NPCがこの値以上殴られた場合にキックします。学習済データが見つからない場合はこの値が優先されます。 |
| decoration.lightning | true | プレイヤーがキックされるときに、ダメージを受けない雷を落とすかどうかです。 |
| decoration.flame | true | プレイヤーがキックされるときに、ブレイズのパーティクルでアピールするかどうかです。 |
| decoration.circle | true | プレイヤーがキックされるときに、色付きの円でアピールするかどうかです。 |
| message.lynx | true | Lynx Modと互換性を持たせるかどうかです。 |
| autoMessage.enabled | true | 定期メッセージの有効または無効を切り替えます。 |
| autoMessage.time | 15 | 定期メッセージの時間周期です。分で指定します。 |
| skins | \(UUID...\) | NPCに適用するスキンです。この中からランダムで選ばれます。 |

### 人工知能もどきについて

~~このプラグインでは、開発名「クソ雑魚ナメクジゴミ人工知能」とよばれる、人工知能**もどき**があります。  
実際のチートを用いて学習させることにより、キックの精度が向上する…（**と思って**作ったものです~~）  
心優しい英語ドキュメント担当者が機械学習を追加しました。キックの回数が増えるほど精度が~~下~~超上がります。
BANされるべき人がBANされなくても~~上の通り~~知りません。勝手にフォークしてアルゴリ**ス**ム作り直せください。

↑ハハッ ＾＾；

### message.ymlについて

PeyangSuperbAntiCheat.jarを`mvn package`でビルドすると、`mvn shade`が自動実行されます（たぶん）←ちゃんと実行されます  
その時、message.ymlとかもくっついてきます。  
それがPeyangSuperbAntiCheat.jarのなかに同梱されています。  
message.ymlをいじくり倒すことで、いろいろできますが、まぁ…うん。後はすべて察しろ。

### ターゲットアイテムについて

`/target`コマンドを実行すると、いくつかのアイテムが手に入ります。  
このアイテムは右クリックで実行でき、ドロップで削除されます。  
アイテムは以下の通りです。

| アイテム            | ID                   | その他説明                                 | 実行されるコマンド                |
|:---------------:|:--------------------:|:-------------------------------------:|:------------------------:|
| 犬の頭             | AURA_BOT             | AuraBotが対象に飛ばされます。                    | /aurabot <Target>        |
| 犬の頭             | AURA_PANIC           | AuraPanicNPCが対象に飛ばされます。               | /acpanic <Target>        |
| --------------- | -------------------- | ------------------------------------- | ------------------------ |
| 矢               | TEST_KB              | 対象に見えない矢が飛んでいきます。                     | /testkb <Target>         |
| コンパス            | TRACKER              | 対象にテレポートします。                          | /tpto <Target>           |
| 本               | BANS                 | 対象の処罰情報が表示されます。                       | /bans -a <Target>        |
| 矢               | TO_TARGET_<番号>       | ページ2に飛びます。                            | /target <Target> <番号>    |
| 時計              | BACK                 | 戻ります。                                 | ======================== |
| 矢               | BACK_TOTARGET_<番号>   | (ページ1に)戻ります。                          | /target <Target> <番号>    |
| --------------- | -------------------- | ------------------------------------- | ------------------------ |
| リード             | PULL                 | 対象を引き寄せます。                            | /pull <Target>           |
| 本               | MOD_LIST             | 対象の入れてるMod一覧です。                       | /mods <Target>           |
| --------------- | -------------------- | ------------------------------------- | ------------------------ |
| ブレイズロッド         | TARGET_STICK         | 目の前のプレイヤを対象にします。                      | /target <Player>         |

### バグ等

バグ等は、[**こ↑こ↓**](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues)にて受け付けております。  
見つけたら報告お願い申し上げますだなも（？）

### [**こ↑こ↓**](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues)場所の担当者

日本語：[ぺやんぐ](https://github.com/peyang-Celeron)←開発者  
~~日本語校正してない人：[Lemonade19x](https://github.com/Lemonade19x)←自称ﾌﾟﾛｸﾞﾗﾏｰ&ここのコミット100回くらいやり直した人♡~~  
英語：[Potato1682](https://github.com/Potato1682)←ここのコミット~~3回くらいやり直した~~人♡  

### 謝辞

このプラグインは、以下のライブラリ / API を使用しています。  
[brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)  
[dmulloy2/ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) [(\*)](https://dev.bukkit.org/projects/protocollib)  
[jedk1/BookUtil.java](https://www.spigotmc.org/threads/resource-bookutil-1-8-1-9.131549/)  
[DarkBlade12/ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)  
[P2P-Develop/PeyangSuperLibrary](https://github.com/P2P-Develop/PeyangSuperLibrary)  
[PhantomUnicorns](https://stackoverflow.com/users/6727559/phantomunicorns)
[Matrix API](https://matrix.rip/) [\(*\)](https://www.mc-market.org/resources/13999/)
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
[縺吶∋縺ｦ縺ｯ隕∫ｴ?↓驕弱℃縺ｾ縺帙ｓ縲ょ?縺ｦ縺ｯ?遨ｶ繝｡?縺ｫ譖ｸ縺?※縺ゅｊ縺ｾ縺吶?ゅ％縺薙ｒ繧ｯ繝ｪ繝?け縺励※隱ｭ繧?縺薙→縺後〒縺阪∪縺吶?](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/tree/master/docs/memo)
