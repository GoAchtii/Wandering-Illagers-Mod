package de.achtii.wandering_illager.event;
import de.achtii.wandering_illager.wandering_illager;
import de.achtii.wandering_illager.entity.client.ModModelLayers;
import de.achtii.wandering_illager.entity.client.WanderingIllagerModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = wandering_illager.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.WANDERING_ILLAGER_LAYER, WanderingIllagerModel::createBodyLayer);
    }
}
