package com.rcx.funkyfluids.blocks;

import java.util.function.Supplier;

import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.fluidtypes.OobleckType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class OobleckBlock extends LiquidBlock {

	public OobleckBlock(Supplier<? extends FlowingFluid> pFluid, BlockBehaviour.Properties pProperties) {
		super(pFluid, pProperties);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		if (pContext instanceof EntityCollisionContext) {
			Entity entity = ((EntityCollisionContext) pContext).getEntity();
			//if (entity != null)
				//System.out.println("collision motion: " + Math.abs(entity.getDeltaMovement().length()));

			if (entity != null && entity.getFluidTypeHeight(FunkyFluidsResources.oobleck.TYPE.get()) < 0.2d && Math.abs(entity.getDeltaMovement().length()) > OobleckType.MIN_SPEED)
				return SolidLiquidBlock.SHAPES[pState.getValue(LEVEL)];
		}
		return Shapes.empty();
	}
}
