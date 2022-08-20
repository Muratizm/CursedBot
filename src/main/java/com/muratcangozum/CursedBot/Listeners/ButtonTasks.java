package com.muratcangozum.CursedBot.Listeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ButtonTasks extends ListenerAdapter {


    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        if(event.getComponentId().equals("delete")){

            event.getMessage().delete().queue();
        }

    }
}
