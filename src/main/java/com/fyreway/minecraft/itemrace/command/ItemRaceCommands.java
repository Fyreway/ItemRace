package com.fyreway.minecraft.itemrace.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ItemRaceCommands {
    public static void registerAll() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            SuperEnchantCommand.register(dispatcher, registryAccess);
            SuperRegretCommand.register(dispatcher, registryAccess);
        });
    }
}
