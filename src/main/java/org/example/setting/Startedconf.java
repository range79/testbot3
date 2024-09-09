package org.example.setting;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.listeners.Merhaba;
import org.example.moderation.Banla;

import org.example.listeners.Merhaba2;
import org.example.oyun.tkm;


public class Startedconf {
    public JDA api1;

    public void fonksiyon()throws Exception {
//fonksiyonlarin newlenmis hali
            Merhaba merhaba = new Merhaba();
            Merhaba2 merhaba2 = new Merhaba2();
            tkm tkm = new tkm();
            Banla banla =new Banla();








        String sifre = Settings.INSTANCE.getTokensifresi();
        api1 = JDABuilder
                //token giriyor
                .createDefault(sifre,
                        //izinleri aliyor
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT).
                //komutlari ekliyor
                        addEventListeners(merhaba2,
                        merhaba,
                        tkm,
                        banla).build();


        api1.awaitReady();


        // Komutları güncelle
        api1.updateCommands()
                .addCommands(



                )
                .queue();// Komutları kuyruğa al ve gönder

        // Loglama
        System.out.println("Bot başarıyla başlatıldı.");
    }

}
