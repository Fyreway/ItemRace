package com.fyreway.minecraft.itemrace.mixin;

import com.fyreway.minecraft.itemrace.item.ItemRaceItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class QuickAddEnchantmentMixin {
    @Inject(method = "onClicked", at = @At("HEAD"))
    private void onClicked(ItemStack stack,
                           ItemStack otherStack,
                           Slot slot,
                           ClickType clickType,
                           PlayerEntity player,
                           StackReference cursorStackReference,
                           CallbackInfoReturnable<Boolean> cir) {
        if (otherStack.isOf(ItemRaceItems.MENDING_TOKEN) && Enchantments.MENDING.isAcceptableItem(stack)
                && EnchantmentHelper.isCompatible(
                EnchantmentHelper.get(stack).keySet(), Enchantments.MENDING)) {
            stack.addEnchantment(Enchantments.MENDING, 1);
            NbtCompound display = stack.getOrCreateSubNbt("display");
            NbtList lore = new NbtList();
            if (display.contains("Lore")) {
                lore = display.getList("Lore", NbtElement.STRING_TYPE);
            }

            lore.add(NbtString.of(Text.Serializer.toJson(Text.translatable("item.itemrace.mending_token.success"))));
            display.put("Lore", lore);

            otherStack.setCount(0);
            cursorStackReference.set(ItemStack.EMPTY);
        }
    }
}
