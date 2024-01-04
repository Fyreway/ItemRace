package com.fyreway.minecraft.itemrace.item;

import com.fyreway.minecraft.itemrace.ItemRace;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ItemRaceItems {
    private static final Map<String, Item> map = new HashMap<>();
    public static final Item GOLD_MEDAL =
            add("gold_medal", new GlintItem(new FabricItemSettings().rarity(Rarity.EPIC).fireproof().maxCount(1)));
    public static final Item SUPER_ENCHANTMENT_CATALYST =
            add("super_enchantment_catalyst", new GlintItem(new FabricItemSettings().rarity(Rarity.UNCOMMON)));
    public static final Item ORB_OF_REGRET =
            add("orb_of_regret", new Item(new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item DEEPSLATE_DEPTH_STAR =
            add("deepslate_depth_star", new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)));
    public static final Item DEPTH_STAR =
            add("depth_star", new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)));
    public static final Item WARDEN_ESSENCE =
            add("warden_essence", new Item(new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item MENDING_TOKEN =
            add("mending_token", new GlintItem(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(1)));

    private static Item add(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ItemRace.MOD_ID, name), item);
    }

    public static void init() {}
}
