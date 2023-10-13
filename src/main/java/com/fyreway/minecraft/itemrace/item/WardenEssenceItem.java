package com.fyreway.minecraft.itemrace.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class WardenEssenceItem extends Item {
    public WardenEssenceItem() {
        super(new FabricItemSettings().maxCount(64).rarity(Rarity.RARE));
    }
}
