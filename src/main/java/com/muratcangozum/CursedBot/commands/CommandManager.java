package com.muratcangozum.CursedBot.commands;

import com.muratcangozum.CursedBot.Listeners.Information;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        String command = event.getName();


        String serverName = event.getGuild().getName();
        DiscordLocale locale = event.getGuild().getLocale();
        int members = event.getGuild().getMemberCount();
        List<GuildChannel> channelList = event.getGuild().getChannels();
        Information getInformation = new Information(serverName, locale, members, channelList);


        if (command.contains("sunucu")) {

            MessageChannel channel = event.getChannel();
            channel.sendMessage("Sunucu konumu: " + locale + " Bulunuyor.").queue();

        } else if (command.contains("öldür")) {

            OptionMapping mappingKill = event.getOption("öldür");
            User user = mappingKill.getAsUser();


            MessageChannel channel = event.getChannel();
            channel.sendMessage(user.getAsMention() + " öldü.").queue();


            event.reply(user.getAsTag() + " peşine suikastçı gönderdin!").setEphemeral(true).queue();

        } else if (command.contains("roller")) {

            event.deferReply().setEphemeral(true).queue();
            String response = "";

            for (Role role : event.getGuild().getRoles()) {

                response += role + "\n";
            }

            event.getHook().sendMessage(response).setEphemeral(true).queue();

        } else if (command.contains("konuş")) {

            OptionMapping mappingSay = event.getOption("mesaj");
            OptionMapping mappingChannel = event.getOption("kanal");
            String message = mappingSay.getAsString();

            if (mappingChannel != null) {

                MessageChannel channel = mappingChannel.getAsChannel().asGuildMessageChannel();
                channel.sendMessage(message).queue();
                event.reply("Mesajın gönderildi!").setEphemeral(true).queue();


            } else {
                MessageChannel chan = event.getChannel();
                chan.sendMessage(message).queue();
                event.reply("Mesajın gönderildi!").setEphemeral(true).queue();


            }


        }


    }

    //Guild Command - sonradan eklenen komutlar var olan sunucularda çalışmaz max komut limiti 100
    // Command data da bir problem var onu çözmen gerek

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        OptionData killOpt = new OptionData(OptionType.USER, "öldür", "Birini öldürmek için kullanılır", true);
        OptionData saySomething = new OptionData(OptionType.STRING, "mesaj", "Sizin yerinize bot konuşsun.").setRequired(true);
        OptionData WhichChannel = new OptionData(OptionType.CHANNEL, "kanal", "Mesaji göndermek istediğiniz kanal").setRequired(false);

        List<CommandData> commandData = new ArrayList<>();

        for (CommandData data : commandData) {

            System.out.println("Komutlar: " + data + "\n");

        }


        commandData.add(Commands.slash("roller", "Sunucuda ki rolleri gösterir."));
        commandData.add(Commands.slash("öldür", "Ölüm meleği komutu, öldürmek istediğinin ismini yazman yeterli").addOptions(killOpt));
        commandData.add(Commands.slash("konuş", "Sizin yerinize bot konuşsun.").addOptions(saySomething, WhichChannel));
        event.getGuild().updateCommands().addCommands(commandData).queue();


    }


}
