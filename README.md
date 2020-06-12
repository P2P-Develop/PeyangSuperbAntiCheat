# PeyangSuperbAntiCheat
Bukkitのプラグインだと思います。
1.12.2で動作確認/絶賛開発中です(多分)
...

Bukkitサーバなどで使用できる、**ハッキングレポート管理** / **ハッキングテスト**プラグインです。

## インストール方

+ 適当にDLシます  
+ [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)[(\*)](https://dev.bukkit.org/projects/protocollib) を /path/to/plugins にぶちこみます。  
+ このプラグインをぶちこみます。
+ reloadやらなんやらで適用してください。

## コマンド

### /wdr

言わずと知れた某有名系Hypixelの**~~パクリ~~**コマンドです。

#### 説明

「こんなものをつかってるぷれいやーがいるよ！みてみて！」
って言うコマンドです。(雑)

##### 運営側サイド
プレイヤーからのレポートは、2形式で表示されます。  
「\[PeyangSuperbAntiCheat\] プレイヤーがレポートを提出しました！クリックしてレポートを確認してください！ \[CLICK\]」  
と  
「\[STAFF\] \[ADMIN\] Fishy: Report of \<PlayerName\> \<理由1\>, \[理由2\]...」  
の形で表示されます。コンフィグを編集することにより、変更できます。

このモードは、█████によって**Leakされた**Mod 「Lynx」との互換性をもたせるモードです。  
このModは、(某有名系)Hypixelから既に禁止されているModであり、このModを使用したまま、Hypixelサーバには、**絶対に**行かないでください。

#### 使用法
+ /wdr \<PlayerName\>
この状態で実行すると、本が開くはずです。
この本の文字をクリックすると、理由が追加されます。

**緑太字**のやつ(?)をクリックで送信、
**赤太字**のやつ(?)をクリックで破棄します。

**注意: このプラグインでは超原始的な本の実装方法を使用しております。(まぁ実際某有名系Hypixelで使われてますしおすし**

+ /wdr \<PlayerName\> \<理由1\> \[理由2\]...
これは、本を開かずにそのまま理由を**ぶちこんで**レポートする方法です。
本を開かないため、**意味を感じられませんが**、コンソールからもレポートできます。
#### レポートに使用できる理由一覧
##### 本で表示される順に書きます。多分。
| 理由 | エイリアス | 簡単な説明 |
|:-:|:-:|-|
| Fly | flight | プレイヤーが、空飛んだりするやつです |
| KillAura | killaura, aura, ka | 後ろにいる人を殺したり、範囲に入った人を一気に殺したりできます。 |
| AutoClicker | autoclicker, ac, autoclick | 自動攻撃ツールや、マウス等のマクロのことです。 |
| Speed | speed, bhop, timer | ありえない速度で走ったりすることです。BhopやTimerはここで検出されます。 |
| AntiKnockback | akb, velocity, antikb | 殴られたり、攻撃を受けても一切ノックバックしないやつです。Velocityなどもここ。 |
| Reach | reach | 攻撃できる距離を伸ばすやつです。 |
| Dolphin | dolphin | よくわからん。情報求む |

###### 同じ人から同じ人へのレポートはできないようになっています。レポートスパム防止です。

#### 権限
``` psr.report ``` です。
全員持ってる権限です。

### /aurabot

#### 説明
このコマンドを実行すると、プレイヤーの周りに、プレイヤーの周囲を回るNPCを、召喚します。  
このNPCは、特定の速度でプレイヤーの周りを回り、特定の回数叩かれた場合、プレイヤーをキックします。  

#### 使用法
##### /aurabot \<PlayerName\>
\<PlayerName\> に指定されたプレイヤーに、回るNPCを召喚します。
#### 権限
``` psr.mod ``` です。  


### /acpanic
#### 説明
このコマンドを実効すると、プレイヤーの背後にへばりつくようにNPCを指定された秒数召喚します。  
このNPCは、プレイヤーの後ろにいるように設定しているため、KillAuraを使用していない限り、連続して見ることは厳しいです。  
KillAuraでは、後ろのプレイヤーも殴る設定があるため、BlatantModeなどを入れていた場合、常にこのNPCを見続けようとします。  
その結果、通常ではありえない挙動になり、KillAuraを使用しているプレイヤーを検出することができます。  
#### 使用法
##### /acpanic \<PlayerName\> \[seconds\]
\<PlayerName\> に指定されたプレイヤーに \[seconds\] で指定された秒数、背後にNPCを召喚します。
#### 権限
``` psr.mod ``` です。

### /peyangsuperbanticheat
#### Alias
+ /psac
+ /psr
等  

#### 説明
このプラグインのメインコマンドです。引数をつけることによって動作します。

#### 引数一覧

##### /psac help
このプラグインコマンドのヘルプを表示します。
``` psac.mod ``` または ``` psac.admin ``` 権限を持っている人には、以下のヘルプも表示されます。

##### /psac view \[ページ数\]
プレイヤーが提出したレポートを確認することができます。  
脅威度順に5件ずつ表示されます。  

##### /psac show \<管理ID\>
プレイヤーが提出したレポートの詳細を表示します。  
このコマンドをプレイヤーから実行すると、本が開き、レポートの詳細を確認することができます。  
コンソールから実行した場合、コンソールにそのまま表示されます。

##### /psac drop \<管理ID\>
プレイヤーが提出したレポートを破棄します。  
ログとかなんも残らずに一瞬で消えます。注意してください。

##### /psac kick \<PlayerName\> \[test\]
\<PlayerName\>で指定したプレイヤーをキックします。  
第2引数に、 ``` test ``` を指定すると、テストモードでキックされます。  

## キックについて
このプラグインでは、誤検出などのために、プレイヤーが誤BANされることを危惧しています。そのため、自動BAN/BANはこのプラグインには含まれません。  

### ブロードキャストメッセージについて
**プレイヤーがキックされるとき、以下のブロードキャストメッセージが流れます。**  

「\[PEYANG CHEAT DETECTION\] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。」  

「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」  

というメッセージがブロードキャストされます。  

このメッセージは、AntiCheatを自動検出した時のメッセージです。  
スタッフによるキックの場合は、「違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！」だけが流れます。  

### キック理由について
キックの理由は以下の通りです。

#### PEYANG ANTI CHEAT DETECTION
このプラグインが自動で検知した場合のメッセージです。
#### KICKED BY STAFF
スタッフによるkickコマンドです。
### PEYANG ANTI CHEAT TEST
このプラグインのテストです。

## NPCについて

現NPCは、[@randomapi](https://twitter.com/randomapi)によるAPI「[RandomUserGenerator](https://randomuser.me/)」を使用して、  
ランダムなユーザ名をもつプレイヤーを召喚します。  

スキンはランダムですが。現段階では、コンフィグに登録されたUUIDのスキンのみがランダム表示されます。

## コンフィグについて
このプラグインでは、以下のコンフィグを使用しています。適当に変えてください。
| 設定名 | デフォルト値 | 説明 |
|:-:|:-:|-|
| database.path | ./eye.db | レポート情報などを保存するSQLiteのデータベースの置き場所です。 |
| database.logPath | ./log.db | キック情報などを保存するSQLiteのデータベースの置き場所です。 |
| npc.seconds | 6                                                           | AuraBot(NPC)が回る時間です。 |
| npc.bump | 30.0                          | NPCが途中で落ちてしまったり、動きが止まってしまった場合などに、適度に増やす値です。 |
| npc.time | 0.2 | NPCが回る速さです。感覚で調整してください。 |
| npc.range | 2.1 | NPCが回る半径です。 |
| npc.kill | 3                             | NPCをだすkill数です。この場合では、10秒間に3Killを表します。 |
| kick.delay | 2                   | プレイヤーをキックするまでの遅延です。ブロードキャストされた瞬間から数えられます。 |
| kick.lightning | true | プレイヤーがキックされるときに(エフェクトだけの)雷を落とすかどうかです。 |
| kick.defaultKick | 40 | 学習済データが見つからないとき、この回数NPCを殴ると、Kickされます。 |
| message.lynx | true | █████によってLeakされたMod「Lynx」と互換性を保たせるかどうかです。 |
| skins | \(省略\) | NPCに適用するスキンです。この中からランダムで選ばれます。 |

# クソ雑魚ナメクジゴミAIについて
このプラグインでは、クソ雑魚ナメクジゴミAIとよばれる、AI**もどき**が出てきます。  
実際のハックを用いて学習させることにより、Kick精度が向上する… **(と思って作ったものです。**  

...

実際意味は皆無でした。

# つかってるやつ
このプラグインは、以下のライブラリ/APIを使用しています。
[brettwooldridge](https://github.com/brettwooldridge/)氏 [Hikari(光)CP](https://github.com/brettwooldridge/HikariCP)  
[dmulloy2](https://github.com/dmulloy2/)氏 [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/)[(\*)](https://dev.bukkit.org/projects/protocollib)  
[jedk1(Jed)](https://www.spigotmc.org/members/jedk1.43536/)氏 [BookUtil.java](https://www.spigotmc.org/threads/resource-bookutil-1-8-1-9.131549/)  
[DarkBlade12](https://github.com/DarkBlade12/)氏 [ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)  
