
abstract class Drawable(protected var point: Point, protected val priority: Priority) : Comparable<Drawable> {
    enum class Priority {
        WALL,
        BONUS,
        SHOT,
        MONSTER,
        PLAYER
    }

    abstract fun draw()

    override fun compareTo(other: Drawable): Int {
        return priority.compareTo(other.priority)
    }
}
