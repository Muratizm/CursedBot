package com.muratcangozum.CursedBot;

import com.muratcangozum.CursedBot.commands.CommandManager;
import com.muratcangozum.CursedBot.listiners.EventListener;
import com.muratcangozum.CursedBot.listiners.Information;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.user.GenericUserEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.List;

public class Curse {


    private final Dotenv config;

    private final ShardManager shardManager;

    public Curse() throws LoginException {

        // environment variables

        config = Dotenv.configure().ignoreIfMissing().load();
        String token = config.get("TOKEN");
        String doing = "cursed life of murat";

        //ShardManager Building

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.watching(doing));
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setChunkingFilter(ChunkingFilter.ALL); // alta ki ikiliyle izinler herkes için tarama yaptığın çok memory tüketiyor önerilmez
        builder.enableCache(CacheFlag.ONLINE_STATUS, CacheFlag.ACTIVITY,CacheFlag.EMOJI,CacheFlag.CLIENT_STATUS,CacheFlag.MEMBER_OVERRIDES);
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES);
        shardManager = builder.build();


        //Reg Listeners

        shardManager.addEventListener(new EventListener(), new CommandManager());




    }

    // Instance Getters

    public Dotenv getConfig() {

        return config;
    }


    public ShardManager getShardManager() {

        return shardManager;
    }

    public static void main(String[] args) {


        try {
            Curse curse = new Curse();

        } catch (LoginException e) {

            System.out.println(e.getMessage() + " Muhtemelen token geçersiz ");
        }


    }

}
