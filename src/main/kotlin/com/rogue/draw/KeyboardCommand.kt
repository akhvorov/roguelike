package com.rogue.draw

import com.rogue.Game
import com.rogue.actor.PlayerActor
import com.rogue.state.StateService
import com.rogue.utils.Move
import org.hexworks.zircon.api.UIEventResponses
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventResponse

interface KeyboardCommand {
    fun execute(): UIEventResponse
}

class UpCommand : KeyboardCommand {
    override fun execute(): Processed {
        PlayerActor.move(Move.UP)
        return UIEventResponses.processed()
    }
}

class RightCommand : KeyboardCommand {
    override fun execute(): Processed {
        PlayerActor.move(Move.RIGHT)
        return UIEventResponses.processed()
    }
}

class DownCommand : KeyboardCommand {
    override fun execute(): Processed {
        PlayerActor.move(Move.DOWN)
        return UIEventResponses.processed()
    }
}

class LeftCommand : KeyboardCommand {
    override fun execute(): Processed {
        PlayerActor.move(Move.LEFT)
        return UIEventResponses.processed()
    }
}

class SpaceCommand : KeyboardCommand {
    override fun execute(): Processed {
        StateService.save()
        Game.isMenu = true
        return UIEventResponses.processed()
    }
}

class IgnoreCommand : KeyboardCommand {
    override fun execute(): Pass {
        return UIEventResponses.pass()
    }
}
