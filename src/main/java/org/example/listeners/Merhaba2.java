package org.example.listeners;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Merhaba2 extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()){return;}
        String mesaj = event.getMessage().getContentRaw();
        if (mesaj.equalsIgnoreCase("selam")){
            event.getChannel().sendMessage("aleyküm selam");
        }
    }
}
