package com.fyreway.minecraft.itemrace;

import com.fyreway.minecraft.itemrace.command.ItemRaceCommands;
import com.fyreway.minecraft.itemrace.item.ItemRaceItems;
import net.fabricmc.api.ModInitializer;

public class ItemRace implements ModInitializer {
    public static final String MOD_ID = "itemrace";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ItemRaceItems.init();
        ItemRaceCommands.init();
    }
}
