package com.muratcangozum.CursedBot.commands;

import com.muratcangozum.CursedBot.Listeners.Information;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.sticker.Sticker;
import net.dv8tion.jda.api.entities.sticker.StickerItem;
import net.dv8tion.jda.api.entities.sticker.StickerSnowflake;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.net.URL;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {


    private String[] args;


    public static boolean filterOnOff = true;


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
            embed.setThumbnail(event.getGuild().getIconUrl());
            embed.addField("Sunucu ismi: ", event.getGuild().getName().toString(), true);
            embed.addField("Sunucu konumu: ", event.getGuild().getLocale().toString(), true);
            embed.addField("Toplam üye sayısı:", String.valueOf(event.getGuild().getMemberCount()), false);
            embed.addField("Sunucu kurulma tarihi: ", String.valueOf(event.getGuild().getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))), false);
            embed.addField("Sunucu sahibi: ", event.getGuild().getOwner().getAsMention(), false);
            embed.setTimestamp(Instant.now());
            event.getChannel().sendMessageEmbeds(embed.build()).setActionRow(Button.danger("delete", "Sil")).queue();
            embed.clear();


            event.reply("Sunucu bilgileri başarıyla derlendi").setEphemeral(true).queue();

        } else if (command.equalsIgnoreCase("öldür")) {

            OptionMapping mappingKill = event.getOption("ismi");
            User user = mappingKill.getAsUser();


           EmbedBuilder embed = new EmbedBuilder();

           embed.setColor(Color.red);
           embed.setDescription(user.getAsMention() + " Cesedi bulundu!");
           embed.setTimestamp(Instant.now());
           event.getChannel().sendMessageEmbeds(embed.build()).queue();

            event.reply(user.getAsTag() + " peşine suikastçı gönderdin!").setEphemeral(true).queue();

        } else if (command.equalsIgnoreCase("roller")) {


            event.deferReply().setEphemeral(true).queue();
            String response = "";

            for (Role role : event.getGuild().getRoles()) {

                response += role.getAsMention() + "\n";
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
                    event.reply("istediğiniz kişiyi sevdiniz!").setEphemeral(true).queue();

                }
                case "sarıl" -> {

                    replyMessage = " Sarıldı.";
                    event.reply("istediğiniz kişiye sarıldınız!").setEphemeral(true).queue();

                }
                case "tekmele" -> {

                    replyMessage = " Tekmeledi!";
                    event.reply("istediğiniz kişiyi tekmelediniz!").setEphemeral(true).queue();

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


        } else if (command.equalsIgnoreCase("rolal")) {

            Member member = event.getOption("kimden").getAsMember();
            Role role = event.getOption("rol").getAsRole();

            String SomeoneTakenRole = member.getAsMention().trim();
            event.getGuild().removeRoleFromMember(member.getUser(), role).queue();
            event.reply(SomeoneTakenRole + " Kişisinden şu rol: " + role.getAsMention() + " Alındı.").setEphemeral(true).queue();

        } else if (command.equalsIgnoreCase("kedi")) {

            URL url = null;
            try {
                url = new URL("https://cat-api.org/cat/normal");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Kedi");
            eb.setImage(url.toString());
            eb.setColor(Color.BLUE);
            event.getChannel().sendMessageEmbeds(eb.build()).queue();
            eb.clear();

            event.reply("Kedi gönderildi.").setEphemeral(true).queue();


        } else if (command.equalsIgnoreCase("filtreaçkapa") && filterOnOff) {

            filterOnOff = false;
            event.getChannel().sendMessage("Sohbet Filtresi " + event.getUser().getAsTag() + " tarafından kapatıldı.").queue();
            event.reply("Filtre kapatıldı.").setEphemeral(true).queue();


        } else if (command.equalsIgnoreCase("filtreaçkapa") && filterOnOff == false) {
            filterOnOff = true;
            event.getChannel().sendMessage("Sohbet Filtresi " + event.getUser().getAsTag() + " tarafından açıldı.").queue();
            event.reply("Filtre açıldı.").setEphemeral(true).queue();

        } else if (command.equalsIgnoreCase("kullanıcıbilgisi")) {

            Member mem = event.getOption("gör").getAsMember();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("-Kullanıcı bilgisi-");
            eb.setColor(new Color(255, 165, 0));
            eb.setThumbnail(mem.getEffectiveAvatar().getUrl());
            eb.addField("Kullanıcı adı: ", mem.getUser().getAsTag(), false);
            eb.addField("Kullanıcı ID: ", mem.getId(), false);
            for (int i = 0; i < mem.getActivities().size(); i++) {

                eb.addField("Kullanıcı aktivite durumu: ", String.valueOf(mem.getActivities().get(i)), false);

            }
            eb.addField("Kullanıcı oluşturma tarihi: ", mem.getTimeCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), false);
            eb.addField("Kullanıcı sunucuya katılma tarihi: ", mem.getTimeJoined().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), false);
            eb.addField("Kullanıcı online statüsü: ", mem.getOnlineStatus().getKey(), false);
            eb.setTimestamp(Instant.now());
            event.getChannel().sendMessageEmbeds(eb.build()).queue();

            event.reply("Bilgiler başarıyla görünütlendi.").setEphemeral(true).queue();

        }


    }

    //Guild Command - sonradan eklenen komutlar var olan sunucularda çalışmaz max komut limiti 100
    // Command data da bir problem var onu çözmen gerek
    // help komutu ekle (yardım)
    // filtreyi saate göre açıp kapanır yapabilirsin uğraş
    // müzik çalma eklenecek
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
        OptionData whoInfo = new OptionData(OptionType.USER, "gör", "kimi görmek istiyorsun?", true);

        List<CommandData> commandData = new ArrayList<>();

        OptionData fromWho = new OptionData(OptionType.USER, "kimden", "Rolünü alacağınız kişi", true);
        commandData.clear();
        commandData.add(Commands.slash("kullanıcıbilgisi", "Kullanıcı hakkında bilgi verir.").addOptions(whoInfo));
        commandData.add(Commands.slash("filtreaçkapa", "filtreyi açıp kapaya yarar").setDefaultPermissions(DefaultMemberPermissions.DISABLED));
        commandData.add(Commands.slash("kedi", "Rasgele bir kedi resmi gönderir."));
        commandData.add(Commands.slash("rolal", "kişinin rolünü almak için kullanılır").setDefaultPermissions(DefaultMemberPermissions.DISABLED).addOptions(fromWho, WhichRole));
        commandData.add(Commands.slash("rolver", "Birine rol vermek için kullanılır").setDefaultPermissions(DefaultMemberPermissions.DISABLED).addOptions(rolSomeone, WhichRole));
        commandData.add(Commands.slash("at", "Birini sunucudan atmak için kullanılır. bkz;kick").setDefaultPermissions(DefaultMemberPermissions.DISABLED).addOptions(kickSomeOne));
        commandData.add(Commands.slash("duygular", "Bot, istenilen duyguyu işler.").addOptions(duyguMetin, duyguKisi));
        commandData.add(Commands.slash("roller", "Sunucuda ki rolleri gösterir."));
        commandData.add(Commands.slash("öldür", "Ölüm meleği komutu, öldürmek istediğinin ismini yazman yeterli").addOptions(killOpt));
        commandData.add(Commands.slash("konuş", "Sizin yerinize bot konuşsun.").addOptions(saySomething, WhichChannel));
        event.getGuild().updateCommands().addCommands(commandData).queue();

        for (CommandData data : commandData) {

            System.out.println("Komutlar: " + data.getName() + "\n");


        }


    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {

    }
}
