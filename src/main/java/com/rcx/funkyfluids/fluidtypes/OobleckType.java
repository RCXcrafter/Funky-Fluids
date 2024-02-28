package com.rcx.funkyfluids.fluidtypes;

import com.rcx.funkyfluids.util.FunkyFluidsUtil;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class OobleckType extends FunkyFluidType {

	public static final double MIN_SPEED = 0.23D;
	public static final float FRICTION = 0.6F;
	public static final double RESISTANCE_MULTIPLIER = 20.0D;

	public OobleckType(Properties properties, FunkyFluidInfo info) {
		super(properties, info);
	}

	public boolean canSwim(Entity entity) {
		double depth = entity.getFluidTypeHeight(this);
		return !(depth > 0.0d && depth < 0.2d && Math.abs(entity.getDeltaMovement().length()) > MIN_SPEED / 2.0D);
	}

	public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
		double depth = entity.getFluidTypeHeight(this);
		if (depth > 0.0d && depth < 0.2d && Math.abs(entity.getDeltaMovement().length()) > MIN_SPEED / 2.0D) { //if entity is moving fast enough and is at the surface of fluid, behave as a solid
			entity.setOnGround(true);
			entity.setSwimming(false);

			float f3 = entity.onGround() ? FRICTION * 0.91F : 0.91F;
			Vec3 vec35 = entity.handleRelativeFrictionAndCalculateMovement(movementVector, FRICTION);
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
		} else { //else, behave as a fluid
			boolean flag = entity.getDeltaMovement().y <= 0.0D;
			double d9 = entity.getY();
			float f4 = entity.isSprinting() ? 0.9F : 0.8F;
			float f5 = 0.02F;
			float f6 = (float)EnchantmentHelper.getDepthStrider(entity);
			if (f6 > 3.0F) {
				f6 = 3.0F;
			}

			if (!entity.onGround()) {
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

			vec32 = vec32.scale(1.0D / (1.0D + Math.abs(entity.getDeltaMovement().length() * RESISTANCE_MULTIPLIER))); //add more friction depending on speed of entity

			entity.setDeltaMovement(vec32);
			if (entity.horizontalCollision && entity.isFree(vec32.x, vec32.y + (double)0.6F - entity.getY() + d9, vec32.z)) {
				entity.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
			}
		}
		return true;
	}

	public void setItemMovement(ItemEntity entity) {
		Vec3 motion = entity.getDeltaMovement();
		motion = new Vec3(motion.x * (double)0.99F, motion.y + (double)(motion.y < (double)0.06F ? 5.0E-4F : 0.0F), motion.z * (double)0.99F);
		entity.setDeltaMovement(motion.scale(1.0D / (1.0D + Math.abs(motion.length() * RESISTANCE_MULTIPLIER)))); //add more friction depending on speed of entity
	}
}
