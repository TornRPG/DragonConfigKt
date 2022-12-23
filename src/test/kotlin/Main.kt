import ml.stonkdragon.dragon.ConfigParser

fun main() {
    println(ConfigParser.parse("config.drg").getString("foo")?.value)
}