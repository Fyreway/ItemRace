package item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class SuperEnchantmentCatalyst extends Item {
    public SuperEnchantmentCatalyst() {
        super(new FabricItemSettings().rarity(Rarity.UNCOMMON).maxCount(1).fireproof());
    }
}
