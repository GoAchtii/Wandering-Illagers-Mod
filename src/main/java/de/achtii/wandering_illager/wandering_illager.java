package de.achtii.wandering_illager;

import com.mojang.logging.LogUtils;
import de.achtii.wandering_illager.item.ModCreativeModeTabs;
import de.achtii.wandering_illager.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(wandering_illager.MODID)
public class wandering_illager {

    public static final String MODID = "wandering_illager";

    private static final Logger LOGGER = LogUtils.getLogger();

    public wandering_illager() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
        public void onServerStarting(ServerStartingEvent event) {
        }

        private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
