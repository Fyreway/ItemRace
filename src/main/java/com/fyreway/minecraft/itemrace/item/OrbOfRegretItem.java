package com.fyreway.minecraft.itemrace.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class OrbOfRegretItem extends Item {
    public OrbOfRegretItem() {
        super(new FabricItemSettings().fireproof().maxCount(1).rarity(Rarity.RARE));
    }
}
