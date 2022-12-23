package ml.stonkdragon.dragon

class CompoundEntry : ConfigEntry() {
    val entries = arrayListOf<ConfigEntry>()

    fun getString(key: String): StringEntry? {
        var found: StringEntry? = null
        entries.forEach {
            if (it.key == key && it is StringEntry) {
                found = it
                return@forEach
            }
        }
        return found
    }

    fun getList(key: String): ListEntry? {
        var found: ListEntry? = null
        entries.forEach {
            if (it.key == key && it is ListEntry) {
                found = it
                return@forEach
            }
        }
        return found
    }

    fun getCompound(key: String): CompoundEntry? {
        var found: CompoundEntry? = null
        entries.forEach {
            if (it.key == key && it is CompoundEntry) {
                found = it
                return@forEach
            }
        }
        return found
    }

    fun setString(key: String, value: String) {
        entries.forEach {
            if (it.key == key && it is StringEntry) {
                it.value = value
                return
            }
        }
        entries.add(
            StringEntry().let {
                it.key = key
                it.value = value
                it
            }
        )
    }

    fun setList(key: String, vararg value: String) {
        entries.forEach {
            if (it.key == key && it is ListEntry) {
                it.values.clear()
                it.values.addAll(value)
                return
            }
        }
        entries.add(
            ListEntry().let {
                it.key = key
                it.values.addAll(value)
                it
            }
        )
    }

    fun setCompound(key: String, entry: CompoundEntry) {
        entries.forEach {
            if (it.key == key && it is CompoundEntry) {
                it.entries.clear()
                it.entries.addAll(entry.entries)
                return
            }
        }
        entries.add(entry)
    }

    override fun toString(): String {
        var str = if (key.isNotEmpty()) {
            "$key: {"
        } else {
            ""
        }
        entries.forEach {
            str += "$it"
        }
        str += if (key.isNotEmpty()) {
            "};"
        } else {
            ""
        }
        return str
    }
}