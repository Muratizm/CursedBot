package com.muratcangozum.CursedBot.Listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChatClear extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");




        if(args[0].equalsIgnoreCase("/temizle") && event.getMember().hasPermission(Permission.MANAGE_CHANNEL,Permission.MESSAGE_MANAGE,Permission.MESSAGE_HISTORY)){

            if(!isNumber(args[1])){

                return;
            }
            List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1]) + 1).complete();
            event.getChannel().asTextChannel().deleteMessages(messages).queue();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.BLACK);
            embed.setDescription(Integer.parseInt(args[1]) + 1 + " Mesaj silindi.");
            event.getChannel().sendMessageEmbeds(embed.build()).queue(message -> message.delete().queueAfter(5,TimeUnit.SECONDS));



        }




    }

    private boolean isNumber(String msg){

        try{
            Integer.parseInt(msg);
            return true;
        }
        catch (NumberFormatException e){

            return false;
        }
    }

}
