package item;

import com.fyreway.minecraft.itemrace.ItemRace;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ItemRaceItems {
    private static final Map<String, Item> map = new HashMap<>();
    public static final Item GOLD_MEDAL = add("gold_medal", new GoldMedalItem());
    public static final Item SUPER_ENCHANTMENT_CATALYST =
            add("super_enchantment_catalyst", new SuperEnchantmentCatalyst());

    private static Item add(String name, Item item) {
        map.put(name, item);
        return item;
    }

    public static void registerAll() {
        map.forEach((name, item) -> Registry.register(Registries.ITEM, new Identifier(ItemRace.MOD_ID, name), item));
    }
}
