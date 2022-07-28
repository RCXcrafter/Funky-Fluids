package com.rcx.funkyfluids.blocks;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SolidLiquidBlock extends LiquidBlock {

	public static final VoxelShape[] SHAPES = new VoxelShape[] {
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 13.4D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.6D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.8D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.3D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.5D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.7D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 0.9D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty()
	};

	public SolidLiquidBlock(Supplier<? extends FlowingFluid> pFluid, BlockBehaviour.Properties pProperties) {
		super(pFluid, pProperties);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPES[pState.getValue(LEVEL)];
	}
}
