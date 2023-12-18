package com.fyreway.minecraft.itemrace.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class SuperEnchantmentCatalystItem extends Item {
    public SuperEnchantmentCatalystItem() {
        super(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(16).fireproof());
    }
}
