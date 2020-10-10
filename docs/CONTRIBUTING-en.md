# PSAC contribution rules

[Overview](README-en.md#overview) | [Installation](README-en.md#installation) | [Permissions](README-en.md#permissions) | [Commands](README-en.md#commands) | [Config settings](README-en.md#config-settings) | [SQL](SQL-en.md) | [BungeeCord](BUNGEE-en.md) | Contributing | [Security](SECURITY-en.md) | [FAQ](README-en.md#what-is-this-npcwatchdog)

<details>
<summary>Table of Contents</summary>

- [PSAC contribution rules](#psac-contribution-rules)
  - [Overview](#overview)
    - [Issue, Pull Request manners](#issue-pull-request-manners)
    - [Commit manners](#commit-manners)
    - [See also](#see-also)

</details>

まぁまぁ真面目な日本語(笑)が欲しいなら[ここ](CONTRIBUTING.md)へ、どうぞ。

## Overview

PSAC has established some rules to properly contribute as a PSAC contributor.

### Issue, Pull Request manners

-   Try to minimize duplicate issue.
    The duplicate issue has a \[duplicate\] label and a reference to the issue, so if you notice it, close it.
-   When asking questions in Issue, keep in mind **smart questions**.
    Please use the \[question\] label when asking a question.
-   If you cannot modify `develop` directly, please create a pull request from the **forked branch**.
    PSAC has two branches, `stable` and `develop`.
-   Be sure to select the `develop` as the basis for your pull request.
    `stable` holds only the source code that is stable at the time of release.
-   Try to use pull requests and publishing templates as much as possible.

### Commit manners

-   Usually, do not use strange commit names except in **docs**.
-   If you have a GPG key, feel free to sign it.
-   If you can check it, compile it to see if it works and then commit.
    If you are not sure, and the workflow also fails, commit the fixed changes.
    > [!CAUTION]
    > Do not make significant changes in the fix commit.

### See also

[Code of Conduct](CODE_OF_CONDUCT.md)
