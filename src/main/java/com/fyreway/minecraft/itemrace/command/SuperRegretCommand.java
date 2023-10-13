package com.fyreway.minecraft.itemrace.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.fyreway.minecraft.itemrace.item.ItemRaceItems;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

import static com.fyreway.minecraft.itemrace.enchantment.ItemRaceEnchantmentHelper.canSuperRegret;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SuperRegretCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("superregret")
                .then(argument("enchantment", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT))
                        .executes(ctx -> SuperRegretCommand.execute(ctx.getSource(), RegistryEntryArgumentType.getEnchantment(ctx, "enchantment")))));
    }

    private static int execute(ServerCommandSource source, RegistryEntry<Enchantment> entry) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayerOrThrow();
        PlayerInventory inv = player.getInventory();
        Enchantment enchantment = entry.value();

        int slot = inv.getSlotWithStack(new ItemStack(ItemRaceItems.ORB_OF_REGRET));

        if (slot != -1) {
            ItemStack mainHandStack = inv.getMainHandStack();
            if (mainHandStack.isEmpty() || (!mainHandStack.isEnchantable() && !mainHandStack.hasEnchantments()))
                throw new CommandException(Text.translatable("command.superregret.error.invalid_item"));
            if (!canSuperRegret(mainHandStack, enchantment))
                throw new CommandException(Text.translatable("command.superregret.error.invalid_enchantment", enchantment.getName(1), mainHandStack.getName()));
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(mainHandStack);
            enchantments.put(enchantment, enchantments.get(enchantment) - 1);
            EnchantmentHelper.set(enchantments, mainHandStack);
            inv.removeStack(slot);
            player.playSound(SoundEvents.BLOCK_GRINDSTONE_USE, SoundCategory.MASTER, 5f, 1f);
            player.sendMessage(
                    Text.translatable("command.superregret.success",
                            enchantment.getName(1),
                            mainHandStack.getName()));
            ItemStack goldMedalStack = new ItemStack(ItemRaceItems.GOLD_MEDAL);
            if (!inv.insertStack(goldMedalStack)) {
                Vec3d pos = player.getPos();
                ServerWorld world = player.getServerWorld();
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), goldMedalStack));
            }
        } else
            throw new CommandException(Text.translatable("command.superregret.error.no_orb"));
        return 0;
    }
}
