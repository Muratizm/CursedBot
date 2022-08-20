package com.muratcangozum.CursedBot;

import com.muratcangozum.CursedBot.Listeners.WordFilter;
import com.muratcangozum.CursedBot.commands.CommandManager;
import com.muratcangozum.CursedBot.Listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

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
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS,GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING);
        shardManager = builder.build();


        //Reg Listeners

        shardManager.addEventListener(new EventListener(), new CommandManager(), new WordFilter());




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
