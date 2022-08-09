package com.rcx.funkyfluids.blocks;

import java.util.function.Supplier;

import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.entities.FallingSillyPuttyEntity;
import com.rcx.funkyfluids.fluidtypes.SillyPuttyType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidInteractionRegistry;

public class SillyPuttyBlock extends LiquidBlock implements Fallable {

	public static final VoxelShape[] SHAPES = new VoxelShape[] {
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 13.4D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 11.6D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 9.8D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 6.3D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 4.5D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 2.7D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 0.9D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty(),
			Shapes.empty()
	};

	public static final VoxelShape INSIDE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public SillyPuttyBlock(Supplier<? extends FlowingFluid> pFluid, BlockBehaviour.Properties pProperties) {
		super(pFluid, pProperties);
		pProperties.sound(SoundType.SLIME_BLOCK);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		if (pContext instanceof EntityCollisionContext) {
			Entity entity = ((EntityCollisionContext) pContext).getEntity();
			if (entity != null && entity.getFluidTypeHeight(FunkyFluidsResources.SILLY_PUTTY.TYPE.get()) < 0.2d && Math.abs(entity.getDeltaMovement().length()) > SillyPuttyType.MIN_SPEED)
				return SHAPES[pState.getValue(LEVEL)];
		}
		return Shapes.empty();
	}

	@Override
	public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
		if(canFall(pState, pLevel, pPos)) {
			pLevel.scheduleTick(pPos, this, this.getDelayAfterPlace(pLevel));
		} else if (!FluidInteractionRegistry.canInteract(pLevel, pPos) && !pLevel.getFluidTicks().hasScheduledTick(pPos, pState.getFluidState().getType())) {
			int delay = getFluid().getTickDelay(pLevel);
			//if (!canFall(pState, pLevel, pPos.below()))
			//delay *= 3;
			pLevel.scheduleTick(pPos, pState.getFluidState().getType(), delay);
		}
	}

	@Override
	public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
		if(canFall(pState, pLevel, pCurrentPos)) {
			pLevel.scheduleTick(pCurrentPos, this, this.getDelayAfterPlace(pLevel));

		} else if ((pState.getFluidState().isSource() || pFacingState.getFluidState().isSource()) && !pLevel.getFluidTicks().hasScheduledTick(pCurrentPos, pState.getFluidState().getType())) {
			int delay = getFluid().getTickDelay(pLevel);
			//if (canFall(pState, pLevel, pCurrentPos.below()))
			//delay *= 3;
			pLevel.scheduleTick(pCurrentPos, pState.getFluidState().getType(), delay);
		}
		return pState;
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (canFall(state, level, pos)) {
			FallingSillyPuttyEntity.fall(level, pos, state);
		}
	}

	public int getDelayAfterPlace(LevelAccessor pLevel) {
		return getFluid().getTickDelay(pLevel) * 3;
	}

	public boolean canFall(BlockState state, LevelAccessor level, BlockPos pos) {
		BlockState below = level.getBlockState(pos.below());
		return (below.isAir() || below.is(BlockTags.FIRE) || below.getMaterial().isReplaceable())
				&& below.getBlock() != this
				&& !isSupported(level, pos)
				&& pos.getY() >= level.getMinBuildHeight();
	}

	public boolean isSupported(LevelAccessor level, BlockPos pos) {
		for(Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos blockpos = pos.relative(direction);
			BlockState state = level.getBlockState(blockpos);
			if (state.getBlock() == this || state.isFaceSturdy(level, blockpos, direction.getOpposite()))
				return true;
		}
		return false;
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entity) {
		if (entity.isSuppressingBounce() || !(entity instanceof LivingEntity) && !(entity instanceof ItemEntity)) {
			super.updateEntityAfterFallOn(worldIn, entity);
			// this is mostly needed to prevent XP orbs from bouncing. which completely breaks the game.
			return;
		}

		Vec3 vec3d = entity.getDeltaMovement();

		if (vec3d.y < 0 && Math.abs(vec3d.length()) > SillyPuttyType.MIN_SPEED) {
			double speed = entity instanceof LivingEntity ? 1.0D : 0.8D;
			entity.setDeltaMovement(vec3d.x, -vec3d.y * speed, vec3d.z);
			entity.fallDistance = 0;
			if (entity instanceof ItemEntity) {
				entity.setOnGround(false);
			}
		} else {
			super.updateEntityAfterFallOn(worldIn, entity);
		}
	}

	@Override
	public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
		// no fall damage
		entityIn.causeFallDamage(fallDistance, 0.0F, DamageSource.FALL);
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isClientSide() && !entityIn.isSuppressingBounce() && Math.abs(entityIn.getDeltaMovement().length()) > SillyPuttyType.MIN_SPEED && worldIn.getEntities(null, SolidLiquidBlock.SHAPES[state.getValue(LEVEL)].bounds().move(pos)).contains(entityIn)) {
			if (worldIn.getEntities(null, INSIDE_SHAPE.bounds().move(pos)).contains(entityIn)) {
				entityIn.setDeltaMovement(entityIn.getDeltaMovement().scale(0.5D));
				return;
			}
			if (entityIn.isInFluidType(FunkyFluidsResources.SILLY_PUTTY.TYPE.get()))
				return;
			Vec3 entityPosition = entityIn.position();
			Vec3 direction = entityPosition.subtract(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
			// only bounce if within the block height, prevents bouncing on top or from two blocks vertically
			if (direction.y() < 0.9365 && direction.y() >= -0.0625) {
				// bounce the current speed, slightly smaller to prevent infinite bounce
				double velocity = entityPosition.subtract(entityIn.xo, entityIn.yo, entityIn.zo).length() * 0.95;
				// determine whether we bounce in the X or the Z direction, we want whichever is bigger
				Vec3 motion = entityIn.getDeltaMovement();
				double absX = Math.abs(direction.x());
				double absZ = Math.abs(direction.z());
				if (absX > absZ) {
					// but don't bounce past the halfway point in the block, to avoid bouncing twice
					if (absZ < 0.495) {
						entityIn.setDeltaMovement(new Vec3(velocity * Math.signum(direction.x()), motion.y(), motion.z()));
						entityIn.hurtMarked = true;
						if (velocity > 0.1) {
							worldIn.playSound(null, pos, getSoundType(state, worldIn, pos, entityIn).getStepSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
						}
					}
				} else {
					if (absX < 0.495) {
						entityIn.setDeltaMovement(new Vec3(motion.x(), motion.y(), velocity * Math.signum(direction.z())));
						entityIn.hurtMarked = true;
						if (velocity > 0.1) {
							worldIn.playSound(null, pos, getSoundType(state, worldIn, pos, entityIn).getStepSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
						}
					}
				}
			}
		}
	}
}
