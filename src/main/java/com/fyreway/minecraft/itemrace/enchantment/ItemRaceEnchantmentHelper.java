package com.fyreway.minecraft.itemrace.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class ItemRaceEnchantmentHelper {
    public static boolean canSuperEnchant(ItemStack item, Enchantment enchantment) {
        for (Enchantment e : EnchantmentHelper.get(item).keySet())
            if (!enchantment.canCombine(e) && !e.equals(enchantment)) return false;
        ItemStack item2 = new ItemStack(item.getItem());
        for (Enchantment e : EnchantmentHelper.get(item).keySet()) {
            if (e.equals(enchantment)) continue;
            item2.addEnchantment(e, 1);
        }
        if (EnchantmentHelper.getLevel(enchantment, item) > enchantment.getMaxLevel() + 1) return false;
        return enchantment.isAcceptableItem(item2);
    }

    public static boolean canSuperRegret(ItemStack item, Enchantment enchantment) {
        // As per CultistOwl's request, this check is not nice to stupid people.
        return EnchantmentHelper.getLevel(enchantment, item) > enchantment.getMaxLevel();
    }
}
