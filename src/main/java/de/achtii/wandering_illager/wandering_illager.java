package de.achtii.wandering_illager;

import com.mojang.logging.LogUtils;
import de.achtii.wandering_illager.entity.ModEntities;
import de.achtii.wandering_illager.entity.client.WanderingIllagerRenderer;
import de.achtii.wandering_illager.item.ModCreativeModeTabs;
import de.achtii.wandering_illager.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
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

    //Creates MODID and Events
    public static final String MODID = "wandering_illager";

    private static final Logger LOGGER = LogUtils.getLogger();

    public wandering_illager() {


        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
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


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.WANDERINGILLAGER.get(), WanderingIllagerRenderer::new);
        }
    }
}
