package com.fyreway.minecraft.itemrace.state;

import com.fyreway.minecraft.itemrace.ItemRace;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.Objects;

public class RaceState extends PersistentState {
    private String raceWinner = "";

    public String getRaceWinner() {
        return raceWinner;
    }

    public void setRaceWinner(String raceWinner) {
        this.raceWinner = raceWinner;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putString("raceWinner", raceWinner);
        return nbt;
    }

    public static RaceState createFromNbt(NbtCompound nbt) {
        RaceState state = new RaceState();
        state.raceWinner = nbt.getString("raceWinner");
        return state;
    }

    public static RaceState getRaceState(MinecraftServer server) {
        PersistentStateManager manager = Objects.requireNonNull(
                server.getWorld(World.OVERWORLD)).getPersistentStateManager();

        return manager.getOrCreate(RaceState::createFromNbt, RaceState::new, ItemRace.MOD_ID);
    }
}
