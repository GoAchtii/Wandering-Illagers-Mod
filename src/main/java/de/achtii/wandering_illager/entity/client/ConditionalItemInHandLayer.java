package de.achtii.wandering_illager.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.achtii.wandering_illager.entity.custom.WanderingIllagerEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;

public class ConditionalItemInHandLayer<T extends WanderingIllagerEntity, M extends HierarchicalModel<T> & ArmedModel>
        extends ItemInHandLayer<T, M> {

    public ConditionalItemInHandLayer(RenderLayerParent<T, M> parent, ItemInHandRenderer itemRenderer) {
        super(parent, itemRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity,
                       float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isAggressive()) {
            super.render(poseStack, bufferSource, packedLight, entity,
                    limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }
    }
}