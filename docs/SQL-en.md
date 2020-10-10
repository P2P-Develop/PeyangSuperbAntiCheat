# PeyangSuperbAntiCheat (PSAC) SQL Configuration Tutorial

[Overview](README-en.md#overview) | [Installation](README-en.md#installation) | [Permissions](README-en.md#permissions) | [Commands](README-en.md#commands) | [Config settings](README-en.md#config-settings) | SQL | [BungeeCord](BUNGEE-en.md) | [FAQ](README-en.md#what-is-this-npcwatchdog)

<details>
<summary>Table of Contents</summary>

- [PeyangSuperbAntiCheat (PSAC) SQL Configuration Tutorial](#peyangsuperbanticheat-psac-sql-configuration-tutorial)
  - [Overview](#overview)
  - [Configuration](#configuration)
    - [SQLite](#sqlite)
    - [MySQL](#mysql)
  - [Settings file format](#settings-file-format)
  - [Config settings](#config-settings)
  - [Methods](#methods)
  - [Protocols](#protocols)
  - [SQL path specification for each database](#sql-path-specification-for-each-database)
    - [SQLite](#sqlite-1)
    - [MySQL](#mysql-1)

</details>

## Overview

PSAC can use other SQLs such as SQLite, MySQL.

## Configuration

Each SQLs may require specific settings.

### SQLite

Install [this plugin](https://www.spigotmc.org/resources/sqlite-for-bungeecord.57191/update?update=344657) and restart your server.
You can use SQLite by installed the plugin.

### MySQL

No action is needed.
You can edit settings file and start database.

## Settings file format

PSAC sets SQL configurations in settings file.
The settings are described in following format:

```yml
database:
    # The database method
    # SQLite is org.sqlite.JDBC, MySQL is com.mysql.jdbc.Driver
    method: "org.sqlite.JDBC"
    url: "jdbc:sqlite"

    path: "./eye.db"
    logPath: "./log.db"
    learnPath: "./learn.json"
    trustPath: "./trust.db"
```

## Config settings

The settings are following:

|  Setting name   |  Default value  | Description                                  |
| :-------------: | :-------------: | :------------------------------------------- |
| database.method | org.sqlite.JDBC | [Methods](#methods) of SQL configulation     |
|  database.url   |   jdbc:sqlite   | [Protocols](#protocols) of SQL configulation |

## Methods

Specify the JDBC driver namespace for the database.

|  Name  |       Namespace       |
| :----: | :-------------------: |
| MySql  | com.mysql.jdbc.Driver |
| SQLite |    org.sqlite.JDBC    |

## Protocols

Specify the database access protocol.
This used as a URL prefix.
The driver is JDBC, so **first** prefix is starts `jdbc:`.

|  Name  |    Prefix     |
| :----: | :-----------: |
| MySql  | jdbc:sqlite:  |
| SQLite | jdbc:mysql:// |

## SQL path specification for each database

The SQL path specification is different in each database.

### SQLite

In this case, path can be set easily.
An example is following:

```yml
# For Windows
fooPath: "%USERPROFILE%\\DataBases\\foo.db"

# For Linux (Mac)
fooPath2: "/path/to/foo2.db"

# In relative path
fooPath3: "../rel/path/to/foo2.db"
```

### MySQL

In this case, path is this format: `Hostname:Port/Database?user=username&password=password`
An example is following:

```yaml
fooPath: "localhost:3306/eyeData?user=peyang&password=P_young"
```
