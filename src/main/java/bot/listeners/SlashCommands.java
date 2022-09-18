package bot.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SlashCommands extends ListenerAdapter
{
    List<SlashCommandData> global = new ArrayList<>();
    List<SlashCommandData> guilds = new ArrayList<>();
    public SlashCommands(String commands)
    {
        getCommands(commands);
    }
    @Override
    public void onReady(@NotNull ReadyEvent event)
    {
        event.getJDA().updateCommands().addCommands(global).queue();
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event)
    {
        event.getGuild().updateCommands().addCommands(guilds).queue();
    }

    public void getCommands(String json)
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            ArrayNode global = (ArrayNode) jsonNode.get("global");
            ArrayNode guilds = (ArrayNode) jsonNode.get("guilds");
            global.forEach(node -> {
                this.global.add(addCommand(node));
            });

            guilds.forEach(node -> {
                this.guilds.add(addCommand(node));
            });
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    private SlashCommandData addCommand(JsonNode node)
    {
        String command = node.get("command").asText();
        String description = node.get("description").asText();
        SlashCommandData slashCommandData = Commands.slash(command,description);
        ArrayNode options = (ArrayNode) node.get("options");
        options.forEach(option -> {
            String optionString = option.get("option").asText();
            String optionDescription = option.get("description").asText();
            boolean optionRequired = option.get("required").asBoolean();
            OptionData optionData = new OptionData(OptionType.STRING,optionString,optionDescription,optionRequired);
            slashCommandData.addOptions(optionData);
        });
        return slashCommandData;
    }
}
