package dev.insaneduck.bot.main;

import dev.insaneduck.bot.listeners.BuildListener;
import dev.insaneduck.bot.listeners.SlashCommands;
import dev.insaneduck.bot.listeners.UtilityListener;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.apache.commons.io.FileUtils;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Bot
{
    DefaultShardManagerBuilder builder;
    ShardManager shardManager;
    public static void main(String[] args) throws IOException, LoginException
    {
        if(args.length>0)
        {
            File configFile = new File(args[0]);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode config = objectMapper.readTree(configFile);
            String commands = FileUtils.readFileToString(new File(config.get("commandsJSON").asText()), StandardCharsets.UTF_8);
            String builds = FileUtils.readFileToString(new File(config.get("buildsJSON").asText()), StandardCharsets.UTF_8);
            Bot bot = new Bot(config.get("TOKEN").asText(),commands,builds);
            bot.startBot();
        }
        else
        {
            System.out.println("input config file");
        }
    }

    public Bot(String TOKEN,String commands, String builds)
    {
        builder = DefaultShardManagerBuilder.createDefault(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.listening("build commands"));
        builder.addEventListeners(new SlashCommands(commands),new BuildListener(builds),new UtilityListener());
    }

    void startBot() throws LoginException
    {
        shardManager = builder.build();
    }

}
