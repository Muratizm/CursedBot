package com.muratcangozum.CursedBot.Listeners;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.interactions.DiscordLocale;

import java.util.List;

public class Information {

    private String serverName;
    private DiscordLocale locale;
    private int members;
    private List<GuildChannel> channelList;


    public Information(String serverName, DiscordLocale locale, int members, List<GuildChannel> channelList) {

        this.serverName = serverName;
        this.locale = locale;
        this.members = members;
        this.channelList = channelList;

        System.out.println("Sunucu ismi: " + serverName);
        System.out.println("konumu: "  + locale + "\n" + " Üye sayısı: " + members);

        for (Object o : channelList) {

            System.out.println("Kanalları: " + o);
        }


    }
}
