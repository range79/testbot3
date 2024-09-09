package org.example.listeners

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Merhaba : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val message = event.message.contentRaw
        if (message.equals( "merhaba" , ignoreCase = true)) {
            println("bota mesaj gonderildi")
            event.channel.sendMessage("merhabalar").queue()
        }
    }
}

