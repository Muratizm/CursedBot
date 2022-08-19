package com.muratcangozum.CursedBot.commands;

import com.muratcangozum.CursedBot.Listeners.Information;
import net.dv8tion.jda.api.EmbedBuilder;
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

import java.awt.*;
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


        if (command.equalsIgnoreCase("sunucu")) {

            EmbedBuilder embed = new EmbedBuilder();

            embed.setTitle("Sunucu bilgileri:");
            embed.setColor(Color.green);
            embed.setThumbnail(event.getUser().getAvatarUrl()).setAuthor(event.getUser().getAsTag());
            embed.addField("Sunucu ismi: ", event.getGuild().getName().toString(), true);
            embed.addField("Sunucu konumu: ", event.getGuild().getLocale().toString(),true);
            embed.addField("Toplam üye sayısı:", String.valueOf(event.getGuild().getMemberCount()),false);
            embed.addField("Sunucu kurulma tarihi: ", String.valueOf(event.getGuild().getTimeCreated()),false);
            embed.addField("Sunucu sahibi: ", event.getGuild().getOwner().getAsMention(), false);
            event.getChannel().sendMessageEmbeds(embed.build()).queue();


            event.reply("Sunucu bilgileri başarıyla derlendi").setEphemeral(true).queue();

        } else if (command.equalsIgnoreCase("öldür")) {

            OptionMapping mappingKill = event.getOption("ismi");
            User user = mappingKill.getAsUser();


            MessageChannel channel = event.getChannel();
            channel.sendMessage(user.getAsMention() + " öldü.").queue();


            event.reply(user.getAsTag() + " peşine suikastçı gönderdin!").setEphemeral(true).queue();

        } else if (command.equalsIgnoreCase("roller")) {

            event.deferReply().setEphemeral(true).queue();
            String response = "";

            for (Role role : event.getGuild().getRoles()) {

                response += role + "\n";
            }

            event.getHook().sendMessage(response).setEphemeral(true).queue();

        } else if (command.equalsIgnoreCase("konuş")) {

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


        } else if (command.equalsIgnoreCase("duygular")) {


            OptionMapping optionMapping = event.getOption("duygu");
            OptionMapping optionKisi = event.getOption("kişi");

            User user = optionKisi.getAsUser();
            String duygu = optionMapping.getAsString().toString();


            String replyMessage = "";

            switch (duygu.toLowerCase()) {

                case "sev" -> {

                    replyMessage = " Kafasını sevdi.";

                }
                case "sarıl" -> {

                    replyMessage = " Sarıldı.";

                }
                case "tekmele" -> {

                    replyMessage = " Tekmeledı!";

                }


            }
            event.getChannel().sendMessage(event.getUser().getName() + ", " + user.getAsMention() + replyMessage).queue();

        } else if (command.equalsIgnoreCase("at")) {

            Member member = event.getOption("kim").getAsMember();

            String kickedPerson = member.getAsMention().trim();

            event.getGuild().kick(member.getUser()).queue();

            event.reply(kickedPerson + " Sunucudan Atıldı.").setEphemeral(true).queue();


        } else if (command.equalsIgnoreCase("rolver")) {


            Member member = event.getOption("kime").getAsMember();
            Role role = event.getOption("rol").getAsRole();

            event.getGuild().addRoleToMember(member.getUser(), role).queue();
            event.reply(member.getAsMention() + " Kişiye şu role verildi: " + role.getAsMention()).setEphemeral(true).queue();

        }
        else if(command.equalsIgnoreCase("rolal")){

             Member member = event.getOption("kimden").getAsMember();
             Role role = event.getOption("rol").getAsRole();

             String SomeoneTakenRole = member.getAsMention().trim();
             event.getGuild().removeRoleFromMember(member.getUser(),role).queue();
             event.reply(SomeoneTakenRole + " Kişisinden şu rol: " + role.getAsMention() + " Alındı.").setEphemeral(true).queue();


        }

    }

    //Guild Command - sonradan eklenen komutlar var olan sunucularda çalışmaz max komut limiti 100
    // Command data da bir problem var onu çözmen gerek

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {

        OptionData killOpt = new OptionData(OptionType.USER, "ismi", "Birini öldürmek için kullanılır", true);
        OptionData saySomething = new OptionData(OptionType.STRING, "mesaj", "Sizin yerinize bot konuşsun.").setRequired(true);
        OptionData WhichChannel = new OptionData(OptionType.CHANNEL, "kanal", "Mesaji göndermek istediğiniz kanal").setRequired(false)
                .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);

        OptionData duyguKisi = new OptionData(OptionType.USER, "kişi", "Kime duygu göndermek istiyorsum?", true);
        OptionData duyguMetin = new OptionData(OptionType.STRING, "duygu", "Duygu seçenekleri", true)
                .addChoice("Sev", "sev")
                .addChoice("Sarıl", "sarıl")
                .addChoice("Tekme", "tekmele");

        OptionData kickSomeOne = new OptionData(OptionType.USER, "kim", "Atılacak kişinin ismini girin.", true);
        OptionData rolSomeone = new OptionData(OptionType.USER, "kime", "Rol vermek istediğiniz kişi", true);
        OptionData WhichRole = new OptionData(OptionType.ROLE, "rol", "Hangi rol?", true);

        List<CommandData> commandData = new ArrayList<>();

        OptionData fromWho = new OptionData(OptionType.USER,"kimden","Rolünü alacağınız kişi",true);
        for (CommandData data : commandData) {

            System.out.println("Komutlar: " + data + "\n");

        }
        commandData.add(Commands.slash("rolal","kişinin rolünü almak için kullanılır").addOptions(fromWho,WhichRole));
        commandData.add(Commands.slash("rolver", "Birine rol vermek için kullanılır").addOptions(rolSomeone,WhichRole));
        commandData.add(Commands.slash("at", "Birini sunucudan atmak için kullanılır. bkz;kick").addOptions(kickSomeOne));
        commandData.add(Commands.slash("duygular", "Bot, istenilen duyguyu işler.").addOptions(duyguMetin, duyguKisi));
        commandData.add(Commands.slash("roller", "Sunucuda ki rolleri gösterir."));
        commandData.add(Commands.slash("öldür", "Ölüm meleği komutu, öldürmek istediğinin ismini yazman yeterli").addOptions(killOpt));
        commandData.add(Commands.slash("konuş", "Sizin yerinize bot konuşsun.").addOptions(saySomething, WhichChannel));
        event.getGuild().updateCommands().addCommands(commandData).queue();


    }


}
