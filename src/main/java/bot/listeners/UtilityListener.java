package bot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UtilityListener extends ListenerAdapter
{
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        String command = event.getName();
        if(command.equals("clear"))
        {
            TextChannel channel = (TextChannel) event.getMessageChannel();
            List<Message> messageList;
            MessageHistory messageHistory = new MessageHistory(channel);
            messageList = messageHistory.retrievePast(100).complete();
            channel.deleteMessages(messageList).queue();
        }
    }
}
