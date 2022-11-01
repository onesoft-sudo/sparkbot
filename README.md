# SparkBot

SparkBot is an issue tracker bot. 

### Building from source 
1. Clone this repository or download and extract the [latest release](https://github.com/onesoft-sudo/sparkbot/releases/latest).
2. Run `gradle jar`, or if you don't have gradle installed, run `./gradlew jar`. It will download and install gradle automatically.
3. The executable JAR package should be available at `app/build/libs/sparkbot-<version>.jar`.
4. Set the `TOKEN` environment variable by running `export TOKEN=<your_token>`.
4. Set the `SPARK_PREFIX` environment variable to current directory by running `export SPARK_PREFIX=$(pwd)`.
4. Run `java -jar app/build/libs/sparkbot-<version>.jar` to start the bot.
