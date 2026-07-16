# TicTacToe Offline

Offline Android tic-tac-toe: **3×3** or **4×4**, human vs human or vs computer.

Package ID: `gr.ckaramolegkos.tictactoe`

## Features

- 3×3 and 4×4 boards (win = **3 in a row** on both sizes)
- Play vs a friend or vs the computer
- Computer difficulty when vs AI:
  - **Easy** — random legal moves
  - **Hard** — minimax with alpha-beta pruning
- Landscape layouts for menu / players / game

## Screenshots

### 1. Choose game type
![Choose game type](screens/screen1.jpg)

### 2. Enter name
![Enter name](screens/screen2.jpg)

### 3. Play!
![Play!](screens/screen3.jpg)

## Requirements

- JDK **17**
- Android SDK (compile/target **API 35**, min **API 21**)
- Android Studio Ladybug+ or command-line tools

## Build

```bash
./gradlew assembleDebug
```

APK output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Unit tests (pure board + AI logic, no device needed):

```bash
./gradlew test
```


## Releases

Tagged versions on `master` publish a production APK to [GitHub Releases](https://github.com/ChrisKar96/TicTacToe-Offline/releases).

### Cut a release

From an up-to-date `master` commit:

```bash
git checkout master
git pull
git tag -a v2.1.0 -m "TicTacToe Offline 2.1.0"
git push origin v2.1.0
```

Tag format: `vX.Y` or `vX.Y.Z` (example: `v2.0`, `v2.1.0`).

The **Release** workflow then:

1. Checks the tag commit is on `master`
2. Builds `assembleRelease` with matching `versionName` / `versionCode`
3. Publishes a GitHub Release with asset **`TicTacToe-Offline-vX.Y.Z.apk`**

### Optional release signing

By default the release APK is signed with the Android **debug** keystore (installable sideload, fine for personal use).

For a real keystore, add repository secrets:

| Secret | Purpose |
|--------|---------|
| `RELEASE_STORE_BASE64` | Base64-encoded `.jks` / `.keystore` |
| `RELEASE_STORE_PASSWORD` | Keystore password |
| `RELEASE_KEY_ALIAS` | Key alias |
| `RELEASE_KEY_PASSWORD` | Key password |

```bash
base64 -w0 my-release.keystore   # paste into RELEASE_STORE_BASE64
```

## CI

GitHub Actions builds the debug APK, runs unit tests, checks package/activity via `aapt`, and uploads `app-debug` as a workflow artifact on every push and pull request.

## Project layout

```text
app/src/main/java/gr/ckaramolegkos/tictactoe/
  MainActivity.java
  PlayersActivity.java
  GameActivity.java          # single board UI for 3x3 and 4x4
  model/
    Board.java               # pure game rules
    GameAi.java              # easy / hard
    Difficulty.java
```

## License

See [LICENSE.md](LICENSE.md).
