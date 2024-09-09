package org.example.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class Banla extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String message = event.getMessage().getContentRaw();
        String reason;


        if (!message.startsWith("!ban")) {
            return; // Only handle "!ban" command
        }


        reason= message.substring(4).trim();


        Guild guild = event.getGuild();
        Member author = event.getMember();
        Member botSelf = guild.getSelfMember();


        // kullanici izinleri kontrol ediliyor
        if (author == null || !author.hasPermission(Permission.BAN_MEMBERS)) {
            event.getChannel().sendMessage("You don't have permission to use this command!").queue();
            return;
        }

        if (!botSelf.hasPermission(Permission.BAN_MEMBERS)) {
            event.getChannel().sendMessage("I don't have permission to ban members!").queue();
            return;
        }

        List<Member> mentionedMembers = event.getMessage().getMentions().getMembers();

        if (mentionedMembers.isEmpty()) {
            event.getChannel().sendMessage("Banlamak istediginiz kullaniciyi etiketliyerek bu komutu kullaniniz").queue();
            return;
        }

        Member targetMember = mentionedMembers.get(0); // ilk adami sec

        // sebeb belirtilmezse opsiyonel
        if (reason.isEmpty()) {
            reason = "Sebeb belirtilmedi";
        }

        try {
            // kulllaniciyi sebebiyle 1 gunluk banla
            String finalReason = reason;
            guild.ban(targetMember, 1, TimeUnit.DAYS).queue(
                    success -> event.getChannel().sendMessage("User " + targetMember.getEffectiveName() + " has been banned for: " + finalReason).queue(),
                    error -> event.getChannel().sendMessage("Failed to ban the user!").queue()
            );
        } catch (Exception e) {
            event.getChannel().sendMessage("An error occurred while trying to ban the user.").queue();
        }
    }
}
