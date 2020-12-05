# PeyangSuperbAntiCheat (PSAC) BungeeCord Integration Tutorial

[Overview](README-en.md#overview) | [Installation](README-en.md#installation) | [Permissions](README-en.md#permissions) | [Commands](README-en.md#commands) | [Config settings](README-en.md#config-settings) | [SQL](SQL-en.md) | BungeeCord | [Contributing](CONTRIBUTING-en.md) | [Security](SECURITY-en.md) | [FAQ](README-en.md#what-is-this-npcwatchdog)

<details>
<summary>Table of Contents</summary>

- [PeyangSuperbAntiCheat (PSAC) BungeeCord Integration Tutorial](#peyangsuperbanticheat-psac-bungeecord-integration-tutorial)
  - [Overview](#overview)
  - [What changes with BungeeCord integration](#what-changes-with-bungeecord-integration)
  - [Installation](#installation)
  - [Precautions](#precautions)
    - [How to change the plugin configuration](#how-to-change-the-plugin-configuration)

</details>

## Overview

PSAC can be managed through all servers using BungeeCord.  
This document provides a way to enable BungeeCord integration.

## What changes with BungeeCord integration

BungeeCord is well-known as a proxy server that manages all servers at the same time.  
PSAC takes advantage of it to implement a variety of new features.

- Receiving reports on all servers
- Distribute the processing time of each server

## Installation

1. Install PSAC on all servers and BungeeCord.

   Binary releases is [here](https://github.com/P2P-Develop/PeyangSuperbAntiCheat/releases).

2. _Optional_: Change the configuration of each server. Be careful when changing the configuration!

## Precautions

### How to change the plugin configuration

Edit `database.HogeHogePath`.  
The path supports the relative path and absolute path.  
If your directory tree is:

```tst
servers/
├─ database.db
├─ database2.db
├─ server1/
│  ├─ plugins/
│  │  ├─ PSAC.jar
│  │  ├─ PeyangSuperbAntiCheat/
│  │  │  ├─ config.yml
│  ├─ foo
│  ├─ bar
│  ├─ Bukkit.jar
├─ server2/
│  ├─ plugins/
│  │  ├─ PSAC.jar
│  │  ├─ PeyangSuperbAntiCheat/
│  │  │  ├─ config.yml
│  ├─ foo
│  ├─ bar
│  ├─ Bukkit.jar
```

the configuration path will be `../../../database.db`, counting from plugins.  
Also, if `eyePath` of one server is` ../../../database.db`,
set `eyePath` of all servers to which PSAC is linked to `../../../database.db`.
