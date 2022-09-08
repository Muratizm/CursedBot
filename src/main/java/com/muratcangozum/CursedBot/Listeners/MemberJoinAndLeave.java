package com.muratcangozum.CursedBot.Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.GuildTimeoutEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateAfkTimeoutEvent;
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
        embed.setImage(event.getMember().getAvatarUrl());
        embed.setTimestamp(Instant.now());

        message.sendMessageEmbeds(embed.build()).queue();



    }



    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {

        MessageChannel messageChannel = event.getGuild().getSystemChannel();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(event.getMember().getNickname() + " Aramızdan ayrıldı :(");
        embedBuilder.setColor(new Color(250,185,185));
        embedBuilder.setThumbnail(event.getMember().getAvatarUrl());
        embedBuilder.setTimestamp(Instant.now());
        messageChannel.sendMessageEmbeds(embedBuilder.build()).queue();

    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {

        MessageChannel channel = event.getGuild().getDefaultChannel().asTextChannel();
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(event.getMember().getAsMention() +", "+  event.getChannelJoined().getAsMention() + " odasına katıldı. " );
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
