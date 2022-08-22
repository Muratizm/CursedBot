package com.muratcangozum.CursedBot.Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;

public class MemberJoinAndLeave extends ListenerAdapter {


    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        MessageChannel message = event.getGuild().getSystemChannel();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription("Aramıza " + event.getMember().getAsMention() + " Katıldı.");
        embed.setTimestamp(Instant.now());

        message.sendMessageEmbeds(embed.build()).queue();


    }



    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {

        MessageChannel message = event.getGuild().getSystemChannel();


    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {

        MessageChannel channel = event.getGuild().getDefaultChannel().asTextChannel();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(event.getMember().getAsMention() +  event.getChannelJoined().getAsMention() + " odasına katıldı. " );
        embed.setTimestamp(Instant.now());
        embed.setColor(new Color(29,52,179));
        channel.sendMessageEmbeds(embed.build()).queue();

    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {

        MessageChannel channel = event.getGuild().getDefaultChannel().asTextChannel();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(event.getMember().getAsMention() + ", " + event.getChannelLeft().getAsMention() + " odasından ayrıldı.");
        embed.setTimestamp(Instant.now());
        embed.setColor(new Color(14,23,86));
        channel.sendMessageEmbeds(embed.build()).queue();

    }

}
