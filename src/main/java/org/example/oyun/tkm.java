package org.example.oyun;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class tkm extends ListenerAdapter {
    private final Map<User, String> userChoices = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        User user = event.getAuthor();

        if (user.isBot()) {
            return; // Botların mesajlarını dikkate almıyoruz
        }

        if (message.startsWith("/tkm")) {
            String[] parts = message.split(" ", 2);
            if (parts.length < 2) {
                channel.sendMessage("Lütfen taş, kağıt veya makas seçiminizi belirtin.").queue();
                return;
            }

            String choice = parts[1].trim().toLowerCase();
            if (!choice.equals("taş") && !choice.equals("kağıt") && !choice.equals("makas") && !choice.equals("tas")) {
                channel.sendMessage("Geçersiz seçim! Lütfen taş, kağıt veya makas seçin.").queue();
                return;
            }

            userChoices.put(user, choice);

            if (userChoices.size() < 2) {
                channel.sendMessage("Diğer oyuncunun seçimini bekliyoruz...").queue();
                return;
            }

            User otherUser = userChoices.keySet().stream().filter(u -> !u.equals(user)).findFirst().orElse(null);
            String otherUserChoice = userChoices.get(otherUser);
            String result = determineWinner(choice, otherUserChoice);

            channel.sendMessageFormat("%s seçiminiz: %s\n%s seçiminiz: %s\nSonuç: %s",
                    user.getAsMention(), choice, otherUser.getAsMention(), otherUserChoice, result).queue();

            // Seçimleri temizle
            userChoices.clear();
        }
    }

    private String determineWinner(String userChoice, String otherUserChoice) {
        if (userChoice.equals(otherUserChoice)) {
            return "Beraberlik!";
        }
        switch (userChoice) {
            case "taş":
            case "tas":
                return otherUserChoice.equals("makas") ? "Kazandınız!" : "Kaybettiniz!";
            case "kağıt":
                return otherUserChoice.equals("taş") || otherUserChoice.equals("tas") ? "Kazandınız!" : "Kaybettiniz!";
            case "makas":
                return otherUserChoice.equals("kağıt") ? "Kazandınız!" : "Kaybettiniz!";
            default:
                return "Geçersiz seçim!";
        }
    }
}

