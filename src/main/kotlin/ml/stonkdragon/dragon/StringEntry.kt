package ml.stonkdragon.dragon

class StringEntry : ConfigEntry() {
    lateinit var value: String

    override fun toString(): String {
        return "$key: \"$value\";"
    }
}