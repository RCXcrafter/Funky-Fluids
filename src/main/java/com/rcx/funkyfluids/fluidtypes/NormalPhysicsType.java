package com.rcx.funkyfluids.fluidtypes;

import com.rcx.funkyfluids.util.FunkyFluidsUtil;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class NormalPhysicsType extends FunkyFluidType {

	public NormalPhysicsType(Properties properties, FunkyFluidInfo info) {
		super(properties, info);
	}

	public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
		entity.setOnGround(true);
		entity.setSwimming(false);

		float f2 = 0.6f;
		float f3 = entity.onGround() ? f2 * 0.91F : 0.91F;
		Vec3 vec35 = entity.handleRelativeFrictionAndCalculateMovement(movementVector, f2);
		double d2 = vec35.y;
		if (entity.hasEffect(MobEffects.LEVITATION)) {
			d2 += (0.05D * (double)(entity.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - vec35.y) * 0.2D;
			entity.resetFallDistance();
		} else if (entity.level().isClientSide() && !entity.level().hasChunkAt(entity.blockPosition())) {
			if (entity.getY() > (double)entity.level().getMinBuildHeight()) {
				d2 = -0.1D;
			} else {
				d2 = 0.0D;
			}
		} else if (!entity.isNoGravity()) {
			d2 -= gravity;
		}

		if (entity.shouldDiscardFriction()) {
			vec35 = new Vec3(vec35.x, d2, vec35.z);
		} else {
			vec35 = new Vec3(vec35.x * (double)f3, d2 * (double)0.98F, vec35.z * (double)f3);
		}
		entity.setDeltaMovement(vec35);

		if (entity.isSprinting())
			FunkyFluidsUtil.spawnSprintParticle(entity);
		return true;
	}
}
