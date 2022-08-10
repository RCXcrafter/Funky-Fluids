package com.rcx.funkyfluids.entities;

import com.rcx.funkyfluids.FunkyFluidsResources;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class FallingSillyPuttyEntity extends FallingBlockEntity {

	private double bounceAmount = 0f;

	public FallingSillyPuttyEntity(EntityType<? extends FallingSillyPuttyEntity> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
		this.dropItem = false;
	}

	private FallingSillyPuttyEntity(Level pLevel, double pX, double pY, double pZ, BlockState pState) {
		this(FunkyFluidsResources.FALLING_SILLY_PUTTY.get(), pLevel);
		this.blockState = pState;
		this.blocksBuilding = true;
		this.setPos(pX, pY, pZ);
		this.setDeltaMovement(Vec3.ZERO);
		this.xo = pX;
		this.yo = pY;
		this.zo = pZ;
		this.setStartPos(this.blockPosition());
	}

	public static FallingSillyPuttyEntity fall(Level p_201972_, BlockPos p_201973_, BlockState p_201974_) {
		FallingSillyPuttyEntity fallingblockentity = new FallingSillyPuttyEntity(p_201972_, (double)p_201973_.getX() + 0.5D, (double)p_201973_.getY(), (double)p_201973_.getZ() + 0.5D, p_201974_.hasProperty(BlockStateProperties.WATERLOGGED) ? p_201974_.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false)) : p_201974_);
		p_201972_.setBlock(p_201973_, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		p_201972_.addFreshEntity(fallingblockentity);
		return fallingblockentity;
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
		if (pFallDistance > 2) {
			// invert Y motion, boost X and Z slightly
			Vec3 motion = getDeltaMovement();
			setDeltaMovement(motion.x / 0.95f, motion.y * -0.9, motion.z / 0.95f);
			bounceAmount = getDeltaMovement().y;
			fallDistance = 0f;
			hasImpulse = true;
			setOnGround(false);
			playSound(SoundEvents.SLIME_BLOCK_STEP, 1.0f, 1.0f);
		}
		return super.causeFallDamage(pFallDistance, pMultiplier, pSource);
	}

	@Override
	public void move(MoverType typeIn, Vec3 pos) {
		super.move(typeIn, pos);
		if (bounceAmount > 0) {
			Vec3 motion = getDeltaMovement();
			setDeltaMovement(motion.x, bounceAmount, motion.z);
			bounceAmount = 0;
		}
	}
}
