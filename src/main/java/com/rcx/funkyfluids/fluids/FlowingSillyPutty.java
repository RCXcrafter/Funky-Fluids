package com.rcx.funkyfluids.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class FlowingSillyPutty extends ForgeFlowingFluid.Flowing {

	public FlowingSillyPutty(Properties properties) {
		super(properties);
	}

	public void tick(Level pLevel, BlockPos pPos, FluidState pState) {
		FluidState fluidstate = this.getNewLiquid(pLevel, pPos, pLevel.getBlockState(pPos));
		if (fluidstate.getAmount() > 2)
			this.spread(pLevel, pPos, pState);
		if (!pState.isSource()) {
			int i = this.getSpreadDelay(pLevel, pPos, pState, fluidstate);
			if (fluidstate.isEmpty()) {
				pState = fluidstate;
				pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
			} else if (!fluidstate.equals(pState)) {
				pState = fluidstate;
				BlockState blockstate = fluidstate.createLegacyBlock();
				pLevel.setBlock(pPos, blockstate, 2);
				pLevel.scheduleTick(pPos, fluidstate.getType(), i);
				pLevel.updateNeighborsAt(pPos, blockstate.getBlock());
			}
		}
	}

	public FluidState getNewLiquid(LevelReader pLevel, BlockPos pPos, BlockState pBlockState) {
		int i = pBlockState.getFluidState().getAmount();
		int j = 0;
		int l = 0;

		for(Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos blockpos = pPos.relative(direction);
			BlockState blockstate = pLevel.getBlockState(blockpos);
			FluidState fluidstate = blockstate.getFluidState();
			if (fluidstate.getType().isSame(this) && this.canPassThroughWall(direction, pLevel, pPos, pBlockState, blockpos, blockstate)) {
				if (fluidstate.isSource() && net.minecraftforge.event.ForgeEventFactory.canCreateFluidSource(pLevel, blockpos, blockstate, fluidstate.canConvertToSource(pLevel, blockpos))) {
					++j;
				}
				++l;
				i = Math.max(i, fluidstate.getAmount());
			}
		}

		if (j >= 2) {
			BlockState blockstate1 = pLevel.getBlockState(pPos.below());
			FluidState fluidstate1 = blockstate1.getFluidState();
			if (blockstate1.getMaterial().isSolid() || this.isSourceBlockOfThisType(fluidstate1)) {
				return this.getSource(false);
			}
		}

		BlockPos blockpos1 = pPos.above();
		BlockState blockstate2 = pLevel.getBlockState(blockpos1);
		FluidState fluidstate2 = blockstate2.getFluidState();
		if (!fluidstate2.isEmpty() && fluidstate2.getType().isSame(this) && this.canPassThroughWall(Direction.UP, pLevel, pPos, pBlockState, blockpos1, blockstate2)) {
			return this.getFlowing(8, true);
		} else {
			int k = i - this.getDropOff(pLevel) + (l > 1 ? 1 : 0);
			return k <= 0 ? Fluids.EMPTY.defaultFluidState() : this.getFlowing(k, false);
		}
	}
}
