package com.ferodev.simplepaint.canvas

import android.graphics.Path
import java.io.ObjectInputStream
import java.io.Serializable
import java.util.*

class MyPath : Path(), Serializable {
    val actions = LinkedList<Action>()

    private fun readObject(inputStream: ObjectInputStream) {
        inputStream.defaultReadObject()

        val copiedActions = actions.map { it }
        copiedActions.forEach {
            it.perform(this)
        }
    }

    override fun reset() {
        actions.clear()
        super.reset()
    }

   

}
