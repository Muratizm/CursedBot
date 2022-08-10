package com.muratcangozum.CursedBot.commands;

import com.muratcangozum.CursedBot.listiners.Information;
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
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        String command = event.getName();

        DiscordLocale locale = event.getGuild().getLocale();
        int members = event.getGuild().getMemberCount();
        List<GuildChannel> channelList = event.getGuild().getChannels();
        Information getInformation = new Information(locale, members, channelList);

        if (command.contains("sunucu")) {

            MessageChannel channel = event.getChannel();
            channel.sendMessage("Sunucu konumu: " + locale + " Bulunuyor.").queue();

        } else if (command.contains("öldür")) {

            OptionMapping mappingKill = event.getOption("öldür");
            Mentions mentions = mappingKill.getMentions();

            MessageChannel channel = event.getChannel();
            channel.sendMessage(mentions + " öldü").queue();

            event.reply("unseen assasin just for u").setEphemeral(true).queue();

        } else if (command.contains("roller")) {

            event.deferReply().setEphemeral(true).queue();
            String response = "";

            for (Role role : event.getGuild().getRoles()) {

                response += role + "\n";
            }

            event.getHook().sendMessage(response).setEphemeral(true).queue();

        } else if (command.contains("konuş")) {

            OptionMapping mappingSay = event.getOption("konuş");
            String message = mappingSay.getAsString();

            MessageChannel channel = event.getChannel();

            channel.sendMessage(message).queue();
            event.reply("Mesajın gönderildi!").setEphemeral(true).queue();

        }


    }

    //Guild Command - sonradan eklenen komutlar var olan sunucularda çalışmaz max komut limiti 100

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        OptionData killOpt = new OptionData(OptionType.MENTIONABLE, "Öldürmek istediğin kişiyi etiketle", "Birini öldürmek için kullanılır", true);
        OptionData saySomething = new OptionData(OptionType.STRING, "konuş", "Sizin yerinize bot konuşsun.");

        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("roller", "Sunucuda ki rolleri gösterir."));
        commandData.add(Commands.slash("öldür", "Ölüm meleği komutu, öldürmek istediğinin ismini yazman yeterli").addOptions(killOpt));
        commandData.add(Commands.slash("konuş", "Sizin yerinize bot konuşsun.").addOptions(saySomething));
        event.getGuild().updateCommands().addCommands(commandData).queue();

    }

    // unlimited it takes maybe an hour
    // şu anlık altı kullanma ortalık karışıyor

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("sunucu", "Sunucunun konumunu gösterir"));
        commandData.add(Commands.slash("öldür", "Ölüm meleği komutu, öldürmek istediğinin ismini yazman yeterli"));
        event.getJDA().upsertCommand("sunucu", "Sunucunun konumunu gösterir");
        event.getJDA().upsertCommand("öldür", "Ölüm meleği komutu, öldürmek istediğinin ismini yazman yeterli");
        event.getJDA().upsertCommand((CommandData) commandData).queue();
        event.getJDA().updateCommands().addCommands(commandData).queue();

    }
}
