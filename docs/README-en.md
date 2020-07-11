# PeyangSuperbAntiCheat(PSAC) English Documentation

**WARNING: This repository has jokes in commit messages and source by developer(and a little contributor).  
If you want to introduce an anti-cheat plugin with high detection rate, please do not use this plugin.**  
***VERY WARNING***: **With this plugin, detectopm will run +4 timers at the same time.  
In other words, it may consume a certain amount of RAM or CPU, so please use a server with specifications.**  
For more information on the number of threads, see [Plugin Threads Summary](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/PluginThreads.txt).  
  
## Overview

Anti Cheat plugin for Bukkit / Spigot / PaperMC based server.  
It has been confirmed to work with version 1.12.2.  
  
This plugin is a **Cheat Report Management** / **Cheat Detection Test** plugin.  
  
In the description of **hack**, the meaning of hacking (cracking) of the server itself is ambiguous, so it is written as **cheat**.
  
  
### Markdown formatting (This area is not so important)

This document defines a simple markdown grammar to make it easy for users to understand.
  
**WARNING: foo is __not__ bar, please understand.**
  
**This represents information that all users should know.**
  
```tst
This is message[CLICK]
```
  
This means that the messages within the code block will send to the Minecraft chat area.
  
**IMPORTANT: foo, bar.**
  
This means important items that the user should read.
  
- /foo \<bar\> \[player\]
  
This list a represents command and argmuents.  
Argument enclosed in <>, it represents the necessary command, Enclosed in \[\] indicates an arbitrary command.

- `fooperm.bar`
  
This block of code represents a permission.  
**WARNING: No authority group is described. Before referring to permissions, make sure you understand it.**
  
---
  
## Installation

**IMPORTANT: Compiled jar files have not yet been released in this repository. If you can not prepare a compilable environment, please wait until it is released.**  

### Auto Build (Linux only)

1. Execute this command.
   maven and curl and git required.
```bash
$ bash -c "$(curl -fsSL https://raw.githubusercontent.com/peyang-Celeron/PeyangSuperbAntiCheat/master/build)"
```
2. Move / Copy ProtocolLib \([\[Spigot\]](https://www.spigotmc.org/resources/protocollib.1997/) | [\[Bukkit\]](https://dev.bukkit.org/projects/protocollib)\) to the `plugins` directory.
```bash
$ mv ProtocolLib.jar (Your plugins dir)
```
3. Move / Copy the built plugin in the `plugins` directory.
```bash
$ mv PSACBuild/target/(PSAC jar file) (Your plugins dir)
```
4. Start / Restart server.
```bash
/stop
```
or
```bash
$ java -jar server.jar
```

### Manual Build
  
1. Clone this repository.
```bash
$ git clone https://github.com/peyang-Celeron/PeyangSuperbAntiCheat.git PSACBuild
```
2. enter repository root and build in Java environment **with Maven** \([`mvn shade` is not needed](#what-is-messageyml)\).  
```bash
$ cd PSACBuild && mvn package
```
3. Move / Copy ProtocolLib \([\[Spigot\]](https://www.spigotmc.org/resources/protocollib.1997/) | [\[Bukkit\]](https://dev.bukkit.org/projects/protocollib)\) to the `plugins` directory.
```bash
$ mv ProtocolLib.jar (Your plugins dir)
```
4. Move / Copy the built plugin in the `plugins` directory.
```bash
$ mv target/(PSAC jar file) (Your plugins dir)
```
5. Start / Restart server.
```bash
/stop
```
or
```bash
$ java -jar server.jar
```
  
---
  
## Permissions

Commands are always assigned one or more premissions.  
Other settings can be done using permissions.  
  
|     Permission      |           Assigned Command           | Description                                                                                                                               | Default Value | Permission Group |
| :-----------------: | :----------------------------------: | :-----------------------------------------------------------------------------------------------------------------------------------------| :-----------: | :--------------: |
|   **psac.member**   |                 group                | This is a server member permission group.                                                                                                 |      true     |       none       |
|    `psac.report`    |          [/report](#report)          | This permission can execute report commands.  Player deprived of this permission cannot report.                                           |      true     |    psac.member   |
|    `psac.report`    |       [/psac help](#arguments)       | This permission can view help for members of this plugin.                                                                                 |      true     |    psac.member   |
| `psac.notification` |                 none                 | This permission will receive broadcast messages when other players are kicked.                                                            |      true     |    psac.member   |
|    `psac.regular`   |                 none                 | This permission can visible sended regular messages.                                                                                      |      true     |    psac.member   |
|    **psac.mod**     |                 group                | This permission can kick or test the player.                                                                                              |       op      |       none       |
|     `psac.kick`     |       [/psac kick](#arguments)       | This permission can kick player manually.                                                                                                 |       op      |     psac.mod     |
|   `psac.aurabot`    |         [/aurabot](#aurabot)         | This permission can summon [KillAura Test NPC](#aurabot).                                                                                 |       op      |     psac.mod     |
|   `psac.aurapanic`  |         [/acpanic](#acpanic)         | This permission can summon [Panic NPC](#acpanic).                                                                                         |       op      |     psac.mod     |
|    `psac.testkb`    |          [/testkb](#testkb)          | This permission can release invisible arrow to the player and check for knockback.                                                        |       op      |     psac.mod     |
|   `psac.viewnpc`    |                 none                 | This permission visible NPC other than the target player.                                                                                 |       op      |     psac.mod     |
|     `psac.view`     |       [/psac view](#arguments)       | This permission can view report information.                                                                                              |       op      |     psac.mod     |
|     `psac.show`     |       [/psac show](#arguments)       | This permission can view *verbose* report information.                                                                                    |       op      |     psac.mod     |
|     `psac.bans`     |            [/bans](#bans)            | This permission can view ban statics.                                                                                                     |       op      |     psac.mod     |
|   `psac.ntfadmin`   |                 none                 | If the player calling the NPC has this permission, when that player detects a cheat, a message will be sent indicating the player's name. |       op      |     psac.mod     |
|   `psac.reportntf`  |                 none                 | Players with this permission can be notified when the player submits a report.                                                            |       op      |     psac.mod     |
|     `psac.pull`     |            [/pull](#pull)            | This permission can pull other players.                                                                                                   |       op      |     psac.mod     |
|  `psac.chattarget`  |                 none                 | A mark will be added to the left of the chat for players with this permission.                                                            |       op      |     psac.mod     |
|     `psac.mods`     |                 none                 | This permission can see the mods that other players have introduced.                                                                      |       op      |     psac.mod     |
|   **psac.admin**    |                 group                | This permission can use all commands of the plugin.                                                                                       |      false    |       none       |
|     `psac.drop`     |       [/psac drop](#arguments)       | This permission can delete submitted report.                                                                                              |      false    |    psac.admin    |
|     `psac.error`    |                 none                 | This permission can get error information when the plugin encountered an internal error.                                                  |      false    |    psac.admin    |
  
---
  
## Commands

This section are describe plugin commands.

## /report

### Aliases

- /peyangreport
- /pcr
- /rep
- /wdr

### Description

Send the content of the report selected and submitted by the player to the staff.  
**WARNING: It is not a command to automatically summon NPC.**  
The staff can see if the player is doing the same as the report.  
User can also set the format for the report.  
  
```tst
[PeyangSuperbAntiCheat] プレイヤーがレポートを提出しました！クリックしてレポートを確認してください！ [CLICK]
```  
  
Staff can check the contents of the report with the `[CLICK]` button.  
  
```tst
[STAFF] [ADMIN] Fishy: Report of <PlayerName> <Reason1>, [Reason2]...
```  
  
This report format is lets them know who reported who and why.  
**WARNING: This report format is compatible with the Hypixel Lynx Mod (Keep leaking users down).  
This mod may be Bannable on Hypixel server, so never use this on Hypixel server.  
[Developer](https://github.com/peyang-Celeron) does not take any responsibility.**  

### Usages

- /report \<PlayerName\>  
  Player can execute this command with this argument to open a book where you can select the reason for the report.  
  If you click on the reporting reason displayed in the book, the reason will be added as the content of the report.  
  
- ![#008000](https://via.placeholder.com/15/008000/000000?text=+) Click to send report in "レポートを送信" , or
- ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) Click the "レポートをキャンセル" to discard.
  
- /report \<PlayerName\> \<Reason1\> \[Reason2\]...  
  Player can execute this command with this argument to report directly in chat/console without using a book.  
  **Can use an alias for this reason. Please read below.**  

### Reasons

The books are sorted in the order they are displayed.

|    Reason     |          Aliases           | Description                                                                                         |
| :-----------: | :------------------------: | :-------------------------------------------------------------------------------------------------- |
|      Fly      |           flight           | Fly without creative mode.                                                                          |
|   KillAura    |     killaura, aura, ka     | Attack entity without aiming.                                                                       |
|  AutoClicker  | autoclicker, ac, autoclick | Click Entity/Block automatically(External software clickers and macros also belong to AutoClicker). |
|     Speed     |     speed, bhop, timer     | Run at a impossible speed(Bunny hops and Timer belong to speed).                                    |
| AntiKnockback |   akb, velocity, antikb    | Never be knocked back.                                                                              |
|     Reach     |           reach            | Extend the attack distance.                                                                         |
|    Dolphin    |          dolphin           | Swiming automatically like a dolphin.                                                               |

##### To avoid reporting spam, the same player cannot report to the same player.

### Permission

- `psac.report`
  
## /aurabot

### Aliases

- /testaura
- /auratest
- /killauratest

### Description

Executing this command will summon an NPC that spins around the player at a constant speed.  
When an NPC is attacked a certain number of times, it kicks that player.  

### Usage

- /aurabot \<PlayerName\>
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\>.  

### Permission

- `psac.aurabot`
  
Manages the permission to execute the summon commands of Aurabot and Watchdog.  
Players with this permission can summon Watchdogs.  
  
## /acpanic

### Aliases

- /testpanic
- /panictest
- /aurapanictest

### Description

This command always summons the NPC that is trying to move behind the player.  
When an NPC is attacked a certain number of times, it kicks that player.  

### Usage

- /acpanic \<PlayerName\>
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\>.  

### Permission

- `psac.aurapanic`
  
## /testkb

### Aliases

- /testknockback
- /kbtest
- /knockbacktest

### Description

Fire a **invisible arrow** at the specified player.  
This allows you to see if the player is knocking back.

### Usage

- /testkb \<PlayerName\>

Fire invisible arrow at \<PlayerName\>.

### Permission

- `psac.testkb`
  
## /pull

Pull the specified player.

### Aliases

- /pul

### Description

Pull the specified player to the executed player.
It cannot be run from console.

### Usage

- /pull \<PlayerName\>

### Permission

- `psac.pull`
  
## /bans

### Aliases

- /banlist
- /playerbans
- /banlookup

### Description

Displays the player's kick (BAN) history **remaining in this plugin**.

### Usage

- /bans \[\-a | ban | kick\] \<PlayerName\>

Displays the BAN history of player specified by \<PlayerName\>.  
Add \-a to show all bans and kicks.  

### Permission

- `psac.bans`
  
## /mods

Displays the mods installed by the specified player.

### Description

If the mod loader is Forge, can view the mods installed by the player.  
The response is returned as the Mod ID.  

### Usage

- /mods \<PlayerName\>

Displays the mods of the player specified by \<PlayerName\>.

### Permission

- `psac.mods`

**WARNING: This command uses `psac.mods` permissions. Not `psac.mod`.**
  
## /target

Tracks the specified player as a target.

### Description

Executing this command gives utility items.  
These items allow you to execute useful commands with a click.  
Dropping a gived item, clears all items.  

### Usage

- /target \<PlayerName\>

Start tracking \<PlayerName\>.

### Permission

- `psac.target`
  
## /psac

### Aliases

- /peyangsuperbanticheat
- /psr
- /wdadmin
- /anticheat

### Description

The main command of this plugin. It works by adding an argument.

### Arguments

#### /psac help

Displays help for this plugin command.  
**Commands related to management ID can be used in `psac.mod`, but they are not shown in help.**  
Players with `psac.mod` or `psac.admin` permissions will also see the following help:  

#### /psac view \[Pages\]

See the report submitted by player.  
The reports are sorted by highest risk, five at a time.  

#### /psac show \<ManagementID\>

View details of the report sent by player.  
You can run this command from the player to view the report details by book.  
If you run it from the console, it will appear as a log in the console.  

#### /psac drop \<ManagementID\>

**Completely** discards the reports sent by the player, except the command execution log.  
**The log of the deletion itself is not displayed. Be careful when discarding.**  

#### /psac kick \<PlayerName\> \[test\]

Kick player specified by \<PlayerName\>.  
Specifying \[test\] as the second argument kick player in test mode.  

### What is \<ManagementID\>

\<ManagementID\> is a 32-character alphanumeric string that is automatically assigned when the player submits the report.  
This ID is displayed when you run `/psac view` from console.  
Also, can execute commands related to the \<ManagementID\> from the player.  

### Why not execute BAN command in this plugin

The plugin is concerned about falsely banning players due to false Watchdog detection.  
Therefore, the plugin does not ban players _automatically_.  

### Bloadcast Messages

**The following broadcast message will be played when the player is kicked.**  
  
```tst
[PEYANG CHEAT DETECTION] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。
```
  
```tst
違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！
```
  
This message is sent when the watchdog automatically detects a cheat.  
For staff kicks, you will only broadcast secondary message.
  
### Kick reasons
  
There are three types of kick reasons:
  
#### PEYANG CHEAT DETECTION
  
This is the message when this plugin automatically detects cheats.
  
#### KICKED BY STAFF
  
This message is displayed when a staff member issues a [kick command](#psac-kick-playername-test).
  
#### PEYANG ANTI CHEAT TEST
  
A test message for this plugin. Please use it for testing.
  
### What is this NPC\(WatchDog\)

The Watchdog calls the NPC with a random username using the API "[RandomUserGenerator](https://randomuser.me/)" by [@randomapi](https://twitter.com/randomapi).    
The NPC skin is displayed randomly by referring to the UUID skin settting.  

---

## Config settings

In this plugin, the following config is set by default.
  
|     Setting name    | Default value | Description                                                                                                           |
| :-----------------: | :-----------: | :-------------------------------------------------------------------------------------------------------------------- |
|    database.path    |   ./eye.db    | Save report information by specifying location of SQLite database path.                                               |
|   database.logPath  |   ./log.db    | Save kick infomation by specifying location of SQLite database path.                                                  |
|     npc.seconds     |       4       | Specifies the number of seconds the [NPC](#aurabot) will orbit the player.                                            |
|       npc.time      |      0.3      | Specifies the value of [NPC](#aurabot) orbit speed.                                                                   |
|      npc.range      |      2.1      | Specifies the radius that the NPC will rotate. The default distance is suitable for KillAura detection.               |
|    npc.panicRange   |      1.5      | Specifies the relative height of the [Panic NPC](#acpanic) and player.                                                |
|       npc.wave      |      true     | Whether the [NPC](#aurabot) spins like a wave.                                                                        |
|     npc.waveMin     |      1.0      | The minimum radius that the [NPC](#aurabot) orbits like a wave.                                                       |
|    npc.speed.wave   |     true      | Specify whether to make the orbital velocity of NPC variable.                                                         |
| npc.speed.waveRange |     0.03      | Specify the speed change range.                                                                                       |
|       npc.kill      |       3       | Specifies the maximum number to call when an NPC is killed within 10 seconds.                                         |
|      kick.delay     |       2       | Specifies the delay between sending a broadcast message and kicking the player.                                       |
|    kick.lightning   |     true      | Specifies whether to drop lightning effect\(no damage\) when kicking.                                                 |
|   kick.defaultKick  |      40       | Kick if the NPC is attacked above this value. This value takes precedence if no learned data is found.                |
|     message.lynx    |     true      | Specifies whether Lynx Mod compatible.                                                                                |
| autoMessage.enabled |     true      | Toggle the presence or absence of regular messages.                                                                   |
|   autoMessage.time  |      15       | Specify a minuites for recurring messages.                                                                            |
|        skins        |   \(UUID\)    | Specifies the skin to apply to the NPC.<br>You can specify multiple UUIDs and it will be selected from a random UUID. |
  
---
  
## What is learning function?

This plugin has a learning function that automatically adjusts the parameters using the actual cheat material.  
Learning cheat data can improve the accuracy of your decision to kick or not.  
**This feature is under development. Please note that this function cannot learn completely.**  

### Learning mechanism

The learning feature of this plugin adjusts key parameters by iteratively calculating the average of the parameters when it detects a cheat or kick.

## What is *message.yml*?

When you build PeyangSuperbAntiCheat.jar with `mvn package`, `mvn shade` is automatically executed.  
You can edit this file to change the plugin messages before building.  
At build time, *message.yml* is **automatically include jar resource**.

## Did you find any bugs or errors?

We accept bugs and errors related to [GitHub issues](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues).  
I think that it will be handled by about two people, so please feel free to post.

#### Assignees

Depending on the language of the problem, the following persons are responsible for resolution:
- Japanese issue assignee: [peyang-Celeron](https://github.com/peyang-Celeron) (and [Lemonade19x](https://github.com/Lemonade19x)?)
- English issue assignee: [Potato1682](https://github.com/Potato1682)
  
---
  
## Thanks

This plugin uses the following libraries/APIs:  

- [RandomApi/RandomUserGenerator](https://randomuser.me)
- [brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)
- dmulloy2/Protocollib [\[Spigot\]](https://www.spigotmc.org/resources/protocollib.1997/) | [\[Bukkit\]](https://dev.bukkit.org/projects/protocollib)
- [jedk1/BookUtil.java](https://www.spigotmc.org/threads/resource-bookutil-1-8-1-9.131549/)
- [DarkBlade12/ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)  
- [P2P-Develop/PeyangSuperLibrary](https://github.com/P2P-Develop/PeyangSuperLibrary)
- [PhantomUnicorns](https://stackoverflow.com/users/6727559/phantomunicorns)
  
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<a href="https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/blob/master/docs/memo/memo02.txt">)̶̡̢̡̧̡̧̢̢̡̡̨̨̡̢̧̨̞̘͖̦͈͎̰͔̯͉̻͍͎̻̙̝͖̤̤̠͎̜̗̜̞̪̞̳̳̫͇̜̜̭̣̥͍̲̝͚̟̠̺͎͍̠̩͕͉͇͔͚̙̭̱̺͚̭̣̥͕̙̪̣̰͙̲͚̙̟̲̩̗̯͇̫̬͈̜̣̝͈̗̮͕̝͈̟̬͍͚̜̗͖̭̗͍̰̫̟͎̠͇͚̫͖̹̲͔̝̱̝̜̮̲̥̼͍̯̪͙̬̩̠̦͎̫̖̰̞͇͖̘̱͍̥̟͕͔͍͉̳̤͎̠͍͖̩̼̫̞̫͎͖̟͕̻̟̬̗̰̲̦͖̬̫͍̮̟̖͚̤̝̖̥́̃͊̄̓̊̀̐́͂̌̌̽̈́͒́̍͆̀̇͊̏́͛͛̀̊̊̏̈̓́̈́̔̃̃̃͗͊̒̿̓̚͘̕̕̕̕͜͜͜͝ͅͅͅͅ.̵̡̧̡̨̢̨̡̨̨̡̢̨̧̡̨̧̧̢̨̨̡̧̡̡̧̨̡̡̛̛̛̛̛̛̛̜͓̭͕̗͍̱̼͔̦̹̼͉͔̜͎̩͖̤̮͎͙̙̹̗͙͇͓̱̱̝̣̭̟̭̙̻̝͚̖̻͙̫̠̰̠͖̮̞̱̱̗̺̺̗̞̝̦̖̮͉͇̱̗͎̪̫͎͔͔̝͖̮̤̖͚͔̜̞̝͕̬̱͈̱̦̩̙̱̗̦̼̺͇̭̤͈̞̳͍̤̭̟̫͙̞̰̹̪̱͈̱̺̣̫͇͔̙͕̹̱͉̝̙̙͙̹͉̥̺̜̲̮̳̯̯̖͈͓̰̥̙̻̹̳̘͈̗̺͖̬͍̘̘̦͕̫͉͚̹̲͖̫̯̙̠͉͍̰̫͎̼̫̻͖̖̺̰̥͖͎̦͓͇̮͚͖̳̻̻̭̻̜̥̥͈̤̥͇̺̙̬̜̞̖̗̻̺͓̺̙̯̯̰̯̱͇̰̤̌̓̉̊͛̾͐͊͐͛̃̾̾͋̓̒̋̓̒̂̓̐̆̑̎̂̆͂͌̅͛̊͒̾́̑̒́̅̑̍͐̓̾͐̐̾̌̒̀́̔̑͐͌̈́͐̑̑̓̓̐̆̀͒̍̐̎̈̈͌͂͂́̀̓̊͌̅̊̏̓͌͊̐̊̌̇̑̈́̀͗͌̿̃̊͐̓̊̏̇͆́̽̂̃̅͑̌̾̔̋́́̂͑̂̓̅̍̀̋̾̑̀͊̑̓͆̓̇̅̀̂̉̈́̄͒̓́̂͂̽̃̈́͆̓̃̀̈́̅̎̓̄͋̽͗̋̏̊͐̉̀̑͑̍͋̓͂̾̅̄̊͑̄́̆̓̅́̀͑̉̋̈́͛̈́͒̽͋̄̉̉̈́̔̽̽̓́̑̂͐͌̑̌̆̎͌̄̌͒̿͋̆͒͗̔̓̅̇̏͂̆̓̇͒̈́̐́̆͂͛́͂̽̊͗̇̽̓͊̄̆̋̉̎̔̂́̑̂͗̔̉̂̈́̕̚̚͘͘͘̚͘̚̚̕͘̕͘̚͘̚̚̕͜͜͜͜͜͜͠͠͝͝͝͝͠͝͝͝͠͝͝͝͠͝ͅͅͅ/̷̧̡̡̢̡̢̢̧̨̧̡̧̧̢̡̢̧̨̧̢̡̧̧̛̛̛͈̫̳̮̩̱͈̮͚̯͖̞͕͇͇̜̠̟͉̗̘̥̪͉̠͉͖̻̰̹̯͔̘̝̻̜͙̬̪̲̭̙̮̻͚̝̹͎̥͈͍̣͎̻̘͙̜͍̻̞̺͎͍̰̙̦̞͉̠̦̞͕̩̱͉͚̭̩̝͍̠̼̳͈̰̫̫͔̯̝̲̘͉̤͙͚̙̯͉̠͎̠͉̺͈͎͕̝̟̹͇͎̘̰̤̺̘̙̖͕̫͓̳͎̪̞̼͚̭̥̘͙̭͇͔̹̯͙̝͈͇̯̲̹̬͙̰͈̣̻͔̭̦͇̙̻͇͙̖̫̹̤̘̺̤̩̜̲̰̳̞̦̹̱́̾͆͒̈́̊̽̎̒͊͌̂̏́̐̿̅́̈͛̂̈̓̍̽̑͒̀͒͒͑̆̽̎͛́͐̒́̄̒̈́̃̌̐̅̃̅͂̌̃͂̽̾̐̒͛̄̀͒̍̄͆͌̿̂͊͛̏͑̇̇͐̈́̂̉̌́͊͋̿͑̒̂͂̔̈́́͆̀̓̓̇̊̄͊̊͊̉͂̀͊̄̃̓͊̾̅̅̂̑̃̎͐̃̍͂́̈̃̌̔̽̓͊̓͋̿̈́̿̏͗̌̽̔̃͗̍̊̈́̋͐̐̄̈́̐̈́̿̋̅̓̀̍̐̈́̉̃̑̈̓̐̆̇͐̆̽̔̎̒́̽́́̌͗͛̓̂́͋͂͌͆̽̈́̿̅̑̄̉̏̈̚͘̕͘͘͘̚̚̕͜͜͜͠͝͠͝͝͝͝͠͠͝͠͠͝͝͝͠͠͝ͅͅͅͅͅͅ:̷̢̡̡̡̡̧̧̩̥͚̣̹̥͉̱̩̘̲̮̠̺̦̘̭̟̭̮͎̭̖̝͈͔̟̬̩̯͔͎̣̼̫̞͕͍̗̮̼͉̮̞̟̻͖̼̦̜̙̭̪̳̖̘̥̈͜͝ͅͅ$̶̡̢̧̡̧̧̢̨̢̢̢̨̨̢̡̡̡̡̧̨̢̢̡̡̢̢̨̢̧̡̛̛̛̛̛̛̱̬̻̮̩̪͎͇̞̰͉̦̥̖̬͎̥̹͎̰͎̤̤̺̯̺̻̖̩̪͎͇̪̬̝̯͚͇͈͔͚̯͕̹̣̳͚̫͓̩̳̦̪̝̪̗̯͎̤̪̜̞̯͈̺̜͚̲̭̜̭̤̠͇̯̪̞͔̤̝̤̖̼̟̜̺̣̦̰̮̟̼̣̤̦̻̖͍͔̰̭̖̤̺̣̤̞̖̱̣̱͕̫͉͓͔̜̟̻̪͈̰̠̞͙̗̗̼͖̫̹͈͕̠͕͉̯̰̖̭̫̤̳͎̜̙͍͚̣̖̙͚̖̠͙̰̩͚̮̼͔͓̼̹͖͖̮̟̲͎̜̫͉̖̭̞̳̥͓͖̬̞̳̩̱͔̭̭͈͕̯̝͍̼̮̝̥̹͔̱̰̬̦̥̖̩͓̘̭̘͍͕͔̯̮̻̘͔̞͇̞̪̲̝͉͓̰̳̣̺̪͈̣̮̱͖̦̹͎̟͎̖̱̥͙̪͇̘̘̝͐̆͗̒̿̈̌͆͋̒̑́̍̓́͆͒̍̒̀̑̾̄́̔̍̇̔̈́̾͛͆̑̂̄͛̒͌̈́͑͆͛̎̊̈͛̏̽͊̋̌̋̄̄̂͂̀̽̈́̌͌̎͑͊̑̀͐͗̓̾̂̎̅̓́͗̀̓͆̾̇͒̈́̃͆͂̅͛̊̍̽̃̏̀͗̃̾̌̈́̆͛̏̉́̅́̔͒̐̇̂̏̌͒̄̋̓̔͑͂̑̈̑̊͑̇̌͒̔̔͆̔̇̑̓̔́̑͒́̈͋͊̓̑̾͗͛͐̿̊̇͛̆͆̂̾̔͗̐̑̂̈͋͗͘̕̕͘͘̚͘̚̚̕̚̕͜͜͜͜͜͜͜͜͝͝͝͠͠͠͝͠͝ͅͅͅͅͅͅͅ"̵̡̧̡̡̢̡̡̧̨̢̢̨̛̜̙͖͓̯̘̼͈͉͖͎͕̪̫̭͙̼̗̺̥̫̖̫̖̠͕͎̳̳̭̫̫̝̤̞̞̰͙̯̳͙͖̩̬̰̤̯̯͍̻͇͎͈̪̳̼̫̰͎͇͇̳̙̘̜͎͚̦̞̬̜̙͇͙̤̖̮̰̘̫͉̞̩̯͈͎̞͙̣̰͇̹͕̮̥̭̻̳͎͈̖̝̲̳̭̤͇̥̘͖͉̥̦̝̞̞͚̰̻͇͂͌͌̈́͑̋̀̑̆͑͛͌͌̈́̿̋́̈́̀̓̾͐͑̂̇̈͋̀̓̐́͒̎̍̈̅͊͋́̅̂̌͊̔͂̌̾̅̈́̒͂̍̾͗̇̋̽̊͌͐́̄́͑̀͐̇͒̓̈́̂̈́̉͗̏̒̄͑̓̈́̎́̒̓̈̍͒͐͌̽͂̓͋̓͘͘̚̚̚͜͝͝͝͝͠͝͠͝ͅͅͅͅͅ1̸̨̧̨̡̛̠̞̭͖̮͍̺͔̠͕͖̪̘̫̭̩̳̻̳̠͖̪̫͉̜̜̟̯̥̲̜̥̪̹͇͔̘͍̣͇̪̰͇͕͚̭̥͙͎̹̮͚͕̲̪̲̀̿̑̋͊̓̎̓̇̌̒̇̅̓͋̈́̈́̈͆̉̓̐̄̊͗͗͐̕͘͜͜͜͜͝͠͝͝͝ͅͅ$̴̨̡̧̧̡̢̢̢̡̡̢̨̧̡̡̢̢̧̡̨̨̡̢̨̡̛̛̛̗͍̝̺̟͚͚͓͓͓͙͖̙̮͈͉̰͚̲͔̭̘̳͉̫̪̫̱͔̫̜̞̻͕̟̬̗̲͔͖̦͓̘̗͍̙̥͓̗͍͈̝̥̭̳̯̱̠͍͚͈̘͉̗̞̟͖͉͎̦̹̼̳͕͖͙͓̣͖̣͙̟̝̦͓͉̪̬͍̝̥̘͇͙̻̰͉͓̰̗̟͇̼̯̪̟̖̫̩͎͍͖̺̫̰̭͙͈͔̗̲͇̞̻̖̣͎͚̜̪̟̗̤͈͈̲̹͖͇̬̹͈͈͔͎͍̳̥̻̰̭̹̞͓͉̘̺̫̞̺͔̪̭̮̰̤̪̞̰̹̰̼̗̦̦̻̠̳͈̲̱̰̗̦̺̩̗̝̯̯̥̩̦̝̣̳̟̘̩͖̦̪̘̘̣̹̗̙̤͉̟͙̘̣̫̥̗̗͉͔̮̈̿̃͊̇̀͒̀͐͆́̀͛͆̑͆̈͐̅̏̓͆̈́̓̐͌͗̔̔̓̓̂̊̈̾́̑́͆̃̇͒̀͋̑͌́̀̅͂́̅͑́͑͊̀̈́̐́͛́̓̐̄̈́̄̉̔̆̀̾́̽͗͐̋̉́̐̀̊̀̈́͑̾̉͌̒̍̅̈́͑́͌̏̊̉̎̓̋͗̌̏̔͘͘͘͘͘͘̚̚͜͜͜͜͜͜͜͜͠͠͠͝͠͝͝͝͠ͅͅͅͅ'̶̡̨̡̢̡̧̢̢̡̧̧̡̨̢̡̧̧̢̨̨̨̢̡̧̧̛̼̳̲̝͕̱̺̱̤̙̮̲̫̰̮̱̙̪̰̫͎̝̱̘͓̤̜̜̘̱̻̦̠͓͎̘̭̤̝̳̹̯̲̘̳̠͇͈̭̯̲̙̪̠̰̰͎̯͓̙̟̭̞̣̱̩̖̙̩͍̥̫͇͉̭̘̺̠̪̼̹͕̫̦̼̗͚͎̟̫̦͈̻̘͍͇̥̰̻͕̥̞͎͖͈̼͉̖̪̻̥̬͈͙̼͓̩͍̝̜̫̘͎͉̘͓̟͕̥̻̟̫͎͉͚̳̱̮̬͈̠̪̗̟̱̬̙̞̫̯͖͚̫̖̺̝͈̺͔̬̫͍̮̗̺̼̤̝̠̬͍͙̦͍͍͚̦̩͕̻̬̮̫͍͚̣̙̞̩̫̝̠͈̘̙̞̥̱̥̣͈̖̭͉̻̬̩͉͍̮̪̙̲̯̟̥̤̺̤̰͍͖̬͔̬̣̳͍̤̠̤̝̫͍͕̠̣͆̊͗̑̄̓̆̐̀̓́̇͜͜͜͜͜͜͜͜͠͠ͅͅͅͅͅͅ^̷̛̛̛̛̛̛̛̥̮̱͉͇̝̼̝͑̊̅̈̇͐̔̎̌͊̋̀̊̈̂͒̍̈́̆̈̑̈́̑́̐͑̿̐̒̎̈́͊̏͛̒̎͌́̈͗̊̊̈́͑͒̊̃̈́́̾̿̏̃͌͋̈́̃́͗͊̄̈́̅̒͗̽͗̉̈̐̇͛̊̽̍̿̄̔̎͂̊͌͗͋͂̇̄̽̈́̽̌͒͊̾̊̾̐̀̎͂̂̾͒̓̔͊͆̈́̒͆̽̆́̂̎͗͆̊͒̅̈́̒̋̿̈́͗̅́͂͂͐͛̎̇̃͆̀̆̈͐͊̒́́̑̐̓̈́͋͂͛͆̐͛̈́̈̀̒̔̎͗̓̓͗̃̆͑͋͑̈́̊̃͂̀̐͋͆̄̌͊͋̏̃͐̉̓̓͂̈́̓͌̈́̒̈́̐́̿̂̿̐̀̿͒̈́͆̎̈́̌̏̏͋̉͋̌̀̾͛̽̈́̄̄̊̃͋́̾̎̐̑͊̆́͐͗̄͊̈́͗̄̾͑̿̀̓́̍̏̒̄͘̚̚͘͘̕̕̕̕͘̕͘͘͘͘̕̕͠͠͠͝͠͝͝͠͝͝͝͝͠͠͝\̸̧̢̧̨̨̢̡̡̧̡̢̨̢̨̡̡̧̨̨̡̛͚͕̠̰͔̹̳̞̹̖̟̠̮͇̞̩̯͇̫̺̪͖̭̩͓̝͕͎͓̮̥͓̩͍̗̻̞͇̠͙͚̖̫̖̬̜̩̪̳͔̪̳̻̦̙̬̟̩̦̮̺̯̞̼͔̯̹͙͚͕͔̮̻̞̰̠̻̙̺̲͓̟͖̳̟̝͚̭̼̞͖͈͖̖͉̟͓̙͇̥̗̥̗̗̯͓̘̙̤͚͎̯̜̪̣̦̠̗̜̹̼̣͉̦̦̞̹͍̘͈̰͉̖͓̯̖̬̖͙͇̜̥͙͔̥̘̩̹͔̲̻̭̺̫͕̭̞̖̹̗̤͍̳̻̰̮̭̞̯̖̞̰̬̘͉̫͚̥͇̥͓̫͍͙̤͙̱̳͔̫̖͔̺̥͚̙͎̗̦̮̲͍͓̺͉̲͓̊̾̔̐͂͋͗̆̀͂̅̊̂͑̎́̆̂͗̕̚͘͜͜͜͜͜͜͜͝͝ͅͅͅͅͅͅͅͅͅ-̵̢̢̧̡̧̧̢̡̢̢̧̢̢̧̢̢̡̨̨̛̛̛̛̝̖̳̗̩̝̱̗͉̟͖̜͙̹̩͖͓̘̞͇̙̬̩̮̮̯̜̦̻̤̫̗̘̖̱͔͖͇̥͓̰̞͉̯͉͕̟̳̹̹̫͚͙̺̜̗̯̪͉̥̭̼̱̺̱̬͚̮̞̤̬̗͎̯̘͔̝̪̺̪̮̩̙̦̫̲̹̤͉͍̘̠͍̟͕̫͙̫̣̤͍̰̰̬̻͉̮̦̼̦̞̹̣̥͙͕̯̦͕̙̥͕̰̱͍͕̪̯̬̝̟̻̰̹̼͇̺̦̘̮͓̰̹̥̪̟̲̠̞͎̫̯̹̫̲̪̯͚̭͈͎͖͖̼̙̥̹̬̖͍͕̙̤̩̻̦̮͔͚̱̳̜̻̝̼͓̹̬̝͚͖̰͈̤͈̫͚͉̳͙̠̟͚͛̆̽͑̒̓̀̏̌́̒̎͛̉̅̈̏̓̈́̒̔̈́̂̽̅͛̔̆̓̑̾̅̏̄̋͑̽͐̈̏̎͐̋̂̇͒͛̃̄͛͛̑̑̉̾͛͑̉́͆̑̐̾̈́͗̅̽̅̂̀̓̏͐͂̉̽̑͂͆͌̎̈́͌̒͆̍̌̎̊̄̋͑̓̎̽̂̂̓̀͛̆͗̀͋̉͋̓̉͛͒̊́̅̆́̎̆̑͊̀̈́͊̈́̆̆̀͆͛͗̐͛̉́͊̉̏͛̂̑̃̀̋͆̃̔̀̃̾͂͆͂̈́̔̔͐͗̂̒̑͛̐͋͐̈́̇̓̀̊̑͋̏̈́̂̽̋̽̉͗̈͐͆̈́͋̄̾̐̕̕̕͘͘̕̕̕̚̚̕͘̕̚͜͜͜͜͜͜͠͝͝͠͝͠͝͝͝͝͝͝͠ͅͅͅͅ=̶̨̛̛̛̛̯̪̘̜̰̱̖̯̳̻̣͖̅̏̾̓̏̓̈́̈́̿̑̄͋̈́̅̅̏͌͌̿͊̽͌̉̔͋͋̔̌̓̆͆͛̽͛̓̑̍͋́̀̏̐̋͒̄́̋̅̀̃̒̎̀̾̌̓̅͗̆́̏̑̋̈̒̆͐̎̎̌̎̓́̒͑̔̀̍̆̈́͌̿̇̅̈̆̅̀͆̋͒͛̂̑̐̈́͆̅͗̋̓̌̂̀͛̿̀̾̓̒́̅͑̌͗̎̀̏͊̌́̎͛͛̾͋̅̓̇̂̓̂͂͐̈̅̊̔͑̅͗̇̅̌͂̈́͗̀͗̑̏͒̎̾̕͘̕̕̕̕̕͘̚͘̚̕͘̚͘͝͠͠͝͝͝͠͠͝͠͝͠͝_̷̢̨̧̨̢̡̧̨̛̛̛̛̠̖̖̘̖̼͎̗̺̥͈̣̥̬̦̙̹̻̬͚̳̟̞̣̖̜͎͉̯̘̺̬͖̮̫̘̰̪̩͔̮̫̲̬̹͓̜̺͈͚̲͙͍̦͕̲̳̝̦̪͗̀͂̔̍̏̀̉̓͋̄̀̿̔͑̀̾̓̓̊̿̾͋͌̉̈́͛̍̋̎͂̍͂̑̈́̂̓̎͂̓̂́͂̌̓̈́̅̅́̌͒̃̊̎̐̈̄̈́͒͌̓̌͋̈́̂̐́̽̽̉̔̄͌͋̽̈́̇̄͂͌͑̀̋͊͋̊͊̓̈́̑̈̏̊̈́̀̅̆̐̅̊͂̑̇͆̀̃̈́͑̌͒̈́͌͒͋́̓̋͐̾̽̂̉̈́͆̆̂̉̔̆̑̇̈̐̅̄̅̎̔͊̊̍͑͐̈́͐͋̽̅́̈́͛͂́̈́͌̾̏̄͊͋̐̏́͆̑̏̍̂̕͘̚͘͘̚̕̚̕̕̕͘̚͜͜͜͝͝͝͠͝͝͠͝͝͠͝͠ͅͅͅ/̸̭̏̾̌̍̽̊͌̎̓͐͊͐̽̅̾̅͊͂̈́̔̕̕͠͝͝\̵̦͋̍͐̎̔̍́̒͒̅͆̆̋̏͆̾̈́̋́̏̋̓̎͐͛̈́̆͌͒̊̂̅̓̐̃͊̄̌͗̂̆͛̈́͌̓̈̌͂́̌̍̈́́̒̌̈́̂͌̿͛͂̂͆͐͘͘̚͘̚͘̕͝͝͠͠}̸̡̢̧̡̡̡̨̢̢̧̧̡̧̨̧̢̧̨̢̨̨̨̨̛̛̛̛͕̙͙͙̫̝͓̮͚̠̺͔̻͔̳̬̤̫̞̼̱̮̼̥̤̬̻͙̳͔͙̘̱̟̣̜̟͚͇̝͙͔͈̟̘̦̫̥̫̯̲̖̻̬̞̭̣͖̘͉͈̖̺̞͍̻̥͈͍͕͚̫̘̙̞̣̲̱͍̹̰͖͓͓͍̟̜͎͙͙̳̬̤͓̲̫͇̠̝̯̜̞̭̮̟̬̤̝̤͓̤͉͙̻̟̜̝̬͍̭͕͙͕̪͓̥̰̺͇̥̬̳̹̪̩̰̪͚̤̫̠͉̩͓͉͙̭̭̦̝͇̯͍͍̪̫̩̦̙͚̤̭̮̹̘̮̤̱͔̹̗̟̪̞̯͚̻͍̫̝͙̙̘͙̞̫͚̹̣̝̩̮̲͉͖̟̺͔̞͖͙̬̪͇̬̑̌̈́͗̽͑̿̎̍̈́̈́͂̂͌̀̄̈́̀̍̈́̎̐̓̋̏̀̉̇̓͋̉̃͂̐͗̂̋͂̅̔̉̍̇́̓̿̃́͋͆͌̀͂̃̄͒̽̾̌̂͋̀̊̀̄̑̋̽̇́̾̈̂̍͗̇̉̏̂̌̿̃̍͌͒̏̈͊̏͆͗͊̐͗̀͋̀͌̌͒̔̓͂̄̐͒͑̿̒̏͛͂̎̈́̉̅́̒͛͂̾̅̎͆̈́͑͌͂́͒̒͊̿͋͑͒͆̈́̓͛̃́̀́̉͑̽͊̈́̆̊͑̐̓͋̃̓̆̑͑́̊̐̆͗̔̑̾͒̿̍́̌̈́͛̊̐̇̈́͐̎̽̒̄̇͋̇́͌͒͆͒̉̎̑̓̃̉͆̈́̄̆͊̍͑͂̿̈́̎̆̈́̓̃͒̂͋̏͌̊̐͂͆̋̎̀͐̚̚̚̕͘͘̕̚̚͘͘͜͜͜͜͜͜͜͝͝͝͝͝͠͠͝͝͝͝͝͝͝͝͝͝͝͝ͅͅͅͅͅ{̴̨̢̧̡̧̢̧̢̡̢̨̨̨̢̢̧̧̢̨̨̢̧̡̡̧̨̡̧̧̨̨̨̧̧̛̛̛̛̛̛̛͓͖̥̰͖͉̠͖̣̣̲͚͔̻̭͍̝̣͓͎̱̠̘͖̯̻̘̘͉̪̺̰̲̺̳̤̤̻͙̤̯̮̲͈̜͉̲̙̺̠̖͚̖̥̗̰̹͖͈̱̲̤̘͈̭̘̬̰̳̦̝͕̼͚̘̝̠̹̥̯̱͖̥̺̟͙̯̖̣͓̤̗͚̳̥̰̗̲̫̝̲͎̩͓͚̘͖͓͎̜̥̟̗͙̘̞͙͉̱̭̜̤͎̙̳͖͉̮͉̰̮̝̙̩͓͎̦̟̰̼̥͎̩͓̠̦̩̻̖͚̻̤̼̥͖̠̹̜̻̪̞̜̞̺̫̗̮͓͍̟̻̙̣̯͈̤͖͖̣̗̯̦̞̝̺̣͉̳̮͖̦̦̞̫͎͇̪͔͔̻͙͕̭͖͕̲̳͓̝̺̝͍̣͍̤̩̜̭̲̲̼̪̖̬̬̗̝̳̑̔̈͊͊̾̈́̈́̐̀̈́̾̅̒̒͌̉͑́̒̑̊̀̅̌̊̎̃̏̆̄̂̇̀̈̂̿͑͛͌̈́̏̇͗̒͆̓̓͆̌̉̓̔̈́̾͒̉̍̿̀͗̌͒̓̏͒̂͆͆̈́͗͂͛͒̒̀͂̇̍́̅̉̿̑̃́͑̐̿́͒͂͊̄̈́̍̊͑̋̿̔̅͛̽͆̈́̆́̈̀̏̈́̓̈́̈̃̃͊́̈́͂͆̂̔͒́̀̉̒̈́̌̉̋̓̉̑̃̿̒̊́̓͂̍́͊̈́̔͛̆̌̽̄̐̂̌͐͌̇͋͋̆̑͐͂̾͗̽̌̅́̅̌̄̇̿̑̅͗̈̂͐͗̒͐̾͐̇̊̍̋̉̐͆̀̌͋̂͐͒̈́̇̾̊͌͊̐̇̊̈̔̅̃͑͑͂́͑̍͆̒̇̕͘͘̚̚̕̕͘͘̕̕͘̚͘͘̚͘͘͜͜͜͝͝͠͝͠͝͝͝͝͝͠͝͠͝͝͝ͅͅͅͅͅͅͅ@̸̨̢̧̢̡̢̡̢̧̡̛̛̛̛̼̞͚̞͙̪̹̫̥̙̻̘̳̮̩̠͇̙͓͕͉͍̖̝͕̥̺͖͔͙̪̱͔̳̗͓̤̳̬͉͎͇͎͔̬̱̱̹͚͍̹͙͕͔̙͍̮̘̜̖͙͉͈͙͚̯̱̰̬̺̘̳̱͚̙̲͙̹̟͈̰͕͕̗̮͍̻͇̟͈̳̼̭̹̜͚͕͉̬̏̓̋̈́̃̿̌̈́̇̔̎̒̈͐̍̎̇̀̈̐̓͛̌͒̄͛̎͋̓̈́́͑̿̈̀̑͂̏̓̀̂̄̆̋̇̒́̉͌̉͊͐̈́̋̿̈́̉͊̇̾͂͗͊͑̾̓̌̄͒͌̍̾̿͛̈́̿́̄̿̀̅͑̆̓̓̌̍́̆̇̓̈́̀͗̉͗̓̍̆̿̔͛̂̀͊̅̓̏̿̆̈̐̀̈́̑̊̿͊͂̒̀͐͌̂̽̎́͛͂͌͗̏̍̾͑̎̈́̈́̎͆͌͋̾̽͑͂̓̌̂́͗͊̑̽̿̐̋̐̔̓͂͘̕͘̕̚͘͘̚̕̕͘̕͜͜͜͜͠͝͠͝͝͝͝͠͝͠͠͝͠ͅͅͅ:̵̡̡̼̻͙͚̱̥̰͚͓̙̲̝̝̱̱̲͓͙̭̮͚̙̝̤͕̣̘̟̪̣̌̏̀͊͛̈́̅̂̂̔́̃̇͆̂̾̒͋͝ͅͅ;̸̡̢̡̨̧̧̨̧̧̢̡̧̛̛̛̛͓̦̻̮̘̪͇̙̝͍̺̦͈͎̫͍̦̳̬͓̭͈̫͙̟̬͖̫̼̮͖̙̰͚̥̹͇̠̦̥̠̦̣͖̱͉̻̤̜̦̭̰̺̮͖͎̩̥͕̭̳̝̟̺̖̥͙̬̟̪̝̺̻͎̞̗͙͍̤̥̬̻̦͎͇̯͐̀̓̇͋̈́̽͊̔͂̑̎́̀̅̀̀͒͋̋̆̒̂͐̀̎̾̆̇̏͗̎̓̊̿͌̐̽̀͋̊͒͌͛̄̎͋̏̈̂͆̄̋̔͆̅͐̑̔͋̾̉̒͛̾̀͗̓̂̋̎̿̍̈́̇̔̈́̐̍̑͒̌̈̃͗̀̋̀̍̐́͑͛̀͂̈́̊͗̎͗̀͌̿͊̂̅̇̌͆̈́̉̽̄̉͌̋͑͑̈́͒̉̒́̀̈́̅̔̊̍̂̀̾̂̓̎͛́́͂̂̿̈̃̀̌̕͘̚͘͘̚̕͜͜͝͠͝͝͠͠͠͠͝͝͠͝͝ͅ</a>
