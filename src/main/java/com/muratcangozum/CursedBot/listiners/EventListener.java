package com.muratcangozum.CursedBot.listiners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventListener extends ListenerAdapter {


    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {

        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink = event.getJumpUrl();

        String message = user.getAsTag() + "  Mesaja şu tepkiyi gösterdi: " + emoji + "  Kanalında:  " + channelMention;



        event.getGuild().getDefaultChannel().asTextChannel().sendMessage(message).queue();


    }


    // next month might not work...

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {


        MessageChannel channel = event.getChannel();

        if (!event.getAuthor().isBot()) {

            Message message = event.getMessage();
            String content = message.getContentRaw();

            if (content.contains("bot")) {


                channel.sendMessage("Biri bana mı, seslendi? ").queue();
            } else if (content.contains("mutluyum")) {


                channel.sendMessage("İmkansız, Türkiyedesin ve çok yanlış bir zamana denk geldin.").queue();

            } else if (content.contains("ali")) {

                channel.sendMessage("Aligator diye telaffuz ediliyor.");

            } else if (content.contains("görkem")) {

                channel.sendMessage("gay olan mı???").queue();
            } else if (content.contains("ne laneti")) {

                channel.sendMessage("Birçok insanın kendine özgü laneti vardır, sadece bir avuç insan bunun farkındadır.").queue();
            } else if (content.contains("iyi geceler")) {

                channel.sendMessage("karanlığınız bol olsun...").queue();
            } else if (content.contains("günaydın")) {
                channel.sendMessage("Gününüz aydın olsun efendim.").queue();
            } else if (content.contains("seni kim yarattı")) {

                channel.sendMessage("Benim yaratıcım Myura#7498").queue();
            }

        }
    }


    // Doesn't work too cuz of gateway

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        String avatar = event.getUser().getEffectiveAvatarUrl();
        System.out.println(avatar);
    }

    // not properly works check method.

    @Override
    public void onUserUpdateOnlineStatus(@NotNull UserUpdateOnlineStatusEvent event) {

        List<Member> members = event.getGuild().getMembers();
        ArrayList<Member> invisibles = new ArrayList<>();
        int hiders = 0;
        for (Member member : members) {

            if (member.getOnlineStatus() == OnlineStatus.INVISIBLE) {
                hiders++;
                invisibles.add(member);
            }

        }
        for (Member member : invisibles) {
            System.out.println("saklananlar: " + member);


        }
        System.out.println("Toplamda: " + hiders);
    }


    
}


