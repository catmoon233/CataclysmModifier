package net.exmo.cataclysm_modifier.init;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.items.The_Incinerator;
import net.exmo.cataclysm_modifier.Cataclysm_modifier;
import net.exmo.cataclysm_modifier.content.item.The_Abyss_Incinerator;
import net.exmo.cataclysm_modifier.content.item.The_Soul_Incinerator;
import net.exmo.exmodifier.Exmodifier;
import net.exmo.exmodifier.init.RegisterOther;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CMItemInit {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Cataclysm_modifier.MODID);
    public static final RegistryObject<Item> THE_Soul_INCINERATOR = REGISTRY.register("the_soul_incinerator", () -> new The_Soul_Incinerator((new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> THE_Abyss_INCINERATOR = REGISTRY.register("the_abyss_incinerator", () -> new The_Abyss_Incinerator((new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
}
