package com.fyreway.minecraft.itemrace;

import com.fyreway.minecraft.itemrace.command.ItemRaceCommands;
import com.fyreway.minecraft.itemrace.state.RaceState;
import com.fyreway.minecraft.itemrace.item.ItemRaceItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemRace implements ModInitializer {
    public static final String MOD_ID = "itemrace";
    @SuppressWarnings("unused")
    public static final Logger LOG = LogManager.getLogger("ItemRace");

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
