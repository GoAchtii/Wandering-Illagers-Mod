package de.achtii.wandering_illager.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.achtii.wandering_illager.wandering_illager;
import de.achtii.wandering_illager.entity.custom.WanderingIllagerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WanderingIllagerRenderer extends MobRenderer<WanderingIllagerEntity, WanderingIllagerModel<WanderingIllagerEntity>> {
    public WanderingIllagerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WanderingIllagerModel<>(pContext.bakeLayer(ModModelLayers.WANDERING_ILLAGER_LAYER)), 0.5f);
        
    }

    @Override
    public ResourceLocation getTextureLocation(WanderingIllagerEntity pEntity) {
        return new ResourceLocation(wandering_illager.MODID, "textures/entity/wandering_illager.png");
    }

    @Override
    public void render(WanderingIllagerEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
