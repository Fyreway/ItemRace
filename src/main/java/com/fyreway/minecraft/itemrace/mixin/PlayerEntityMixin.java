package com.fyreway.minecraft.itemrace.mixin;

import com.fyreway.minecraft.itemrace.state.RaceState;
import com.fyreway.minecraft.itemrace.item.ItemRaceItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract PlayerInventory getInventory();

    @Shadow public abstract String getEntityName();

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        World world = ((Entity) (Object) this).getWorld();
        if (world.isClient()) return;
        RaceState state = RaceState.getRaceState(
                Objects.requireNonNull(world.getServer()));
        if (getInventory().contains(new ItemStack(ItemRaceItems.SUPER_ENCHANTMENT_CATALYST))
                && state.getRaceWinner().isEmpty()) {
            state.setRaceWinner(getEntityName());
            state.markDirty();
            ItemStack goldMedalStack = new ItemStack(ItemRaceItems.GOLD_MEDAL);
            if (!getInventory().insertStack(goldMedalStack)) {
                Vec3d pos = ((Entity) (Object) this).getPos();
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), goldMedalStack));
            }
            world.getPlayers().forEach(player -> {
                player.sendMessage(Text.translatable("message.itemrace.player_won", getEntityName()));
                player.playSound(SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.PLAYERS, 10f, 1f);
            });
        }
    }
}
