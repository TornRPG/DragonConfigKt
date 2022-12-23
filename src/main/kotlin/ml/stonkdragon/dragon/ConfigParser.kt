package ml.stonkdragon.dragon

import java.io.File
import java.util.concurrent.atomic.AtomicInteger

object ConfigParser {
    fun parse(file: String): CompoundEntry {
        val f = File(file)
        val str = f.readText()

        var data: String = "_:{"
        var inStr: Boolean = false

        for (i in str.indices) {
            if (!inStr && str[i] == ' ') continue
            if (str[i] == '"' && str[i - 1] != '\\') {
                inStr = !inStr
                data += str[i]
                continue
            }
            if (str[i] == '\n') {
                inStr = false
                continue
            }
            data += str[i]
        }
        data += "};"
        val i = AtomicInteger(2)
        val rootEntry = parseCompound(data, i)
        rootEntry.key = ""
        return rootEntry
    }

    private fun isValidIdentifier(c: Char): Boolean {
        return c != ':'
    }

    private fun parseCompound(data: String, i: AtomicInteger): CompoundEntry {
        val compound = CompoundEntry()
        var c = data[i.incrementAndGet()]
        while (c != '}') {
            var key = ""
            while (isValidIdentifier(c)) {
                key += c
                c = data[i.incrementAndGet()]
            }
            c = data[i.incrementAndGet()]
            if (c == '[') {
                val values = arrayListOf<String>()
                c = data[i.incrementAndGet()]
                while (c != ']') {
                    var next = ""
                    if (c == '"') {
                        var prev = c
                        c = data[i.incrementAndGet()]
                        while (true) {
                            if (c == '"' && prev != '\\') break
                            prev = c
                            next += c
                            c = data[i.incrementAndGet()]
                        }
                        c = data[i.incrementAndGet()]
                        if (c != ';') {
                            throw InvalidSyntaxException("Missing Semicolon: $next")
                        }
                        values.add(next)
                        c = data[i.incrementAndGet()]
                    }
                }
                c = data[i.incrementAndGet()]
                if (c != ';') {
                    throw InvalidSyntaxException("Missing Semicolon: $key")
                }
                val entry = ListEntry()
                entry.key = key
                entry.values.addAll(values)
                compound.entries.add(entry)
            } else if (c == '{') {
                val entry = parseCompound(data, i)
                entry.key = key
                compound.entries.add(entry)
            } else {
                var value = ""
                if (c != '"') {
                    throw InvalidSyntaxException("Invalid String: $key")
                }
                c = data[i.incrementAndGet()]
                while (c != '"') {
                    value += c
                    c = data[i.incrementAndGet()]
                }
                c = data[i.incrementAndGet()]
                if (c != ';') {
                    throw InvalidSyntaxException("Missing Semicolon: $key")
                }
                val entry = StringEntry()
                entry.key = key
                entry.value = value
                compound.entries.add(entry)
            }
            c = data[i.incrementAndGet()]
        }
        c = data[i.incrementAndGet()]
        if (c != ';') {
            throw InvalidSyntaxException("Invalid compound entry: $compound")
        }
        return compound
    }
}