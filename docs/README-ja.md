PeyangSuperbAntiCheat
======
If possible.

<details>
<summary>目次</summary>

- [PeyangSuperbAntiCheat](#peyangsuperbanticheat)
  - [概要](#概要)
    - [注意](#注意)
    - [インストール方法](#インストール方法)
    - [コマンド](#コマンド)
    - [エイリアス](#エイリアス)
    - [説明](#説明)
    - [使用法](#使用法)
    - [報告に使用できる事由一覧](#報告に使用できる事由一覧)
    - [権限](#権限)
    - [/aurabot](#aurabot)
    - [エイリアス](#エイリアス-1)
    - [説明](#説明-1)
    - [使用法](#使用法-1)
    - [権限](#権限-1)
    - [/acpanic](#acpanic)
    - [エイリアス](#エイリアス-2)
    - [説明](#説明-2)
    - [使用法](#使用法-2)
    - [権限](#権限-2)
    - [/testkb](#testkb)
    - [エイリアス](#エイリアス-4)
    - [説明](#説明-4)
    - [使用法](#使用法-4)
    - [権限](#権限-4)
    - [/pull](#pull)
    - [エイリアス](#エイリアス-5)
    - [説明](#説明-5)
    - [使用法](#使用法-5)
    - [権限](#権限-5)
    - [/target](#target)
    - [説明](#説明-6)
    - [使用法](#使用法-6)
    - [権限](#権限-6)
    - [/tracking](#tracking)
    - [エイリアス](#エイリアス-6)
    - [説明](#説明-7)
    - [使用法](#使用法-7)
    - [権限](#権限-7)
    - [/trust](#trust)
    - [エイリアス](#エイリアス-7)
    - [説明](#説明-8)
    - [使用法](#使用法-8)
    - [権限](#権限-8)
    - [/silentteleport](#silentteleport)
    - [エイリアス](#エイリアス-8)
    - [説明](#説明-9)
    - [使用法](#使用法-9)
    - [権限](#権限-9)
    - [/kick](#kick)
    - [説明](#説明-10)
    - [使用法](#使用法-10)
    - [権限](#権限-10)
    - [/psac](#psac)
    - [エイリアス](#エイリアス-11)
    - [説明](#説明-11)
    - [引数一覧](#引数一覧)
    - [権限（上から順に）](#権限上から順に)
    - [権限について](#権限について)
    - [\<管理 ID\>について](#管理-idについて)
    - [キックについて](#キックについて)
    - [ブロードキャストメッセージについて](#ブロードキャストメッセージについて)
    - [NPCについて](#npcについて)
    - [コンフィグについて](#コンフィグについて)
    - [人工知能のようなものについて](#人工知能のようなものについて)
    - [言語について](#言語について)
      - [英語](#英語)
      - [日本語](#日本語)
    - [message.yml について](#messageyml-について)
    - [ターゲットアイテムについて](#ターゲットアイテムについて)
    - [バグ等](#バグ等)
    - [Issueの担当者](#Issueの担当者)
    - [謝辞](#謝辞)

</details>

---

### 概要

[**BungeeCordへのつなぎ方はこちらをご覧ください。**](BUNGEE.md)

このプラグインはSpigotやPaperMCのプラグインで、チートテストやチートレポートの管理を手動でするプラグインです。  
そのため、運営が常にいるサーバー向けです。
このプロジェクトは、[行動規範](CODE-OF-CONDUCT.md)の下でリリースされていることに注意してください。  
プロジェクトに参加することより、あなたは条件に従うことに同意するものとします。

ご理解のほどよろしくお願い申し上げます。

### 注意
このプラグインは、常時2個DetectOPMで4個タイマーが動いています。  
NPCが出てくるたびに12個ほどタイマーが増えます。  
すぐに消えますが、JavaのVMによって変わります。  
詳しくは[こちらをご覧ください。](PluginThreads.txt)

このプラグインはRAMやCPUを大量に使用するため、リソースに余裕のあるサーバーで実行することをおすすめします。

このプラグインでは、初歩的な本の実装方法を使用しています。

---

### インストール方法

- [ここ](https://github.com/P2P-Develop/PeyangSuperbAntiCheat/releases)からダウンロードします。
- クローンします。
- Mavenでビルドします。
- [ProtocolLib](https://www.spigotmc.org/resources/1997/)をpluginsフォルダーに入れます。
- このプラグインをpluginsフォルダーに入れます。
- サーバーを再起動してください。

### コマンド

- /wdr

このプラグインのメインコマンドです。  

### エイリアス

- /peyangreport
- /pcr
- /rep
- /report
- /watchdogreport

### 説明

- プレイヤー側

チートを使用しているプレイヤーをスタッフに報告するコマンドです。

- スタッフ側

プレイヤーからの報告は2形式で表示されます。  
「\[PeyangSuperbAntiCheat\] プレイヤーがレポートを提出しました！クリックしてレポートを確認してください！ \[CLICK\]」  
または、  
「\[STAFF\] \[ADMIN\] Fishy: Report of \<PlayerName\> \<理由 1\>, \[理由 2\]...」  
の形で表示されますが、コンフィグを編集することにより変更できます。

このモードはとある人物によりリークされた、Hypixelのスタッフ向けModのHypixel Lynxと互換性を持たせる事ができるモードです。  
Hypixel Lynxを導入したまま、Hypixelには参加しないでください。  
AntiForgeを使用すれば回避できますが、即座にBANされます。
このModを作成したのは、すでに退職したAdminのSpencer Alderman氏だと思われます。

### 使用法

- /wdr \<PlayerName\>

この状態で実行すると、報告の理由を選択できる本が開きます。  
本に表示されている報告理由をクリックすると報告内容として追加されます。

レポートを送信でクリックで送信するか、レポートを放棄をクリックで破棄します。

- /wdr \<PlayerName\> \<理由 1\> \[理由 2\]…

このコマンドは本を使用せずにコマンドのみで報告できますが、コンソールからでも報告できます。

**このコマンドの事由にはエイリアスを使用できます。下記の表をご覧ください。**

### 報告に使用できる理由一覧

本で表示される順に書き起こします。

| 理由          | エイリアス                 | 簡単な説明                                                                                                          |
| :------------ | :------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| Fly           | flight                     | プレイヤーがクリエイティブやスペクテイターモード以外でも、空を飛べるようにできるチートです。                                          |
| KillAura      | killaura, aura, ka         | 設定で異なりますが、範囲に入った人を全方向で攻撃できるチートです。                                                                      |
| AutoClicker   | autoclicker, ac, autoclick | 自動で左クリックができるチートです。マクロもAutoClickerになります。 |
| Speed         | speed, bhop, timer         | とても早い速度で歩けるようになるチートです。Bunnyhopや、TimerもSpeedになります。                                 |
| AntiKnockback | akb, velocity, antikb      | ノックバックを軽減するチートです。                                                                                  |
| Reach         | reach                      | 通常より攻撃できる距離が伸びるチートです。                                                                          |
| Dolphin       | dolphin                    | イルカのように水を自動で泳ぐチートです。                                                                          |

- 報告をスパムされることを避けるため、同じ人から同じ人への報告はできません。

### 権限

- psac.report

初期では全員持っている権限です。  
この権限を剥奪されたプレイヤーはプレイヤーを報告できなくなります。

---

### /aurabot

### エイリアス

- /testaura
- /auratest
- /killauratest

### 説明

このコマンドを実行すると、プレイヤーの周りにプレイヤーの周囲を一定の速度で回るNPCを召喚します。  
このNPCに対して一定の回数以上ヒットした場合、そのプレイヤーを自動でキックします。  
また、第二引数か第一引数のどちらかに-rオプションを付けた場合にはリーチモードとして召喚されます。  
リーチモードはNPCをリーチ用の半径上に召喚するため、リーチのチェックも行えます。

### 使用法

- /aurabot \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーに回転NPCを召喚します。

- /aurabot \<PlayerName\> \[-r\]

\<PlayerName\>に指定されたプレイヤーにリーチモードとして回転NPCを召喚します。

### 権限

- psac.aurabot

---

### /acpanic

### エイリアス

- /testpanic
- /panictest
- /aurapanictest

### 説明

このコマンドを実行すると、プレイヤーの背後にへばりつくようにNPCを指定された秒数召喚します。  
また、第二引数か第一引数のどちらかに-rオプションを付けた場合、リーチモードとして召喚されます。

### 使用法

- /acpanic \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーの背後にNPCを召喚します。

- /acpanic \<PlayerName\> \[-r\]

\<PlayerName\>に指定されたプレイヤーの背後にリーチモードとしてNPCを召喚します。

### 権限

- psac.aurapanic

---

### /testkb

### エイリアス

- /testknockback
- /kbtest
- /knockbacktest

### 説明

指定されたプレイヤーに見えない矢を打ちます。  
そのノックバックでプレイヤーがAntiKnockbackかどうかを判定できます。

### 使用法

- /testkb \<PlayerName\>

\<PlayerName\>に指定されたプレイヤーに矢を打ちます。

### 権限

- psac.testkb

---

### /pull

指定したプレイヤーを引き寄せます。

### エイリアス

- /pul

### 説明

指定したプレイヤーを実行者に引き寄せます。  
コンソールからは実行できません。

### 使用法

- /pull \<PlayerName\>

### 権限

- psac.pull

---

### /target

指定したプレイヤーをターゲットとして追跡します。

### 説明

このコマンドを実行すると、いくつかのアイテムが手に入ります。  
アイテムをクリックすることによりプレイヤーにNPCを出したりできるマクロです。 
ドロップすると全部消えます。

### 使用法

- /target \<PlayerName\>

### 権限

- psac.target

---

### /tracking

指定したプレイヤーをターゲットとして追跡します。

### エイリアス

- /track

### 説明

引数に指定したプレイヤーを追跡します。  
引数に何も指定しなかった場合は追跡が解除されます。

### 使用法

- /tracking \[PlayerName\]

### 権限

- psac.tracking

---

### /trust

指定したプレイヤーを信用します。

### エイリアス

- /noscan
- /trustplayer

### 説明

引数に指定したプレイヤーを信用します。  
信用されている場合は、信用を解除します。  
信用されたプレイヤーに対してはpsac.trust権限を持っているプレイヤーしかスキャンやテストができなくなります。  
信用するのにもpsac.trust権限が必要です。

### 使用法

- /trust \<PlayerName\>

### 権限

- psac.trust

---

### /silentteleport

指定したプレイヤーに自分または指定したプレイヤーをテレポートさせます。

### エイリアス

- /stp
- /tpto

### 説明

引数に指定したプレイヤーにテレポートします。  
2つ引数を指定した場合は、第1引数のプレイヤーが第2引数のプレイヤーにテレポートします。

### 使用法

- /stp \[PlayerName\] \<PlayerName\>

### 権限

- psac.silentteleport

---

### /kick

指定したプレイヤーを、ゲームから退出させます。

### 説明

第1引数に指定したプレイヤーにゲームから退出してもらいます。  
また、第2引数を指定することにより理由をつけて退出させられます。

### 使用法

- /kick \<PlayerName\> \[理由\]

### 権限

- psac.kick

---

### /psac

### エイリアス

- /peyangsuperbanticheat
- /psr
- /wdadmin
- /anticheat

### 説明

このプラグインのメインコマンドです。引数をつけることによって動作します。

### 引数一覧

- /psac help

このプラグインコマンドのヘルプを表示します。  
`psac.mod`または、`psac.admin`権限を持っている人には以下のヘルプも表示されます。

- /psac view \[ページ数\]

プレイヤーが提出したレポートを確認できます。  
危険度順に5件ずつ表示されます。

- /psac show \<管理 ID\>

プレイヤーが提出したレポートの詳細を表示します。  
このコマンドをプレイヤーから実行する本が開き、報告の詳細を確認できます。

- /psac drop \<管理 ID\>

プレイヤーが提出した報告を**完全**に破棄します。  
**削除ログは一切記録されません。破棄する場合には十分ご注意ください。**

### 権限（上から順に）

- psac.help
- psac.view
- psac.show
- psac.drop
- psac.kick

---

### 権限について

権限は最低限コマンドに1つ割り当てられています。 その他細かく調整できます。

|       権限        |                               割り当てられているコマンド                                |                        その他説明                        |             デフォルト              |  グループ   |
| :---------------: | :-------------------------------------------------------------------------------------: | :------------------------------------------------------: | :---------------------------------: | :---------: |
| _**
psac.member**_ |                             サーバーメンバー用の権限です。                              |                                                          |                true                 |             |
|    psac.report    |                                     /wdr \(report\)                                     |                   レポートができます。                   |                true                 | psac.member |
|    psac.report    |                                       /psac help                                        | このプラグインの（メンバー）ヘルプを見ることができます。 |                true                 | psac.member |
| psac.notification |                    プレイヤーが対処されたとき通知が飛ばされます。                     |                                                          |                true                 | psac.member |
|   psac.regular    |                               定期メッセージが流れます。                                |                                                          |                true                 | psac.member |
|  _**
psac.mod**_   |                         プレイヤーのキックやテストをできます。                          |                                                          |                 op                  |             |
|     psac.kick     |                                       /psac kick                                        |               プレイヤーをキックできます。               |                 op                  |  psac.mod   |
|  psac.aurapanic   |                               /aurapanic \<\PlayerName\>                                |    プレイヤーに回るNPCを送りつけることができます。     |                 op                  |  psac.mod   |
|   psac.aurabot    |                                 /aurabot \<PlayerName\>                                 |      プレイヤーの背後に貼り付くNPCを召喚します。      |                 op                  |  psac.mod   |
|    psac.testkb    |                                 /testkb \<PlayerName\>                                  | プレイヤーに見えない弓を放ち、ノックバックを確かめます。 |                 op                  |  psac.mod   |
|   psac.viewnpc    |                    対象プレイヤー以外にNPCを見ることができます。                     |                                                          |                 op                  |  psac.mod   |
|     psac.view     |                                          /view                                          |                 レポートを表示できます。                 |                 op                  |  psac.mod   |
|     psac.show     |                                 /show \<ManagementID\>                                  |                レポートを詳細表示します。                |                 op                  |  psac.mod   |
|   psac.ntfadmin   |                       PEYANG CHEAT DETECTIONに名前を含みます。                       |                                                          |                 op                  |  psac.mod   |
|  psac.reportntf   |                   プレイヤーがレポートを送信したとき通知が届きます。                    |                                                          |                 op                  |  psac.mod   |
|     psac.pull     |                            プレイヤーを自分に引き寄せます。                             |                                                          |                 op                  |  psac.mod   |
|  psac.chattarget  |                      プレイヤーのチャットの左にマークが付きます。                       |                                                          |                 op                  |  psac.mod   |
|   psac.tracking   |                     プレイヤー追跡に関する**
コマンド**の権限です。                      |                                                          |                 op                  |  psac.mod   |
| _**
psac.admin**_  |                   レポートの削除や、サーバーの根幹に関わる権限です。                    |                                                          |                false                |             |
|     psac.drop     |                               /psac drop \<ManagementID\>                               |    プレイヤーからのレポートを跡形も残らずに消します。    |                false                | psac.admin  |
|    psac.error     |                          エラーが発生したとき通知されます。                           |                                                          |                false                | psac.admin  |
|    psac.trust     |            信用されているプレイヤーでも問答無用で操作できます。             |                                                          |                false                | psac.admin  |

### \<管理 ID\>について

\<管理 ID\>はプレイヤーが報告を提出した際に自動的に割当られる32文字の英数文字列です。  
このIDはコンソールから`/psr view`をした際に表示されます。  

### キックについて

このプラグインでは、誤検出などのためにプレイヤーが誤BANされることを危惧しています。  
そのため、勝手にプレイヤーをBANすることはありません。

### ブロードキャストメッセージについて

プレイヤーが自動でキックされるとき以下のブロードキャストメッセージが流れます。  
**「\[PEYANG CHEAT DETECTION\] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。」**  
**「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」**  
このメッセージはチートを自動検出した時のメッセージです。  
スタッフによるキックの場合は、  
**「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」**  
だけが流れます。

### NPC について

現NPCは同梱された英単語リストを使用して、 ランダムなユーザー名を持つプレイヤーを召喚しています。  
スキンは同梱された（現時点では）1400種類のスキンセットからランダムで選ばれます。

### コンフィグについて

このプラグインでは以下のコンフィグを使用しています。SQL関係は、[こちら](SQL.md)を参照してください。

|        設定名        |  デフォルト値   | 説明                                                                                                                        |
| :------------------: | :-------------: | --------------------------------------------------------------------------------------------------------------------------- |
|   database.method    | org.sqlite.JDBC | SQLの設定（メソッド）                                                                                                        |
|     database.url     |   jdbc:sqlite   | SQLの設定（URL）                                                                               |
|   database.logPath   |    ./log.db     | キック情報などを保存するSQLiteデータベースの置き場所です。                                                               |
|  database.learnPath  |  ./learn.json   | 学習によって値が変更された「重み」と学習回数を保存するJSONファイルの置き場所です。                                       |
|  database.trustPath  |   ./trust.db    | 信用したプレイヤーを保存するSQLiteデータベースの置き場所です。これを消去すると信用データが消えます。 |
|        lang          |       en        | [プラグインのメッセージの表示に使用する言語を設定します。](#言語について)                                                                   |
|     npc.seconds      |        3        | NPCがプレイヤーの周りを回る秒数です。                                                                                      |
|       npc.time       |      0.35       | NPCが回る速さです。感覚で調整してください。                                                                                |
|      npc.range       |    3.0, 1.5     | NPCが回る半径です。                                                     |
|    npc.reachRange    |       4.6       | NPCがリーチモードとしてぶん回る半径です。-rオプションがつくとこの値が使用されます。             |
|       npc.wave       |      true       | NPCが波を描くようにして回るかどうかです。                                                                                  |
|     npc.waveMin      |       0.3       | NPCが波を描くようにして回る最低ラインです。                                                                                |
|    npc.panicRange    |       1.5       | Panic NPCがプレイヤーの背後をへばりつく相対的な高さでえす。                                                                |
| npc.panicReachRange  |       4.6       | Panic NPCがプレイヤーの背後をリーチモードとしてへばりつく相対的な高さです。|
|    npc.speed.wave    |      true       | NPCのスピードをWaveで変更するかどうかです。                                                                              |
| npc.speed.waveRange  |      0.03       | Wave で変更する範囲です。                                                                                                   |
|      npc.learn       |       0.3       | NPCの学習機能の学習係数を指定します。値が大きいほど処理は少なくなりますが、精度が下がります。                                |
|     npc.vlLevel      |       17        | NPCがnpc.learnCountより学習出来ていない場合にこの値を利用してVLを評価します。                                          |
|    npc.learnCount    |       15        | 学習機能がこの回数以上学習した場合にキックの評価を学習機能に譲渡します。                                                    |
|       npc.kill       |        3        | 10秒間プレイヤーをキルしていて、この数を上回った場合はNPCを召喚します。                                       |
|      kick.delay      |        2        | プレイヤーをキックするまでの遅延です。ブロードキャストメッセージが流れた瞬間から数えられます。                              |
|   kick.defaultKick   |       25        | NPCがこの値以上殴られた場合にキックします。学習済データが見つからない場合はこの値が優先されます。                          |
| decoration.lightning |      true       | プレイヤーがキックされるときにダメージを受けない雷を落とすかどうかです。                                                  |
|   decoration.flame   |      true       | プレイヤーがキックされるときにブレイズのパーティクルでアピールするかどうかです。                                          |
|  decoration.circle   |      true       | プレイヤーがキックされるときに色付きの円でアピールするかどうかです。                                                      |
|     message.lynx     |      true       | Lynxと互換性を持たせるかどうかです。                                                                                   |
| autoMessage.enabled  |      true       | 定期メッセージの有効または無効を切り替えます。                                                                              |
|   autoMessage.time   |       15        | 定期メッセージの時間周期です。分で指定します。                                                                              |

### 人工知能のようなものについて

このプラグインでは、開発名「PeyangAI」と呼ばれる、人工知能のようなものがあります。  
実際のチートを用いて学習させることにより、キックの精度が向上すると思って作ったものです。

### 言語について

とても心の広い英語ドキュメントの担当者がプラグインを多言語化しました。  
ほぼすべての言語を扱えるわけではありませんが、英語 \(en\)、日本語 \(ja\)の2種類を扱えます。
[コンフィグについて](#コンフィグについて)に記載されている通り、`lang`プロパティに指定することで簡単に言語の変更ができます。  
`lang`プロパティにはある程度曖昧な書き方ができるようにエイリアスも指定されています。  
エイリアスは以下の通りです。

#### 英語

- en-US
- en_US
- en-UK
- en_UK
- English

イギリス英語も指定しているのは本プラグインの英語ではあまり違いがないと思われたためです。

#### 日本語

- ja-JP
- ja_JP
- jp
- Japanese

これらのエイリアスは小文字や大文字が混ざっていても認識できます。

### ターゲットアイテムについて

`/target`コマンドを実行するといくつかのアイテムが手に入ります。  
このアイテムは右クリックで実行でき、ドロップで削除されます。  
アイテムは以下の通りです。

|    アイテム    |          ID          |             その他説明              |   実行されるコマンド    |
| :------------: | :------------------: | :---------------------------------: | :---------------------: |
|     犬の頭     |       AURA_BOT       |   Aura Botが対象に飛ばされます。    |    /aurabot <Target>    |
|     犬の頭     |      AURA_PANIC      | Aura Panic NPCが対象に飛ばされます。 |    /acpanic <Target>    |
|                |                      |                                     |                         |
|       矢       |       TEST_KB        | 対象に見えない矢が飛んでいきます。  |    /testkb <Target>     |
|    コンパス    |       TRACKER        |      対象にテレポートします。       |     /tpto <Target>      |
|       矢       |   TO*TARGET*<番号>   |        ページ2に飛びます。        | /target <Target> <番号> |
|      時計      |         BACK         |             戻ります。              |           ---           |
|       矢       | BACK*TOTARGET*<番号> |       ページ1に戻ります。       | /target <Target> <番号> |
|                |                      |                                     |                         |
|     リード     |         PULL         |        対象を引き寄せます。         |     /pull <Target>      |
|                |                      |                                     |                         |
| ブレイズロッド |     TARGET_STICK     |  目の前のプレイヤーを対象にします。   |    /target <Player>     |

### バグ等

バグ等は、[こちら](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues)にて受け付けております。  
見つけたら報告お願い申し上げます。

### [Issue](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues)の担当者

日本語：[ぺやんぐ](https://github.com/peyang-Celeron)

日本語校正：[れもねーど](https://github.com/lemonade19x)

英語：[Potato1682](https://github.com/Potato1682)

### 謝辞

このプラグインは以下のライブラリ・APIを使用しています。

- [brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)
- [dmulloy2/ProtocolLib](https://www.spigotmc.org/resources/1997/) [(\*)](https://dev.bukkit.org/projects/protocollib)
- [jedk1/BookUtil.java](https://www.spigotmc.org/threads/131549/)
- [DarkBlade12/ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)
- [P2P-Develop/PeyangSuperLibrary](https://github.com/P2P-Develop/PeyangSuperLibrary)
- [PhantomUnicorns](https://stackoverflow.com/users/6727559/phantomunicorns)
- [Matrix API](https://matrix.rip/) [\(\*\)](https://www.mc-market.org/resources/13999/)
