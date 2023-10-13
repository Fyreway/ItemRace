package com.fyreway.minecraft.itemrace.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.fyreway.minecraft.itemrace.item.ItemRaceItems;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.Map;

import static com.fyreway.minecraft.itemrace.enchantment.ItemRaceEnchantmentHelper.canSuperEnchant;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SuperEnchantCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher,
                                CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("superenchant")
                .then(argument("enchantment", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT))
                                .executes(ctx -> SuperEnchantCommand.execute(ctx.getSource(), RegistryEntryArgumentType.getEnchantment(ctx, "enchantment")))));
    }

    private static int execute(ServerCommandSource source,
                               RegistryEntry<Enchantment> entry) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayerOrThrow();
        PlayerInventory inv = player.getInventory();
        Enchantment enchantment = entry.value();

        int slot = inv.getSlotWithStack(new ItemStack(ItemRaceItems.GOLD_MEDAL));

        if (slot != -1) {
            ItemStack mainHandStack = inv.getMainHandStack();
            if (mainHandStack.isEmpty() || (!mainHandStack.isEnchantable() && !mainHandStack.hasEnchantments()))
                throw new CommandException(Text.translatable("command.superenchant.error.invalid_item"));
            if (!canSuperEnchant(mainHandStack, enchantment))
                throw new CommandException(Text.translatable("command.superenchant.error.invalid_enchantment", mainHandStack.getName(), enchantment.getName(1)));
            if (EnchantmentHelper.getLevel(enchantment, mainHandStack) == 0)
                mainHandStack.addEnchantment(enchantment, 1);
            else {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(mainHandStack);
                enchantments.put(enchantment, enchantments.get(enchantment) + 1);
                EnchantmentHelper.set(enchantments, mainHandStack);
            }
            inv.removeStack(slot);
            player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.MASTER, 5f, 1f);
            player.sendMessage(
                    Text.translatable("command.superenchant.success",
                            mainHandStack.getName(),
                            enchantment.getName(EnchantmentHelper.getLevel(enchantment, mainHandStack))));
        } else
            throw new CommandException(Text.translatable("command.superenchant.error.no_medal"));
        return 0;
    }
}
