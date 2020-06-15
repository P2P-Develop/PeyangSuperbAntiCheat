# PeyangSuperbAntiCheat(PSAC) English Documentation

**WARNING: This repository has jokes in commit messages by developer(and little contributor).  
If you want to introduce an anti-cheat plugin with high detection rate, please do not use this plugin.**  
  
Anti Cheat plugin for Bukkit / Spigot based server.  
It has been confirmed to work with version 1.12.2.  
  
This plugin is a **Cheat Report Management** / **Cheat Detection Test** plugin.  
  
In the description of **hack**, the meaning of hacking (cracking) of the server itself is ambiguous, so it is written as **cheat**.

## Installation

1. Clone this repository and build in Java environment.  
   **IMPORTANT: Compiled jar files have not yet been released in this repository. If you can not prepare a compilable environment, please wait until it is released.**
2. Move / Copy ProtocolLib \([\[Spigot\]](https://www.spigotmc.org/resources/protocollib.1997/) | [\[Bukkit\]](https://dev.bukkit.org/projects/protocollib)\) to the `plugins` folder.
3. Move / Copy the built plugin in the `plugins` folder.
4. Start / Restart server.

---

## Commands

### /report

#### Aliases
---

- /peyangreport
- /pcr
- /rep
- /wdr

#### Description
---

Send the content of the report selected and submitted by the player to the staff.  
The staff can see if the player is doing the same as the report.  
User can also set the privacy settings for the report.  
`[PeyangSuperbAntiCheat] プレイヤーがレポートを提出しました！クリックしてレポートを確認してください！ [CLICK]`  
Staff can check the contents of the report with the `[CLICK]` button.  
`\[STAFF\] \[ADMIN\] Fishy: Report of \<PlayerName\> \<Reason1\>, \[Reason2\]...`  
This report format is lets them know who reported who and why.  
This mode is compatible with the Hypixel Lynx Mod _leaked_ from █████.  
**This mod may be Bannable on Hypixel server, so never use this on Hypixel server.**  
**The developer does not take any responsibility.**  

#### Usage
---

- /report \<PlayerName\>
  Player can execute this command with this argument to open a book where you can select the reason for the report.  
  If you click on the reporting reason displayed in the book, the reason will be added as the content of the report.  

- Click to send report in "<span style="color: dark_green; font-weight: bold;">レポートを送信</span>" , or
- Click the "<span style="color: red; font-weight: bold;">レポートをキャンセル</span>" to discard.

- /report \<PlayerName\> \<Reason1\> \[Reason2\]...
  Player can execute this command with this argument to report directly in chat/console without using a book.  
  **Can use an alias for this reason. Please read below.**  

#### Reasons
---

The books are sorted in the order they are displayed.

|    Reason     |          Aliases           | Description                                                                                         |
| :-----------: | :------------------------: | --------------------------------------------------------------------------------------------------- |
|      Fly      |           flight           | Fly without creative mode.                                                                          |
|   KillAura    |     killaura, aura, ka     | Attack entity without aiming.                                                                       |
|  AutoClicker  | autoclicker, ac, autoclick | Click Entity/Block automatically(External software clickers and macros also belong to AutoClicker). |
|     Speed     |     speed, bhop, timer     | Run at a impossible speed(Bunny hops and Timer belong to speed).                                    |
| AntiKnockback |   akb, velocity, antikb    | Never be knocked back.                                                                              |
|     Reach     |           reach            | Extend the attack distance.                                                                         |
|    Dolphin    |          dolphin           | Swiming automatically like a dolphin.                                                               |

##### To avoid reporting spam, the same player cannot report to the same player.

#### Permissions
---

- `psr.report`

Manages permissions to execute report commands.  
Everyone has this permission.  
Player deprived of this permission cannot report.  

---

### /aurabot

#### Aliases
---

- /testaura
- /auratest
- /killauratest

#### Description
---

Executing this command will summon an NPC that spins around the player at a constant speed.  
When an NPC is attacked a certain number of times, it kicks that player.  

#### Usage
---

- /aurabot \<PlayerName\>
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\>.  

#### Permissions
---

- `psr.mod`
- `psr.admin`
  
Manages the permission to execute the summon commands of Aurabot and Watchdog.  
Players with this permission can summon Watchdogs.  

---

### /acpanic

#### Aliases
---

- /testpanic
- /panictest
- /aurapanictest

#### Description
---

This command always summons the NPC that is trying to move behind the player.  
When an NPC is attacked a certain number of times, it kicks that player.  

#### Usage
---

- /acpanic \<PlayerName\>
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\>.  

#### Permissions
---

- `psr.mod`
- `psr.admin`

---

### /bans

#### Aliases
---

- /banlist
- /playerbans
- /banlookup

#### Description
---

Displays the player's kick (BAN) history **remaining in this plugin**.

#### Usage
---

- /bans \[\-a | ban | kick\] \<PlayerName\>

Displays the BAN history of player specified by \<PlayerName\>.  
Add \-a to show all bans and kicks.  

#### Permissions
---

- `psr.mod`
- `psr.admin`

---

### /psac

#### Aliases
---

- /peyangsuperbanticheat
- /psr
- /wdadmin
- /anticheat

#### Description
---

The main command of this plugin. It works by adding an argument.

#### Arguments
---

##### /psac help

Displays help for this plugin command.  
**Commands related to management ID can be used in `psac.mod`, but they are not shown in help.**  
Players with `psac.mod` or `psac.admin` privileges will also see the following help:  

##### /psac view \[Page\]

See the report submitted by player.  
The reports are sorted by highest risk, five at a time.  

##### /psac show \<ManagementID\>

View details of the report sent by player.  
You can run this command from the player to view the report details by book.  
If you run it from the console, it will appear as a log in the console.  

##### /psac drop \<ManagementID\>

**Completely** discards the reports sent by the player, except the command execution log.  
**The log of the deletion itself is not displayed. Be careful when discarding.**  

##### /psac kick \<PlayerName\> \[test\]

Kick player specified by \<PlayerName\>.  
Specifying \[test\] as the second argument kick player in test mode.  

#### What is \<ManagementID\>

\<ManagementID\> is a 32-character alphanumeric string that is automatically assigned when the player submits the report.  
This ID is displayed when you run `/psr view` from console.  
Also, can execute commands related to the \<ManagementID\> from the player.  

## Why not execute BAN command in this plugin

The plugin is concerned about falsely banning players due to false Watchdog detection.  
Therefore, the plugin does not ban players _automatically_.  

### Bloadcast Messages

**The following broadcast message will be played when the player is kicked.**  
  
`[PEYANG CHEAT DETECTION] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。`  
`違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！`  
  
This message is sent when the watchdog automatically detects a cheat.  
For staff kicks, you will only see the message: `違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！`  

### Kick reasons
  
There are three types of kick reasons:
  
#### PEYANG CHEAT DETECTION
  
This is the message when this plugin automatically detects cheats.
  
#### KICKED BY STAFF
  
This message is displayed when a staff member issues a [kick command](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/README-en.md#psac-kick-playername-test).
  
#### PEYANG ANTI CHEAT TEST
  
A test message for this plugin. Please use it for testing.
  
## What is this NPC\(WatchDog\)

This NPC uses the API "[RandomUserGenerator](https://randomuser.me/)" by [@randomapi](https://twitter.com/randomapi) to call the player with a random username to interact with.  
The skin is displayed randomly by referring to the UUID skin settting.  

## Config settings

In this plugin, the following config is set by default.
  
|   Setting name   | Default value | Description                                                                                                                                                |
| :--------------: | :-----------: | ----------------------------------------------------------------------------------------------------------------------------------------------------------|
|  database.path   |   ./eye.db    | Save report information by specifying location of SQLite database path.                                                                                    |
| database.logPath |   ./log.db    | Save kick infomation by specifying location of SQLite database path.                                                                                      |
|   npc.seconds    |       6       | Specifies the number of seconds the NPC will orbit the player.                                                                                            |
|     npc.bump     |     30.0      | Specifies the value to resolve when the NPC is stuck or stopped in the middle.                                                                            |
|     npc.time     |     0.25      | Specifies the value of NPC orbit speed.                                                                                                                    |
|    npc.range     |      2.1      | Specifies the radius that the NPC will rotate. The default distance is suitable for KillAura detection.                                                    |
|  npc.panicRange  |      1.5      | Specifies the relative height of the [Panic NPC](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/README-en.md#acpanic) and player.  |
|     npc.kill     |       3       | Specifies the maximum number to call when an NPC is killed within 10 seconds.                                                                              |
|    kick.delay    |       2       | Specifies the delay between sending a broadcast message and kicking the player.                                                                            |
|  kick.lightning  |     true      | Specifies whether to drop lightning effect\(no damage\) when kicking.                                                                                      |
| kick.defaultKick |      40       | Kick if the NPC is attacked above this value. This value takes precedence if no learned data is found.                                                    |
|   message.lynx   |     true      | Specifies whether Lynx Mod compatible.                                                                                                                    |
|      skins       |   \(UUID\)    | Specifies the skin to apply to the NPC.<br>You can specify multiple UUIDs and it will be selected from a random UUID. |

## What is learning function

This plugin has a learning function that automatically adjusts the parameters using the actual cheat material.  
Learning cheat data can improve the accuracy of your decision to kick or not.  
**This feature is under development. Please note that this function cannot learn completely.**  

### Learning mechanism

The learning feature of this plugin adjusts key parameters by iteratively calculating the average of the parameters when it detects a cheat or kick.

---

# Thanks

This plugin uses the following libraries/APIs:  

- [RandomApi/RandomUserGenerator](https://randomuser.me)
- [brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)
- dmulloy2/Protocollib [\[Spigot\]](https://www.spigotmc.org/resources/protocollib.1997/) | [\[Bukkit\]](https://dev.bukkit.org/projects/protocollib)
- [jedk1/BookUtil.java](https://www.spigotmc.org/threads/resource-bookutil-1-8-1-9.131549/)
- [DarkBlade12/ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)
