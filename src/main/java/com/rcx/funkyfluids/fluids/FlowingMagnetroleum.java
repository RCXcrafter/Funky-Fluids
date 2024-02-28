package com.rcx.funkyfluids.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class FlowingMagnetroleum extends ForgeFlowingFluid.Flowing {

	public FlowingMagnetroleum(Properties properties) {
		super(properties);
	}

	@Override
	public Vec3 getFlow(BlockGetter pBlockReader, BlockPos pPos, FluidState pFluidState) {
		double d0 = 0.0D;
		double d1 = 0.0D;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for(Direction direction : Direction.Plane.HORIZONTAL) {
			blockpos$mutableblockpos.setWithOffset(pPos, direction);
			FluidState fluidstate = pBlockReader.getFluidState(blockpos$mutableblockpos);
			if (this.affectsFlow(fluidstate)) {
				float f = fluidstate.getOwnHeight();
				float f1 = 0.0F;
				if (f == 0.0F) {
					if (!pBlockReader.getBlockState(blockpos$mutableblockpos).blocksMotion()) {
						BlockPos blockpos = blockpos$mutableblockpos.below();
						FluidState fluidstate1 = pBlockReader.getFluidState(blockpos);
						if (this.affectsFlow(fluidstate1)) {
							f = fluidstate1.getOwnHeight();
							if (f > 0.0F) {
								f1 = pFluidState.getOwnHeight() - (f - 0.8888889F);
							}
						}
					}
				} else if (f > 0.0F) {
					f1 = pFluidState.getOwnHeight() - f;
				}

				if (f1 != 0.0F) {
					d0 += (double)((float)direction.getStepX() * f1);
					d1 += (double)((float)direction.getStepZ() * f1);
				}
			}
		}

		Vec3 vec3 = new Vec3(d0, 0.0D, d1);
		if (pFluidState.getValue(FALLING)) {
			vec3 = vec3.normalize().add(0.0D, -6.0D, 0.0D);
		}

		return vec3.normalize();
	}
}
