# PeyangSuperbAntiCheat(PSAC) English Documentation
  
[Overview](#overview) | [Installation](#installation) | [Permissions](#permissions) | [Commands](#commands) | [Config settings](#config-settings) | [BungeeCord](BUNGEE-en.md) | [FAQ](#what-is-this-npcwatchdog)
  
> **WARNING: This repository has jokes in commit messages and source by a developer(and a little contributor).  
> If you want to introduce an anti-cheat plugin with high detection rate, please do not use this plugin.**  
  
> ***VERY WARNING***: **With this plugin, DetectOPM will run +4 timers at the same time.  
> In other words, it may consume a certain amount of RAM or CPU, so please use a server with specifications.**  
> For more information on the number of threads, see [Plugin Threads Summary](PluginThreads.txt).

## Overview

AntiCheat plugin for Bukkit / Spigot / PaperMC based server.  
It has been confirmed to work with version 1.12.2.  
  
This plugin is a **Cheat Report Management** / **Cheat Detection Test** plugin.  
  
In the description of **hack**, the meaning of hacking (cracking) of the server itself is ambiguous, so it is written as **cheat**.  

### Markdown formatting (This area is not so important)

This document defines a simple markdown grammar to make it easy for users to understand.  
  
> **WARNING: foo is __not__ bar, please understand.**  

**This represents information that all users should know.**  
  
```tst
This is message[CLICK]
```

This means that the messages within the code block will send to the Minecraft chat area.  
  
> **NOTE: foo and bar.**  

This means important items that the user should read.  
  
- /foo \<bar\> \[player\]

This list a represents command and arguments.  
Argument enclosed in \<\>, it represents the necessary command, Enclosed in \[\] indicates an arbitrary command.  
  
- `fooperm.bar`

This block of code represents a permission.  
  
> **NOTE: No permission group is described. Before referring to permissions, make sure you understand it.**  

---

## Installation

### Download binary

1. Download binary jar file from [releases](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/releases).
2. Move / Copy plugin in the `plugins` directory.

for bash command:

```bash
$ curl -sL "https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/releases/download/0.3a/PSAC.jar" -o (Your plugins dir)
```

### Auto Build (Linux only)

1. Execute this command.
   **maven**, **curl**, **make** and **git** required.

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

2. enter repository root and build in Java environment **with Maven** \([`mvn shade` is not needed](#what-is-yaml-resources-filesrcmainresources)\).

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

Commands are always assigned one or more permissions.  
Other settings can be done using permissions.

|      Permission     |     Assigned Command     | Description                                                                                                                               | Default Value | Permission Group |
| :-----------------: | :----------------------: | :---------------------------------------------------------------------------------------------------------------------------------------- | :-----------: | :--------------: |
|  ***psac.member***  |           group          | This is a server member permission group.                                                                                                 |      true     |       none       |
|    `psac.report`    |    [/report](#report)    | This permission can execute report commands.  Player deprived of this permission cannot report.                                           |      true     |    psac.member   |
|    `psac.report`    | [/psac help](#arguments) | This permission can view help for members of this plugin.                                                                                 |      true     |    psac.member   |
| `psac.notification` |           none           | This permission will receive broadcast messages when other players are kicked.                                                            |      true     |    psac.member   |
|    `psac.regular`   |           none           | This permission can visible sent regular messages.                                                                                        |      true     |    psac.member   |
|   ***psac.mod***    |           group          | This permission can kick or test the player.                                                                                              |       op      |       none       |
|     `psac.kick`     | [/psac kick](#arguments) | This permission can kick player manually.                                                                                                 |       op      |     psac.mod     |
|    `psac.aurabot`   |   [/aurabot](#aurabot)   | This permission can summon [KillAura Test NPC](#aurabot).                                                                                 |       op      |     psac.mod     |
|   `psac.aurapanic`  |   [/acpanic](#acpanic)   | This permission can summon [Panic NPC](#acpanic).                                                                                         |       op      |     psac.mod     |
|    `psac.testkb`    |    [/testkb](#testkb)    | This permission can release invisible arrow to the player and check for knockback.                                                        |       op      |     psac.mod     |
|    `psac.viewnpc`   |           none           | This permission visible NPC other than the target player.                                                                                 |       op      |     psac.mod     |
|     `psac.view`     | [/psac view](#arguments) | This permission can view report information.                                                                                              |       op      |     psac.mod     |
|     `psac.show`     | [/psac show](#arguments) | This permission can view *verbose* report information.                                                                                    |       op      |     psac.mod     |
|     `psac.bans`     |      [/bans](#bans)      | This permission can view ban statics.                                                                                                     |       op      |     psac.mod     |
|   `psac.ntfadmin`   |           none           | If the player calling the NPC has this permission, when that player detects a cheat, a message will be sent indicating the player's name. |       op      |     psac.mod     |
|   `psac.reportntf`  |           none           | Players with this permission can be notified when the player submits a report.                                                            |       op      |     psac.mod     |
|     `psac.pull`     |      [/pull](#pull)      | This permission can pull other players.                                                                                                   |       op      |     psac.mod     |
|  `psac.chattarget`  |           none           | A mark will be added to the left of the chat for players with this permission.                                                            |       op      |     psac.mod     |
|   `psac.userinfo`   |  [/userinfo](#userinfo)  | This permission can see the player information. If `message.lynx` enabled, add some information.                                          |       op      |     psac.mod     |
|  ***psac.admin***   |           group          | This permission can use all commands of the plugin.                                                                                       |     false     |       none       |
|     `psac.drop`     | [/psac drop](#arguments) | This permission can delete submitted report.                                                                                              |     false     |    psac.admin    |
|     `psac.error`    |           none           | This permission can get error information when the plugin encountered an internal error.                                                  |     false     |    psac.admin    |
|     `psac.trust`    |     [/trust](#trust)     | This permission can add and execute Scan / Kick / Test with trusted players.                                                              |     false     |    psac.admin    |

---

## Commands

This section are describe plugin commands.

## /report

### Aliases

- /peyangreport
- /pcr
- /rep
- /wdr
- /watchdogreport

### Description

Send the content of the report selected and submitted by the player to the staff.  

> **WARNING: It is not a command to automatically summon NPC.**

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
  
> **WARNING: This report format is compatible with the Hypixel Lynx Mod (Keep leaking users down).  
> This mod may be bannable on Hypixel server, so never use this on Hypixel server.  
> [Developer](https://github.com/peyang-Celeron) does not take any responsibility.**

### Usages

- /report \<PlayerName\>  
  Player can execute this command with this argument to open a book where you can select the reason for the report.  
  If you click on the reporting reason displayed in the book, the reason will be added as the content of the report.

- ![#008000](https://via.placeholder.com/15/008000/000000?text=+) Click to send report in "レポートを送信" , or:

- ![#ff0000](https://via.placeholder.com/15/ff0000/000000?text=+) Click the "レポートをキャンセル" to discard.

- /report \<PlayerName\> \<Reason1\> \[Reason2\]...  
  Player can execute this command with this argument to report directly in chat/console without using a book.  
  **Can use an alias for this reason. Please read below.**

### Reasons

The books are sorted in the order they are displayed.

|     Reason    |           Aliases          | Description                                                                                         |
| :-----------: | :------------------------: | :-------------------------------------------------------------------------------------------------- |
|      Fly      |           flight           | Fly without creative mode.                                                                          |
|    KillAura   |     killaura, aura, ka     | Attack to entity without aiming.                                                                    |
|  AutoClicker  | autoclicker, ac, autoclick | Click Entity/Block automatically(External software clickers and macros also belong to AutoClicker). |
|     Speed     |     speed, bhop, timer     | Run at a impossible speed(Bunny hops and Timer belong to speed).                                    |
| AntiKnockback |    akb, velocity, antikb   | Never be knocked back.                                                                              |
|     Reach     |            reach           | Extend the attack distance.                                                                         |
|    Dolphin    |           dolphin          | Swimming automatically like a dolphin.                                                               |

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
Also, to use the reach mode, adds the "-r" argument to the first or second argument.  
Reach mode can scan the radius and check the reach.

### Usage

- /aurabot \<PlayerName\>  
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\>.

- /aurabot \<PlayerName\> \[-r\]  
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\> in reach mode.

### Permission

- `psac.aurabot`

Manages the permission to execute the command on summoning of AuraBot.  
Players with this permission can summon Watchdogs.

## /acpanic

### Aliases

- /testpanic
- /panictest
- /aurapanictest

### Description

This command always summons the NPC that is trying to move behind the player.  
When an NPC is attacked a certain number of times, it kicks that player.  
Also, to use the reach mode, adds the "-r" argument to the first or second argument.

### Usage

- /acpanic \<PlayerName\>  
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\>.

- /acpanic \<PlayerName\> \[-r\]  
  Summon the NPC that performs the above actions to the player specified by \<PlayerName\> in reach mode.

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
  Fire an invisible arrow at \<PlayerName\>.

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
  Pull \<PlayerName\> to the executed player.

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

## /target

Tracks the specified player as a target.

### Description

Executing this command gives utility items.  
These items allow you to execute useful commands with a click.  
Dropping a given item, clears all items.

### Usage

- /target \<PlayerName\>  
  Start tracking \<PlayerName\> with utility items.

### Permission

- `psac.target`

## /tracking

Tracks the specified player as a target without utility items.  
  
> **NOTE: /target give utility items, but /track does not.**

### Alias

- /track

### Description

Tracks the specified player.  
If tracking is currently started and no argument is specified, tracking will be stopped.

### Usages

- /tracking \[PlayerName\]  
  Start tracking \[PlayerName\].

- /tracking  
  Stop track if tracking.

### Permission

- `psac.tracking`

## /trust

Trust specified player.

## Alias

- /noscan
- /trustplayer

### Description

Adds the specified player as a "trusted player".  
If already trusted, can remove player from "trusted player".  
Players without the `psac.trust` cannot run the following commands on trusted players.

- [/aurabot](#aurabot)
- [/acpanic](#acpanic)
- [/testkb](#testkb)
- [/pull](#pull)
- [/target](#target)
- [/track](#tracking)
- [/psac kick](#arguments)

### Usage

- /trust \<PlayerName\>  
  Add / Remove \<PlayerName\> into "trusted player".

### Permission

- `psac.trust`

## /userinfo

Lists player information.

### Description

Lists the information of the specified player.  
If you add "-f" to the argument, more information (such as rank) will be displayed if "message.lynx" is enabled.  
This command was created to be consistent with Lynx, but states that [main developer](https://github.com/peyang-Celeron) don't need it.  
If `-f` is not specified or is not available, concise information is displayed.

### Usage

- /userinfo \[f\] \<PlayerName\>
  Displays information about \<PlayerName\>.
  If \[-f\] is specified, more detailed information will be displayed.

### Permission

- `psac.userinfo`

## /silentteleport

Causes the specified player to teleport you or the specified player.  
Used for utility item internal commands in the [/target](#target).  
It also suppresses broadcast messages on servers that do not have Essentials installed.

### Aliases

- /stp
- /tpto

### Description

Teleports to the player specified in the argument.

### Usages

- /stp \[PlayerName\]  
  Teleport to \[PlayerName\].

- /stp \[PlayerName\] \[DestPlayerName\]  
  Teleport \[PlayerName\] to \[DestPlayerName\].

### Permission

- `psac.silentteleport`

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

The command's permission is `psac.help`.

#### /psac view \[Pages\]

See the report submitted by the player.  
The reports are sorted by highest risk, five at a time.

The command's permission is `psac.view`.

#### /psac show \<ManagementID\>

View details of the report sent by the player.  
You can run this command from the player to view the report details by the player's book.  
If you run it from the console, it will appear as a log to the console.

The command's permission is `psac.show`.

#### /psac drop \<ManagementID\>

**Completely** discards the reports sent by the player, except the command execution log.  
  
> **NOTE: The log of the deletion itself is not displayed. Be careful when discarding.**

The command's permission is `psac.drop`.

#### /psac kick \<PlayerName\> \[test\]

Kick player specified by \<PlayerName\>.  
Specifying \[test\] as the second argument kick player in test mode.

The command's permission is `psac.kick`.

#### /psac ban \<PlayerName\> \[reason...\]

Ban \<PlayerName\> **manually**.  
If you not specify \[reason...\], ban \<PlayerName\> with reason `Kicked by operator`.

### Broadcast Messages

**The following broadcast message will be played when the player is kicked.**  

```tst
[PEYANG CHEAT DETECTION] ハッキング、または不適切な発言によってゲームからプレイヤーが削除されました。
```
  
```tst
違反行為をしたプレイヤーをゲームから対処しました。ご報告ありがとうございました！
```

This message is sent when the watchdog automatically detects a cheat.  
For staff kicks, you will only broadcast a secondary message.

---

## Config settings

In this plugin, the following config is set by default.

|     Setting name      |  Default value  | Description                                                                                                                   |
| :-------------------: | :-------------: | :---------------------------------------------------------------------------------------------------------------------------- |
|    database.path      |    ./eye.db     | Save report information by specifying location of SQLite database path.                                                       |
|   database.logPath    |    ./log.db     | Save kick information by specifying location of SQLite database path.                                                         |
|  database.learnPath   |  ./learn.json   | Specify the path to the JSON file that stores the neural network weights and learning count from learning.                    |
|  database.trustPath   |   ./trust.db    | Save trusted players information by specifying location of SQLite database path.                                              |
|     npc.seconds       |        4        | Specifies the number of seconds the [NPC](#aurabot) will orbit the player.                                                    |
|      npc.time         |       0.3       | Specifies the value of [NPC](#aurabot) orbit speed.                                                                           |
|      npc.range        |       2.1       | Specifies the radius that the NPC will rotate. The default distance is suitable for KillAura detection.                       |
|    npc.reachRange     |       4.6       | Specifies the radius that the NPC will rotate. The default distance is suitable for KillAura detection with reach mode.       |
|    npc.panicRange     |       1.5       | Specifies the relative height of the [Panic NPC](#acpanic) and player.                                                        |
|  npc.panicReachRange  |       4.6       | Specifies the relative height of the [Panic NPC](#acpanic) and player with reach mode.                                        |
|      npc.wave         |      true       | Whether the [NPC](#aurabot) spins like a wave.                                                                                |
|     npc.waveMin       |       1.0       | The minimum radius that the [NPC](#aurabot) orbits like a wave.                                                               |
|    npc.speed.wave     |      true       | Specify whether to make the orbital velocity of NPC variable.                                                                 |
|  npc.speed.waveRange  |      0.03       | Specify the speed change range.                                                                                               |
|      npc.kill         |        3        | Specifies the maximum number to call when an NPC is killed within 10 seconds.                                                 |
|      npc.learn        |       0.3       | Specify the learning coefficient of the learning function. The higher the value, the less processing, but the less accurate.  |
|     npc.vlLevel       |       17        | This value is used to evaluate the VL when the NPC has not learned beyond this npc.learnCount.                                |
|    npc.learnCount     |       15        | If the learn function learns more than this number of times, the kick rating will be transferred to the learn function.       |
|     kick.delay        |        2        | Specifies the delay between sending a broadcast message and kicking the player.                                               |
|   kick.defaultKick    |       25        | Kick if the NPC is attacked above this value. This value takes precedence if no learned data is found.                        |
| decoration.lightning  |      true       | Specifies whether to drop lightning effect\(no damage\) when kicking.                                                         |
|   decoration.flame    |      true       | Specifies whether to apply flame effect to player when kicking.                                                               |
|   decoration.circle   |      true       | Specifies whether to draw colored circle with effect to player when kicking.                                                  |
|     message.lynx      |      true       | Specifies whether Lynx Mod compatible.                                                                                        |
|  autoMessage.enabled  |      true       | Toggle the presence or absence of regular messages.                                                                           |
|   autoMessage.time    |       15        | Specify a minutes for recurring messages.                                                                                     |
|      commands.**      |  \[property\]   | See [command override feature](#what-is-command-override).

---

## What is this NPC\(WatchDog\)?

The Watchdog calls the NPC with a random username using the [words file](../src/main/resources/wordsx256.txt).  
The NPC skin is displayed randomly by included 1400 skin sets.

### What is \<ManagementID\>?

\<ManagementID\> is a 32-character alphanumeric string that is automatically assigned when the player submits the report.  
This ID is displayed when you run `/psac view` from console.  
Also, can execute commands related to the \<ManagementID\> from the player.

### Why not automatically execute BAN commands in this plugin?

The plugin is concerned about falsely banning players due to false Watchdog detection.  
Therefore, the plugin does not ban players **automatically**.


## What is learning function?

This plugin has a learning function that automatically adjusts the parameters using the actual cheat material.  
Learning cheat data can improve the accuracy of function decision to kick or not.  
The learning function of this plugin uses machine learning to use a neural network.  
**This feature is under development. Please note that this function cannot learn completely.**

### Learning mechanism

The learning feature of this plugin adjusts key parameters by iteratively calculating the average of the parameters when it detects a cheat or kick.

## What is *[YAML resources file](../src/main/resources)*?

When you build PeyangSuperbAntiCheat.jar with `mvn package`, `mvn shade` is automatically executed.  
You can edit this file to change the plugin resources before building.
  
***[message.yml](../src/main/resources/message.yml)***  

*[message.yml](../src/main/resources/message.yml)* is **automatically included jar resource** and **used to change the PSAC message**.  
Programmatically, YAML is referenced as a node tree and anything related to **dynamic references such as "%%name%%" cannot be changed**.
  
***[config.yml](../src/main/resources/config.yml)***  

*[config.yml](../src/main/resources/config.yml)* is **automatically included jar resource** and **the plugin references the pre-build configuration dataset**.  
See [here](#config-settings) for configuration settings.
  
> **WARNING: Configuration settings will not change unless you change them before building.**
  
***[plugin.yml](../src/main/resources/plugin.yml)***  

*[plugin.yml](../src/main/resources/plugin.yml)* contains assembly information, commands, and permission settings for the plugin itself.  
**Therefore, do not change much.**

## What utility items for [/target](#target)?

Player can get the following utility items by executing [/target](#target).

|    Item     |           ID             | Description                                      |                Executing Command                |
| :---------: | :----------------------: | :----------------------------------------------- | :---------------------------------------------: |
|  Dog head   |        AURA_BOT          | Summon [AuraBot NPC](#aurabot) to the target. |         [/aurabot](#aurabot) \<Target\>            |
|  Dog head   |       AURA_PANIC         | Summon [AuraPanic NPC](#acpanic) to the target.  |         [/acpanic](#acpanic) \<Target\>         |
|    Arrow    |        TEST_KB           | Shoot an arrow invisible from target.            |          [/testkb](#testkb) \<Target\>          |
|   Compass   |        TRACKER           | Silent teleport to the target.                   |  [/silentteleport](#silentteleport) \<Target\>  |
|    Book     |          BANS            | The Kick/Ban record of the target is displayed.  |           [/bans](#bans) -a \<Target\>          |
|    Arrow    |   TO_TARGET_\<Number\>   | Go to next page.                                 |           [/target](#target) \<Number\>         |
|    Clock    |          BACK            | Go back.                                         |                      none                       |
|    Arrow    | BACK_TOTARGET_\<Number\> | Back to page 1.                                  |           [/target](#target) \<Number\>         |
|    Reed     |          PULL            | Pull the target.                                 |             [/pull](#pull) \<Target\>           |
|  Blaze Rod  |      TARGET_STICK        | Target the player you are looking at.            |     [/target](#target) \<Looking Player\>       |

## What is command override?

PSAC has been implemented command override feature. got cha!  
The feature is able to setting in [config file](../src/main/resources/config.yml).

```yaml
commands:
  kick:
    #Enable command override feature
    enable: true
```

This example is replacing minecraft's default command to [/psac kick](#arguments).  
[/psac kick](#arguments) can show particles, add decorations when kicking, and record to databases.

## What library does this plugin use?

See *[pom.xml](../pom.xml)* for more information.  
It also describes the Source codes / Libraries / APIs used in [Thanks](#thanks).

## Did you find any bugs or errors?

We accept bugs and errors related to [GitHub issues](https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/issues).  
I think that it will be handled by about two people, so please feel free to post.

#### Assignees

Depending on the language of the problem, the following persons are responsible for resolution:
  
- Japanese issue assignee: [@peyang-Celeron](https://github.com/peyang-Celeron) (and [@Lemonade19x](https://github.com/Lemonade19x)?)
- English issue assignee: [@Potato1682](https://github.com/Potato1682)

---

## Thanks

This plugin uses the following Libraries / APIs / Source codes:

- [brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)
- dmulloy2/ProtocolLib [\[Spigot\]](https://www.spigotmc.org/resources/protocollib.1997/) | [\[Bukkit\]](https://dev.bukkit.org/projects/protocollib)
- [jedk1/BookUtil.java](https://www.spigotmc.org/threads/resource-bookutil-1-8-1-9.131549/)
- [DarkBlade12/ReflectionUtils.java](https://github.com/DarkBlade12/ParticleEffect/blob/master/src/main/java/com/darkblade12/particleeffect/ReflectionUtils.java)
- [P2P-Develop/PeyangSuperLibrary](https://github.com/P2P-Develop/PeyangSuperLibrary)
- [PhantomUnicorns](https://stackoverflow.com/users/6727559/phantomunicorns)
- [Matrix API](https://matrix.rip/) [\(MC-Market\)](https://www.mc-market.org/resources/13999/)

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<a href="https://github.com/peyang-Celeron/PeyangSuperbAntiCheat/tree/master/docs/memo">)̶̡̢̡̧̡̧̢̢̡̡̨̨̡̢̧̨̞̘͖̦͈͎̰͔̯͉̻͍͎̻̙̝͖̤̤̠͎̜̗̜̞̪̞̳̳̫͇̜̜̭̣̥͍̲̝͚̟̠̺͎͍̠̩͕͉͇͔͚̙̭̱̺͚̭̣̥͕̙̪̣̰͙̲͚̙̟̲̩̗̯͇̫̬͈̜̣̝͈̗̮͕̝͈̟̬͍͚̜̗͖̭̗͍̰̫̟͎̠͇͚̫͖̹̲͔̝̱̝̜̮̲̥̼͍̯̪͙̬̩̠̦͎̫̖̰̞͇͖̘̱͍̥̟͕͔͍͉̳̤͎̠͍͖̩̼̫̞̫͎͖̟͕̻̟̬̗̰̲̦͖̬̫͍̮̟̖͚̤̝̖̥́̃͊̄̓̊̀̐́͂̌̌̽̈́͒́̍͆̀̇͊̏́͛͛̀̊̊̏̈̓́̈́̔̃̃̃͗͊̒̿̓̚͘̕̕̕̕͜͜͜͝ͅͅͅͅ.̵̡̧̡̨̢̨̡̨̨̡̢̨̧̡̨̧̧̢̨̨̡̧̡̡̧̨̡̡̛̛̛̛̛̛̛̜͓̭͕̗͍̱̼͔̦̹̼͉͔̜͎̩͖̤̮͎͙̙̹̗͙͇͓̱̱̝̣̭̟̭̙̻̝͚̖̻͙̫̠̰̠͖̮̞̱̱̗̺̺̗̞̝̦̖̮͉͇̱̗͎̪̫͎͔͔̝͖̮̤̖͚͔̜̞̝͕̬̱͈̱̦̩̙̱̗̦̼̺͇̭̤͈̞̳͍̤̭̟̫͙̞̰̹̪̱͈̱̺̣̫͇͔̙͕̹̱͉̝̙̙͙̹͉̥̺̜̲̮̳̯̯̖͈͓̰̥̙̻̹̳̘͈̗̺͖̬͍̘̘̦͕̫͉͚̹̲͖̫̯̙̠͉͍̰̫͎̼̫̻͖̖̺̰̥͖͎̦͓͇̮͚͖̳̻̻̭̻̜̥̥͈̤̥͇̺̙̬̜̞̖̗̻̺͓̺̙̯̯̰̯̱͇̰̤̌̓̉̊͛̾͐͊͐͛̃̾̾͋̓̒̋̓̒̂̓̐̆̑̎̂̆͂͌̅͛̊͒̾́̑̒́̅̑̍͐̓̾͐̐̾̌̒̀́̔̑͐͌̈́͐̑̑̓̓̐̆̀͒̍̐̎̈̈͌͂͂́̀̓̊͌̅̊̏̓͌͊̐̊̌̇̑̈́̀͗͌̿̃̊͐̓̊̏̇͆́̽̂̃̅͑̌̾̔̋́́̂͑̂̓̅̍̀̋̾̑̀͊̑̓͆̓̇̅̀̂̉̈́̄͒̓́̂͂̽̃̈́͆̓̃̀̈́̅̎̓̄͋̽͗̋̏̊͐̉̀̑͑̍͋̓͂̾̅̄̊͑̄́̆̓̅́̀͑̉̋̈́͛̈́͒̽͋̄̉̉̈́̔̽̽̓́̑̂͐͌̑̌̆̎͌̄̌͒̿͋̆͒͗̔̓̅̇̏͂̆̓̇͒̈́̐́̆͂͛́͂̽̊͗̇̽̓͊̄̆̋̉̎̔̂́̑̂͗̔̉̂̈́̕̚̚͘͘͘̚͘̚̚̕͘̕͘̚͘̚̚̕͜͜͜͜͜͜͠͠͝͝͝͝͠͝͝͝͠͝͝͝͠͝ͅͅͅ/̷̧̡̡̢̡̢̢̧̨̧̡̧̧̢̡̢̧̨̧̢̡̧̧̛̛̛͈̫̳̮̩̱͈̮͚̯͖̞͕͇͇̜̠̟͉̗̘̥̪͉̠͉͖̻̰̹̯͔̘̝̻̜͙̬̪̲̭̙̮̻͚̝̹͎̥͈͍̣͎̻̘͙̜͍̻̞̺͎͍̰̙̦̞͉̠̦̞͕̩̱͉͚̭̩̝͍̠̼̳͈̰̫̫͔̯̝̲̘͉̤͙͚̙̯͉̠͎̠͉̺͈͎͕̝̟̹͇͎̘̰̤̺̘̙̖͕̫͓̳͎̪̞̼͚̭̥̘͙̭͇͔̹̯͙̝͈͇̯̲̹̬͙̰͈̣̻͔̭̦͇̙̻͇͙̖̫̹̤̘̺̤̩̜̲̰̳̞̦̹̱́̾͆͒̈́̊̽̎̒͊͌̂̏́̐̿̅́̈͛̂̈̓̍̽̑͒̀͒͒͑̆̽̎͛́͐̒́̄̒̈́̃̌̐̅̃̅͂̌̃͂̽̾̐̒͛̄̀͒̍̄͆͌̿̂͊͛̏͑̇̇͐̈́̂̉̌́͊͋̿͑̒̂͂̔̈́́͆̀̓̓̇̊̄͊̊͊̉͂̀͊̄̃̓͊̾̅̅̂̑̃̎͐̃̍͂́̈̃̌̔̽̓͊̓͋̿̈́̿̏͗̌̽̔̃͗̍̊̈́̋͐̐̄̈́̐̈́̿̋̅̓̀̍̐̈́̉̃̑̈̓̐̆̇͐̆̽̔̎̒́̽́́̌͗͛̓̂́͋͂͌͆̽̈́̿̅̑̄̉̏̈̚͘̕͘͘͘̚̚̕͜͜͜͠͝͠͝͝͝͝͠͠͝͠͠͝͝͝͠͠͝ͅͅͅͅͅͅ:̷̢̡̡̡̡̧̧̩̥͚̣̹̥͉̱̩̘̲̮̠̺̦̘̭̟̭̮͎̭̖̝͈͔̟̬̩̯͔͎̣̼̫̞͕͍̗̮̼͉̮̞̟̻͖̼̦̜̙̭̪̳̖̘̥̈͜͝ͅͅ$̶̡̢̧̡̧̧̢̨̢̢̢̨̨̢̡̡̡̡̧̨̢̢̡̡̢̢̨̢̧̡̛̛̛̛̛̛̱̬̻̮̩̪͎͇̞̰͉̦̥̖̬͎̥̹͎̰͎̤̤̺̯̺̻̖̩̪͎͇̪̬̝̯͚͇͈͔͚̯͕̹̣̳͚̫͓̩̳̦̪̝̪̗̯͎̤̪̜̞̯͈̺̜͚̲̭̜̭̤̠͇̯̪̞͔̤̝̤̖̼̟̜̺̣̦̰̮̟̼̣̤̦̻̖͍͔̰̭̖̤̺̣̤̞̖̱̣̱͕̫͉͓͔̜̟̻̪͈̰̠̞͙̗̗̼͖̫̹͈͕̠͕͉̯̰̖̭̫̤̳͎̜̙͍͚̣̖̙͚̖̠͙̰̩͚̮̼͔͓̼̹͖͖̮̟̲͎̜̫͉̖̭̞̳̥͓͖̬̞̳̩̱͔̭̭͈͕̯̝͍̼̮̝̥̹͔̱̰̬̦̥̖̩͓̘̭̘͍͕͔̯̮̻̘͔̞͇̞̪̲̝͉͓̰̳̣̺̪͈̣̮̱͖̦̹͎̟͎̖̱̥͙̪͇̘̘̝͐̆͗̒̿̈̌͆͋̒̑́̍̓́͆͒̍̒̀̑̾̄́̔̍̇̔̈́̾͛͆̑̂̄͛̒͌̈́͑͆͛̎̊̈͛̏̽͊̋̌̋̄̄̂͂̀̽̈́̌͌̎͑͊̑̀͐͗̓̾̂̎̅̓́͗̀̓͆̾̇͒̈́̃͆͂̅͛̊̍̽̃̏̀͗̃̾̌̈́̆͛̏̉́̅́̔͒̐̇̂̏̌͒̄̋̓̔͑͂̑̈̑̊͑̇̌͒̔̔͆̔̇̑̓̔́̑͒́̈͋͊̓̑̾͗͛͐̿̊̇͛̆͆̂̾̔͗̐̑̂̈͋͗͘̕̕͘͘̚͘̚̚̕̚̕͜͜͜͜͜͜͜͜͝͝͝͠͠͠͝͠͝ͅͅͅͅͅͅͅ"̵̡̧̡̡̢̡̡̧̨̢̢̨̛̜̙͖͓̯̘̼͈͉͖͎͕̪̫̭͙̼̗̺̥̫̖̫̖̠͕͎̳̳̭̫̫̝̤̞̞̰͙̯̳͙͖̩̬̰̤̯̯͍̻͇͎͈̪̳̼̫̰͎͇͇̳̙̘̜͎͚̦̞̬̜̙͇͙̤̖̮̰̘̫͉̞̩̯͈͎̞͙̣̰͇̹͕̮̥̭̻̳͎͈̖̝̲̳̭̤͇̥̘͖͉̥̦̝̞̞͚̰̻͇͂͌͌̈́͑̋̀̑̆͑͛͌͌̈́̿̋́̈́̀̓̾͐͑̂̇̈͋̀̓̐́͒̎̍̈̅͊͋́̅̂̌͊̔͂̌̾̅̈́̒͂̍̾͗̇̋̽̊͌͐́̄́͑̀͐̇͒̓̈́̂̈́̉͗̏̒̄͑̓̈́̎́̒̓̈̍͒͐͌̽͂̓͋̓͘͘̚̚̚͜͝͝͝͝͠͝͠͝ͅͅͅͅͅ1̸̨̧̨̡̛̠̞̭͖̮͍̺͔̠͕͖̪̘̫̭̩̳̻̳̠͖̪̫͉̜̜̟̯̥̲̜̥̪̹͇͔̘͍̣͇̪̰͇͕͚̭̥͙͎̹̮͚͕̲̪̲̀̿̑̋͊̓̎̓̇̌̒̇̅̓͋̈́̈́̈͆̉̓̐̄̊͗͗͐̕͘͜͜͜͜͝͠͝͝͝ͅͅ$̴̨̡̧̧̡̢̢̢̡̡̢̨̧̡̡̢̢̧̡̨̨̡̢̨̡̛̛̛̗͍̝̺̟͚͚͓͓͓͙͖̙̮͈͉̰͚̲͔̭̘̳͉̫̪̫̱͔̫̜̞̻͕̟̬̗̲͔͖̦͓̘̗͍̙̥͓̗͍͈̝̥̭̳̯̱̠͍͚͈̘͉̗̞̟͖͉͎̦̹̼̳͕͖͙͓̣͖̣͙̟̝̦͓͉̪̬͍̝̥̘͇͙̻̰͉͓̰̗̟͇̼̯̪̟̖̫̩͎͍͖̺̫̰̭͙͈͔̗̲͇̞̻̖̣͎͚̜̪̟̗̤͈͈̲̹͖͇̬̹͈͈͔͎͍̳̥̻̰̭̹̞͓͉̘̺̫̞̺͔̪̭̮̰̤̪̞̰̹̰̼̗̦̦̻̠̳͈̲̱̰̗̦̺̩̗̝̯̯̥̩̦̝̣̳̟̘̩͖̦̪̘̘̣̹̗̙̤͉̟͙̘̣̫̥̗̗͉͔̮̈̿̃͊̇̀͒̀͐͆́̀͛͆̑͆̈͐̅̏̓͆̈́̓̐͌͗̔̔̓̓̂̊̈̾́̑́͆̃̇͒̀͋̑͌́̀̅͂́̅͑́͑͊̀̈́̐́͛́̓̐̄̈́̄̉̔̆̀̾́̽͗͐̋̉́̐̀̊̀̈́͑̾̉͌̒̍̅̈́͑́͌̏̊̉̎̓̋͗̌̏̔͘͘͘͘͘͘̚̚͜͜͜͜͜͜͜͜͠͠͠͝͠͝͝͝͠ͅͅͅͅ'̶̡̨̡̢̡̧̢̢̡̧̧̡̨̢̡̧̧̢̨̨̨̢̡̧̧̛̼̳̲̝͕̱̺̱̤̙̮̲̫̰̮̱̙̪̰̫͎̝̱̘͓̤̜̜̘̱̻̦̠͓͎̘̭̤̝̳̹̯̲̘̳̠͇͈̭̯̲̙̪̠̰̰͎̯͓̙̟̭̞̣̱̩̖̙̩͍̥̫͇͉̭̘̺̠̪̼̹͕̫̦̼̗͚͎̟̫̦͈̻̘͍͇̥̰̻͕̥̞͎͖͈̼͉̖̪̻̥̬͈͙̼͓̩͍̝̜̫̘͎͉̘͓̟͕̥̻̟̫͎͉͚̳̱̮̬͈̠̪̗̟̱̬̙̞̫̯͖͚̫̖̺̝͈̺͔̬̫͍̮̗̺̼̤̝̠̬͍͙̦͍͍͚̦̩͕̻̬̮̫͍͚̣̙̞̩̫̝̠͈̘̙̞̥̱̥̣͈̖̭͉̻̬̩͉͍̮̪̙̲̯̟̥̤̺̤̰͍͖̬͔̬̣̳͍̤̠̤̝̫͍͕̠̣͆̊͗̑̄̓̆̐̀̓́̇͜͜͜͜͜͜͜͜͠͠ͅͅͅͅͅͅ^̷̛̛̛̛̛̛̛̥̮̱͉͇̝̼̝͑̊̅̈̇͐̔̎̌͊̋̀̊̈̂͒̍̈́̆̈̑̈́̑́̐͑̿̐̒̎̈́͊̏͛̒̎͌́̈͗̊̊̈́͑͒̊̃̈́́̾̿̏̃͌͋̈́̃́͗͊̄̈́̅̒͗̽͗̉̈̐̇͛̊̽̍̿̄̔̎͂̊͌͗͋͂̇̄̽̈́̽̌͒͊̾̊̾̐̀̎͂̂̾͒̓̔͊͆̈́̒͆̽̆́̂̎͗͆̊͒̅̈́̒̋̿̈́͗̅́͂͂͐͛̎̇̃͆̀̆̈͐͊̒́́̑̐̓̈́͋͂͛͆̐͛̈́̈̀̒̔̎͗̓̓͗̃̆͑͋͑̈́̊̃͂̀̐͋͆̄̌͊͋̏̃͐̉̓̓͂̈́̓͌̈́̒̈́̐́̿̂̿̐̀̿͒̈́͆̎̈́̌̏̏͋̉͋̌̀̾͛̽̈́̄̄̊̃͋́̾̎̐̑͊̆́͐͗̄͊̈́͗̄̾͑̿̀̓́̍̏̒̄͘̚̚͘͘̕̕̕̕͘̕͘͘͘͘̕̕͠͠͠͝͠͝͝͠͝͝͝͝͠͠͝\̸̧̢̧̨̨̢̡̡̧̡̢̨̢̨̡̡̧̨̨̡̛͚͕̠̰͔̹̳̞̹̖̟̠̮͇̞̩̯͇̫̺̪͖̭̩͓̝͕͎͓̮̥͓̩͍̗̻̞͇̠͙͚̖̫̖̬̜̩̪̳͔̪̳̻̦̙̬̟̩̦̮̺̯̞̼͔̯̹͙͚͕͔̮̻̞̰̠̻̙̺̲͓̟͖̳̟̝͚̭̼̞͖͈͖̖͉̟͓̙͇̥̗̥̗̗̯͓̘̙̤͚͎̯̜̪̣̦̠̗̜̹̼̣͉̦̦̞̹͍̘͈̰͉̖͓̯̖̬̖͙͇̜̥͙͔̥̘̩̹͔̲̻̭̺̫͕̭̞̖̹̗̤͍̳̻̰̮̭̞̯̖̞̰̬̘͉̫͚̥͇̥͓̫͍͙̤͙̱̳͔̫̖͔̺̥͚̙͎̗̦̮̲͍͓̺͉̲͓̊̾̔̐͂͋͗̆̀͂̅̊̂͑̎́̆̂͗̕̚͘͜͜͜͜͜͜͜͝͝ͅͅͅͅͅͅͅͅͅ-̵̢̢̧̡̧̧̢̡̢̢̧̢̢̧̢̢̡̨̨̛̛̛̛̝̖̳̗̩̝̱̗͉̟͖̜͙̹̩͖͓̘̞͇̙̬̩̮̮̯̜̦̻̤̫̗̘̖̱͔͖͇̥͓̰̞͉̯͉͕̟̳̹̹̫͚͙̺̜̗̯̪͉̥̭̼̱̺̱̬͚̮̞̤̬̗͎̯̘͔̝̪̺̪̮̩̙̦̫̲̹̤͉͍̘̠͍̟͕̫͙̫̣̤͍̰̰̬̻͉̮̦̼̦̞̹̣̥͙͕̯̦͕̙̥͕̰̱͍͕̪̯̬̝̟̻̰̹̼͇̺̦̘̮͓̰̹̥̪̟̲̠̞͎̫̯̹̫̲̪̯͚̭͈͎͖͖̼̙̥̹̬̖͍͕̙̤̩̻̦̮͔͚̱̳̜̻̝̼͓̹̬̝͚͖̰͈̤͈̫͚͉̳͙̠̟͚͛̆̽͑̒̓̀̏̌́̒̎͛̉̅̈̏̓̈́̒̔̈́̂̽̅͛̔̆̓̑̾̅̏̄̋͑̽͐̈̏̎͐̋̂̇͒͛̃̄͛͛̑̑̉̾͛͑̉́͆̑̐̾̈́͗̅̽̅̂̀̓̏͐͂̉̽̑͂͆͌̎̈́͌̒͆̍̌̎̊̄̋͑̓̎̽̂̂̓̀͛̆͗̀͋̉͋̓̉͛͒̊́̅̆́̎̆̑͊̀̈́͊̈́̆̆̀͆͛͗̐͛̉́͊̉̏͛̂̑̃̀̋͆̃̔̀̃̾͂͆͂̈́̔̔͐͗̂̒̑͛̐͋͐̈́̇̓̀̊̑͋̏̈́̂̽̋̽̉͗̈͐͆̈́͋̄̾̐̕̕̕͘͘̕̕̕̚̚̕͘̕̚͜͜͜͜͜͜͠͝͝͠͝͠͝͝͝͝͝͝͠ͅͅͅͅ=̶̨̛̛̛̛̯̪̘̜̰̱̖̯̳̻̣͖̅̏̾̓̏̓̈́̈́̿̑̄͋̈́̅̅̏͌͌̿͊̽͌̉̔͋͋̔̌̓̆͆͛̽͛̓̑̍͋́̀̏̐̋͒̄́̋̅̀̃̒̎̀̾̌̓̅͗̆́̏̑̋̈̒̆͐̎̎̌̎̓́̒͑̔̀̍̆̈́͌̿̇̅̈̆̅̀͆̋͒͛̂̑̐̈́͆̅͗̋̓̌̂̀͛̿̀̾̓̒́̅͑̌͗̎̀̏͊̌́̎͛͛̾͋̅̓̇̂̓̂͂͐̈̅̊̔͑̅͗̇̅̌͂̈́͗̀͗̑̏͒̎̾̕͘̕̕̕̕̕͘̚͘̚̕͘̚͘͝͠͠͝͝͝͠͠͝͠͝͠͝_̷̢̨̧̨̢̡̧̨̛̛̛̛̠̖̖̘̖̼͎̗̺̥͈̣̥̬̦̙̹̻̬͚̳̟̞̣̖̜͎͉̯̘̺̬͖̮̫̘̰̪̩͔̮̫̲̬̹͓̜̺͈͚̲͙͍̦͕̲̳̝̦̪͗̀͂̔̍̏̀̉̓͋̄̀̿̔͑̀̾̓̓̊̿̾͋͌̉̈́͛̍̋̎͂̍͂̑̈́̂̓̎͂̓̂́͂̌̓̈́̅̅́̌͒̃̊̎̐̈̄̈́͒͌̓̌͋̈́̂̐́̽̽̉̔̄͌͋̽̈́̇̄͂͌͑̀̋͊͋̊͊̓̈́̑̈̏̊̈́̀̅̆̐̅̊͂̑̇͆̀̃̈́͑̌͒̈́͌͒͋́̓̋͐̾̽̂̉̈́͆̆̂̉̔̆̑̇̈̐̅̄̅̎̔͊̊̍͑͐̈́͐͋̽̅́̈́͛͂́̈́͌̾̏̄͊͋̐̏́͆̑̏̍̂̕͘̚͘͘̚̕̚̕̕̕͘̚͜͜͜͝͝͝͠͝͝͠͝͝͠͝͠ͅͅͅ/̸̭̏̾̌̍̽̊͌̎̓͐͊͐̽̅̾̅͊͂̈́̔̕̕͠͝͝\̵̦͋̍͐̎̔̍́̒͒̅͆̆̋̏͆̾̈́̋́̏̋̓̎͐͛̈́̆͌͒̊̂̅̓̐̃͊̄̌͗̂̆͛̈́͌̓̈̌͂́̌̍̈́́̒̌̈́̂͌̿͛͂̂͆͐͘͘̚͘̚͘̕͝͝͠͠}̸̡̢̧̡̡̡̨̢̢̧̧̡̧̨̧̢̧̨̢̨̨̨̨̛̛̛̛͕̙͙͙̫̝͓̮͚̠̺͔̻͔̳̬̤̫̞̼̱̮̼̥̤̬̻͙̳͔͙̘̱̟̣̜̟͚͇̝͙͔͈̟̘̦̫̥̫̯̲̖̻̬̞̭̣͖̘͉͈̖̺̞͍̻̥͈͍͕͚̫̘̙̞̣̲̱͍̹̰͖͓͓͍̟̜͎͙͙̳̬̤͓̲̫͇̠̝̯̜̞̭̮̟̬̤̝̤͓̤͉͙̻̟̜̝̬͍̭͕͙͕̪͓̥̰̺͇̥̬̳̹̪̩̰̪͚̤̫̠͉̩͓͉͙̭̭̦̝͇̯͍͍̪̫̩̦̙͚̤̭̮̹̘̮̤̱͔̹̗̟̪̞̯͚̻͍̫̝͙̙̘͙̞̫͚̹̣̝̩̮̲͉͖̟̺͔̞͖͙̬̪͇̬̑̌̈́͗̽͑̿̎̍̈́̈́͂̂͌̀̄̈́̀̍̈́̎̐̓̋̏̀̉̇̓͋̉̃͂̐͗̂̋͂̅̔̉̍̇́̓̿̃́͋͆͌̀͂̃̄͒̽̾̌̂͋̀̊̀̄̑̋̽̇́̾̈̂̍͗̇̉̏̂̌̿̃̍͌͒̏̈͊̏͆͗͊̐͗̀͋̀͌̌͒̔̓͂̄̐͒͑̿̒̏͛͂̎̈́̉̅́̒͛͂̾̅̎͆̈́͑͌͂́͒̒͊̿͋͑͒͆̈́̓͛̃́̀́̉͑̽͊̈́̆̊͑̐̓͋̃̓̆̑͑́̊̐̆͗̔̑̾͒̿̍́̌̈́͛̊̐̇̈́͐̎̽̒̄̇͋̇́͌͒͆͒̉̎̑̓̃̉͆̈́̄̆͊̍͑͂̿̈́̎̆̈́̓̃͒̂͋̏͌̊̐͂͆̋̎̀͐̚̚̚̕͘͘̕̚̚͘͘͜͜͜͜͜͜͜͝͝͝͝͝͠͠͝͝͝͝͝͝͝͝͝͝͝͝ͅͅͅͅͅ{̴̨̢̧̡̧̢̧̢̡̢̨̨̨̢̢̧̧̢̨̨̢̧̡̡̧̨̡̧̧̨̨̨̧̧̛̛̛̛̛̛̛͓͖̥̰͖͉̠͖̣̣̲͚͔̻̭͍̝̣͓͎̱̠̘͖̯̻̘̘͉̪̺̰̲̺̳̤̤̻͙̤̯̮̲͈̜͉̲̙̺̠̖͚̖̥̗̰̹͖͈̱̲̤̘͈̭̘̬̰̳̦̝͕̼͚̘̝̠̹̥̯̱͖̥̺̟͙̯̖̣͓̤̗͚̳̥̰̗̲̫̝̲͎̩͓͚̘͖͓͎̜̥̟̗͙̘̞͙͉̱̭̜̤͎̙̳͖͉̮͉̰̮̝̙̩͓͎̦̟̰̼̥͎̩͓̠̦̩̻̖͚̻̤̼̥͖̠̹̜̻̪̞̜̞̺̫̗̮͓͍̟̻̙̣̯͈̤͖͖̣̗̯̦̞̝̺̣͉̳̮͖̦̦̞̫͎͇̪͔͔̻͙͕̭͖͕̲̳͓̝̺̝͍̣͍̤̩̜̭̲̲̼̪̖̬̬̗̝̳̑̔̈͊͊̾̈́̈́̐̀̈́̾̅̒̒͌̉͑́̒̑̊̀̅̌̊̎̃̏̆̄̂̇̀̈̂̿͑͛͌̈́̏̇͗̒͆̓̓͆̌̉̓̔̈́̾͒̉̍̿̀͗̌͒̓̏͒̂͆͆̈́͗͂͛͒̒̀͂̇̍́̅̉̿̑̃́͑̐̿́͒͂͊̄̈́̍̊͑̋̿̔̅͛̽͆̈́̆́̈̀̏̈́̓̈́̈̃̃͊́̈́͂͆̂̔͒́̀̉̒̈́̌̉̋̓̉̑̃̿̒̊́̓͂̍́͊̈́̔͛̆̌̽̄̐̂̌͐͌̇͋͋̆̑͐͂̾͗̽̌̅́̅̌̄̇̿̑̅͗̈̂͐͗̒͐̾͐̇̊̍̋̉̐͆̀̌͋̂͐͒̈́̇̾̊͌͊̐̇̊̈̔̅̃͑͑͂́͑̍͆̒̇̕͘͘̚̚̕̕͘͘̕̕͘̚͘͘̚͘͘͜͜͜͝͝͠͝͠͝͝͝͝͝͠͝͠͝͝͝ͅͅͅͅͅͅͅ@̸̨̢̧̢̡̢̡̢̧̡̛̛̛̛̼̞͚̞͙̪̹̫̥̙̻̘̳̮̩̠͇̙͓͕͉͍̖̝͕̥̺͖͔͙̪̱͔̳̗͓̤̳̬͉͎͇͎͔̬̱̱̹͚͍̹͙͕͔̙͍̮̘̜̖͙͉͈͙͚̯̱̰̬̺̘̳̱͚̙̲͙̹̟͈̰͕͕̗̮͍̻͇̟͈̳̼̭̹̜͚͕͉̬̏̓̋̈́̃̿̌̈́̇̔̎̒̈͐̍̎̇̀̈̐̓͛̌͒̄͛̎͋̓̈́́͑̿̈̀̑͂̏̓̀̂̄̆̋̇̒́̉͌̉͊͐̈́̋̿̈́̉͊̇̾͂͗͊͑̾̓̌̄͒͌̍̾̿͛̈́̿́̄̿̀̅͑̆̓̓̌̍́̆̇̓̈́̀͗̉͗̓̍̆̿̔͛̂̀͊̅̓̏̿̆̈̐̀̈́̑̊̿͊͂̒̀͐͌̂̽̎́͛͂͌͗̏̍̾͑̎̈́̈́̎͆͌͋̾̽͑͂̓̌̂́͗͊̑̽̿̐̋̐̔̓͂͘̕͘̕̚͘͘̚̕̕͘̕͜͜͜͜͠͝͠͝͝͝͝͠͝͠͠͝͠ͅͅͅ:̵̡̡̼̻͙͚̱̥̰͚͓̙̲̝̝̱̱̲͓͙̭̮͚̙̝̤͕̣̘̟̪̣̌̏̀͊͛̈́̅̂̂̔́̃̇͆̂̾̒͋͝ͅͅ;̸̡̢̡̨̧̧̨̧̧̢̡̧̛̛̛̛͓̦̻̮̘̪͇̙̝͍̺̦͈͎̫͍̦̳̬͓̭͈̫͙̟̬͖̫̼̮͖̙̰͚̥̹͇̠̦̥̠̦̣͖̱͉̻̤̜̦̭̰̺̮͖͎̩̥͕̭̳̝̟̺̖̥͙̬̟̪̝̺̻͎̞̗͙͍̤̥̬̻̦͎͇̯͐̀̓̇͋̈́̽͊̔͂̑̎́̀̅̀̀͒͋̋̆̒̂͐̀̎̾̆̇̏͗̎̓̊̿͌̐̽̀͋̊͒͌͛̄̎͋̏̈̂͆̄̋̔͆̅͐̑̔͋̾̉̒͛̾̀͗̓̂̋̎̿̍̈́̇̔̈́̐̍̑͒̌̈̃͗̀̋̀̍̐́͑͛̀͂̈́̊͗̎͗̀͌̿͊̂̅̇̌͆̈́̉̽̄̉͌̋͑͑̈́͒̉̒́̀̈́̅̔̊̍̂̀̾̂̓̎͛́́͂̂̿̈̃̀̌̕͘̚͘͘̚̕͜͜͝͠͝͝͠͠͠͠͝͝͠͝͝ͅ</a>
