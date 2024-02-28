package com.rcx.funkyfluids.fluidtypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class LowGravityType extends FunkyFluidType {

	public LowGravityType(Properties properties, FunkyFluidInfo info) {
		super(properties, info);
	}

	public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
		entity.setOnGround(true);

		BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement(entity);
		float f2 = 0.4f;
		float f3 = entity.onGround() ? f2 * 0.91F : 0.91F;
		Vec3 vec35 = entity.handleRelativeFrictionAndCalculateMovement(movementVector, f2);
		double d2 = vec35.y;
		if (entity.hasEffect(MobEffects.LEVITATION)) {
			d2 += (0.05D * (double)(entity.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2D;
			entity.resetFallDistance();
		} else if (entity.level().isClientSide() && !entity.level().hasChunkAt(blockpos)) {
			if (entity.getY() > (double)entity.level().getMinBuildHeight()) {
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
		return BlockPos.containing(entity.position().x, entity.getBoundingBox().minY - 0.5000001D, entity.position().z);
	}
}
