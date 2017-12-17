package me.tabak.projecteuler

class Tree(input: String) {
    val root: Node
    private val lines: List<List<Int>>

    init {
        lines = input
                .lines()
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { it.split(Regex("\\s+")) }
                .map { List(it.size) { index -> it[index].toInt() } }
        root = Node(0 to 0)
    }

    inner class Node(val coords: Pair<Int, Int>) {
        val value: Int
                get() = lines[coords.first][coords.second]
        val left: Node?
            get() {
                return if (coords.first + 1 == lines.size) {
                    null
                } else {
                    Node(coords.first + 1 to coords.second)
                }
            }
        val right: Node?
        get() {
            return if (coords.first + 1 == lines.size) {
                null
            } else {
                Node(coords.first + 1 to coords.second + 1)
            }
        }
    }
}

