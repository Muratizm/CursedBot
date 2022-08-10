package com.muratcangozum.CursedBot.listiners;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.interactions.DiscordLocale;

import java.util.List;

public class Information {

    private DiscordLocale locale;
    private int members;
    private List<GuildChannel> channelList;


    public Information(DiscordLocale locale, int members, List<GuildChannel> channelList){

   this.locale = locale;
   this.members = members;
   this.channelList = channelList;

   System.out.println("konumu: " + locale + " Üye sayısı: ");

   for (Object o : channelList){

       System.out.println("Kanalları: " + o);
   }


    }
}
