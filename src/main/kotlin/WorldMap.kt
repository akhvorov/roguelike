import java.util.*

class WorldMap {
    private var priorityMap: SortedMap<Drawable.Priority, MutableMap<Point, Drawable>> = TreeMap()
    private var map: MutableMap<Point, MutableSet<Drawable>> = HashMap()
//    private var actors: SortedSet<Actor> = TreeSet()

    fun act() {
        priorityMap.values.forEach { levelMap ->
            levelMap.values.forEach {
                if (it is Actor) {

                }
                it.draw()
            }
        }
//        map.values.flatten().sorted()
    }
}
