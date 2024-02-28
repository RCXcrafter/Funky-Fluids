package com.rcx.funkyfluids.entities;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

@OnlyIn(Dist.CLIENT)
public class FallingSillyPuttyRenderer extends EntityRenderer<FallingSillyPuttyEntity> {

	private final BlockRenderDispatcher dispatcher;

	public FallingSillyPuttyRenderer(Context pContext) {
		super(pContext);
		this.shadowRadius = 0.5F;
		this.dispatcher = pContext.getBlockRenderDispatcher();
	}

	@Override
	public void render(FallingSillyPuttyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
		BlockState blockstate = pEntity.getBlockState();
		Level level = pEntity.level();
		if (blockstate != level.getBlockState(pEntity.blockPosition())/* && blockstate.getRenderShape() != RenderShape.INVISIBLE*/) {
			pMatrixStack.pushPose();
			BlockPos blockpos = BlockPos.containing(pEntity.getX(), pEntity.getBoundingBox().maxY, pEntity.getZ());
			pMatrixStack.translate(-0.5D, 0.0D, -0.5D);
			var model = this.dispatcher.getBlockModel(blockstate);
			for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(pEntity.getStartPos())), ModelData.EMPTY))
				this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, pMatrixStack, pBuffer.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(pEntity.getStartPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
			pMatrixStack.popPose();
			super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(FallingSillyPuttyEntity pEntity) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
