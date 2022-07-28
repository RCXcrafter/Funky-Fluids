package com.rcx.funkyfluids.fluidtypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class LowFrictionType extends FunkyFluidType {

	public LowFrictionType(Properties properties, String name, int color) {
		super(properties, name, color);
	}

	public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
		/*//System.out.println("movement vector: " + movementVector);
		//System.out.println("speed: " + entity.getSpeed());
		//System.out.println("motion: " + Math.abs(entity.getDeltaMovement().length()));
		boolean flag = entity.getDeltaMovement().y <= 0.0D;
		double d9 = entity.getY();
		float f4 = 0.8F;
		//f4 /= 1 + Math.abs(entity.getDeltaMovement().length() * 4.0);
		//f4--;
		float f5 = 0.02F;
		float f6 = (float)EnchantmentHelper.getDepthStrider(entity);
		if (f6 > 3.0F) {
			f6 = 3.0F;
		}

		if (!entity.isOnGround()) {
			f6 *= 0.5F;
		}

		if (f6 > 0.0F) {
			f4 += (0.54600006F - f4) * f6 / 3.0F;
			f5 += (entity.getSpeed() - f5) * f6 / 3.0F;
		}

		if (entity.hasEffect(MobEffects.DOLPHINS_GRACE)) {
			f4 = 0.96F;
		}

		f5 *= (float)entity.getAttribute(ForgeMod.SWIM_SPEED.get()).getValue();
		entity.moveRelative(f5, movementVector);
		entity.move(MoverType.SELF, entity.getDeltaMovement());
		Vec3 vec36 = entity.getDeltaMovement();
		if (entity.horizontalCollision && entity.onClimbable()) {
			vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
		}

		entity.setDeltaMovement(vec36.multiply((double)f4, (double)0.8F, (double)f4));
		Vec3 vec32 = entity.getFluidFallingAdjustedMovement(gravity, flag, entity.getDeltaMovement());

		vec32 = vec32.scale(1.0 / (1 + Math.abs(entity.getDeltaMovement().length() * 8.0)));

		entity.setDeltaMovement(vec32);*/
		/*if (entity.horizontalCollision && entity.isFree(vec32.x, vec32.y + (double)0.6F - entity.getY() + d9, vec32.z)) {
			entity.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
		}*/
		entity.setOnGround(true);

		BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement(entity);
		float f2 = 0.4f;//entity.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement(entity)).getFriction(entity.level, this.getBlockPosBelowThatAffectsMyMovement(entity), entity);
		float f3 = entity.isOnGround() ? f2 * 0.91F : 0.91F;
		Vec3 vec35 = entity.handleRelativeFrictionAndCalculateMovement(movementVector, f2);
		double d2 = vec35.y;
		if (entity.hasEffect(MobEffects.LEVITATION)) {
			d2 += (0.05D * (double)(entity.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2D;
			entity.resetFallDistance();
		} else if (entity.level.isClientSide && !entity.level.hasChunkAt(blockpos)) {
			if (entity.getY() > (double)entity.level.getMinBuildHeight()) {
				d2 = -0.1D;
			} else {
				d2 = 0.0D;
			}
		} else if (!entity.isNoGravity()) {
			d2 -= gravity;
		}

		if (entity.shouldDiscardFriction()) {
			entity.setDeltaMovement(vec35.x, d2, vec35.z);
		} else {
			entity.setDeltaMovement(vec35.x * (double)f3, d2 * (double)0.98F, vec35.z * (double)f3);
		}

		return true;
	}

	public void setItemMovement(ItemEntity entity) {
		Vec3 vec3 = entity.getDeltaMovement();
		entity.setDeltaMovement(vec3.x * (double)0.99F, vec3.y + (double)(vec3.y < (double)0.06F ? 5.0E-4F : 0.0F), vec3.z * (double)0.99F);
	}

	protected BlockPos getBlockPosBelowThatAffectsMyMovement(LivingEntity entity) {
		return new BlockPos(entity.position().x, entity.getBoundingBox().minY - 0.5000001D, entity.position().z);
	}
}
