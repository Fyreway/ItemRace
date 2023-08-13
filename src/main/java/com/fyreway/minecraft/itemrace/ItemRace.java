package com.fyreway.minecraft.itemrace;

import com.fyreway.minecraft.itemrace.command.ItemRaceCommands;
import com.fyreway.minecraft.itemrace.state.RaceState;
import item.ItemRaceItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class ItemRace implements ModInitializer {
    public static final String MOD_ID = "itemrace";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ItemRaceItems.registerAll();
        ItemRaceCommands.registerAll();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> RaceState.getRaceState(server).markDirty());
    }
}
