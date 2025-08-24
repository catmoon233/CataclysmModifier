package net.exmo.cataclysm_modifier;

import com.github.L_Ender.cataclysm.init.ModGroup;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.mojang.logging.LogUtils;
import net.exmo.cataclysm_modifier.init.CMItemInit;
import net.exmo.exmodifier.Exmodifier;
import net.exmo.exmodifier.events.ExCustomTabEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Cataclysm_modifier.MODID)
public class Cataclysm_modifier {

    public static final String MODID = "cataclysm_modifier";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public final static RegistryObject<CreativeModeTab> ExModifierTab = CREATIVE_MODE_TABS.register("cataclysm_modifier_tab", () -> CreativeModeTab.builder()
            .icon(Exmodifier::getTabIcon)
            .withSearchBar()
            .title(Component.translatable("itemGroup.exmodifier_tab"))
            .displayItems((parameters, output) -> {
            }).build());

    public Cataclysm_modifier() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CMItemInit.REGISTRY.register(modEventBus);
        modEventBus.addListener(this::onCreativeTabBuild);

        CREATIVE_MODE_TABS.register(modEventBus);


    }

    public  void onCreativeTabBuild(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab().equals(ModGroup.ITEM.get())){
            CMItemInit.REGISTRY.getEntries().forEach(
                    e->{
                        event.accept(e.get());
                    }
            );
        }
    }

}
