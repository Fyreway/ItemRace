package item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class GoldMedalItem extends Item {
    public GoldMedalItem() {
        super(new FabricItemSettings().rarity(Rarity.EPIC).fireproof().maxCount(1));
    }
}
