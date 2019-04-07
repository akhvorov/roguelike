

class Actor(point: Point, priority: Priority,
            private val actionStrategy: (Actor, List<Actor>) -> Boolean) : Drawable(point, priority) {

    override fun draw() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun act(actors: List<Actor>) {

    }
}
