package ml.stonkdragon.dragon

abstract class ConfigEntry {
    lateinit var key: String
    lateinit var type: EntryType

    abstract override fun toString(): String
}
