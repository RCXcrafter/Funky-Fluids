package com.rcx.funkyfluids.blocks;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class RedstoneSuspensionBlock extends LiquidBlock {

	public RedstoneSuspensionBlock(Supplier<? extends FlowingFluid> pFluid, BlockBehaviour.Properties pProperties) {
		super(pFluid, pProperties);
	}

	@Override
	public boolean isSignalSource(BlockState pState) {
		return true;
	}

	@Override
	public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
		return 15 - pBlockState.getValue(LEVEL);
	}
}
