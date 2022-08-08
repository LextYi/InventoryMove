package net.lextyi.inventorymove;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemGroup;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.lextyi.inventorymove.mixinterface.IKeyBinding;

public class InventoryMove implements ClientModInitializer
{
    public MinecraftClient MC = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient()
    {
        ClientTickEvents.START_CLIENT_TICK.register(client ->
        {
            Screen screen = MC.currentScreen;
            if(screen == null)
                return;

            if(!isAllowedScreen(screen))
                return;

            ArrayList<KeyBinding> keys =
                    new ArrayList<>(Arrays.asList(MC.options.keyForward,
                            MC.options.keyBack, MC.options.keyLeft, MC.options.keyRight));

            keys.add(MC.options.keySprint);
            keys.add(MC.options.keyJump);

            for(KeyBinding key : keys)
                key.setPressed(((IKeyBinding)key).isActallyPressed());
        });
    }

    private boolean isAllowedScreen(Screen screen)
    {
        if(screen instanceof AbstractInventoryScreen
                && !isCreativeSearchBarOpen(screen))
            return true;

        if(screen instanceof HandledScreen
                && !hasTextBox(screen))
            return true;

        return false;
    }

    private boolean isCreativeSearchBarOpen(Screen screen)
    {
        if(!(screen instanceof CreativeInventoryScreen))
            return false;

        CreativeInventoryScreen crInvScreen = (CreativeInventoryScreen)screen;
        return crInvScreen.getSelectedTab() == ItemGroup.SEARCH.getIndex();
    }

    private boolean hasTextBox(Screen screen)
    {
        return screen.children().stream()
                .anyMatch(e -> e instanceof TextFieldWidget);
    }
}
