package ml.stonkdragon.dragon

class ListEntry : ConfigEntry() {
    val values = arrayListOf<String>()

    override fun toString(): String {
        var str = "$key: ["
        values.forEach {
            str += "\"$it\"; "
        }
        str += "];"
        return str
    }
}