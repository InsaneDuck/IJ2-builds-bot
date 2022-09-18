package dev.insaneduck.bot.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import dev.insaneduck.bot.modal.Build;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class BuildListener extends ListenerAdapter
{
    HashMap<String, Build> builds;

    public BuildListener(String builds)
    {
        this.builds = getBuilds(builds);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        String command = event.getName();
        System.out.println(command);
        switch (command)
        {
            case "build":
            {
                String option = event.getOption("character").getAsString();
                event.reply(builds.get(option).toString()).setEphemeral(true).queue();
            }
            case "update":
            {
                String option = event.getOption("json").getAsString();
                //todo update data
                event.reply("updated").queue();
            }
            case "add":
            {
                String option =event.getOption("json").getAsString();
                //todo add data
                event.reply("added").queue();
            }
        }

    }

    HashMap<String,Build> getBuilds(String json)
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<String,Build> b = new HashMap<>();
            JsonNode jsonNode = objectMapper.readTree(json);
            ArrayNode arrayNode = (ArrayNode) jsonNode.get("builds");
            for (JsonNode node : arrayNode)
            {
                Build build = objectMapper.treeToValue( node,Build.class);
                b.put(build.getCommand(),build);
            }
            return b;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
