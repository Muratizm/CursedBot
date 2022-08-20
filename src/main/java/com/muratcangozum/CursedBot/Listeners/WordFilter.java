package com.muratcangozum.CursedBot.Listeners;

import com.muratcangozum.CursedBot.commands.CommandManager;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class WordFilter extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String[] Bad_Words = {"orrospu çocuğu", "ananı sikerim", "anani sikerim", "piç", "oç", "yavşak", "sikerim", "ann sikem", "ann skm", "sikim", "allah", "annesiz", "babasiz", "yavşak", "ibne", "kaltak",
                "amcık", "amcik", "am", "sik", "yarrak", "dick", "mother fucker", "fuck", "fucker", "faggot", "pussy", "asshole", "cunt", "ass", "ananizi sikiyim", "annanizi sikiyim"
                , "orsspu çocuğu", "s!k!y!m", "tayyip", "tayyip erdoğan", "recep tayyip erdoğan","orospu","orospu cocugu","erdogan","tayip","rte","yasuo","oçsun"};
        String[] message = event.getMessage().getContentRaw().toLowerCase().split(" ");

        if (CommandManager.filterOnOff) {


            for (int i = 0; i < message.length; i++) {
                for (int m = 0; m < Bad_Words.length; m++) {

                    if (message[i].equals(Bad_Words[m])) {

                        event.getMessage().delete().reason("Çok ayıp, " + event.getMember().getAsMention() + " Lütfen konuşma şeklinize dikkat edin!").queue();

                    }

                }
            }


        } else {
            System.out.println("Filtre kapatıldı");
        }


    }

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {



    }
}
