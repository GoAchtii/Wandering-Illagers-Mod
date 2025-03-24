package de.achtii.wandering_illager.entity.client;

import de.achtii.wandering_illager.wandering_illager;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

//Creates Model Layer of the mob
public class ModModelLayers {
        public static final ModelLayerLocation WANDERING_ILLAGER_LAYER = new ModelLayerLocation(
                new ResourceLocation(wandering_illager.MODID, "wandering_illager_layer"), "main");

}
