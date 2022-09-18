package bot.main;

import bot.listeners.BuildListener;
import bot.listeners.SlashCommands;
import bot.listeners.UtilityListener;
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
    public static void main(String[] args) throws IOException, LoginException
    {
        if(args.length>0)
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode config = objectMapper.readTree(new File(args[0]));
            Bot bot = new Bot(config.get("TOKEN").asText(),config.get("commandsJSON").asText(),config.get("buildsJSON").asText());
        }
        else
        {
            System.out.println("input config file");
        }
    }

    public Bot(String TOKEN,String commandsJSON, String buildsJSON) throws IOException, LoginException
    {
        String commands = FileUtils.readFileToString(new File(commandsJSON), StandardCharsets.UTF_8);
        String builds = FileUtils.readFileToString(new File(buildsJSON), StandardCharsets.UTF_8);
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.listening("build commands"));
        builder.addEventListeners(new SlashCommands(commands),new BuildListener(builds),new UtilityListener());
        ShardManager shardManager = builder.build();
    }

}
