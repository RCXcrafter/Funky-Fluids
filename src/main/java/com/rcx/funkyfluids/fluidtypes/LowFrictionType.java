package com.rcx.funkyfluids.fluidtypes;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

public class LowFrictionType extends FunkyFluidType {

	public LowFrictionType(Properties properties, FunkyFluidInfo info) {
		super(properties, info);
	}

	public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
		boolean flag = entity.getDeltaMovement().y <= 0.0D;
		double d9 = entity.getY();
		float f4 = entity.isSprinting() ? 0.9F : 0.8F;
		float f5 = 0.05F;
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
		entity.setDeltaMovement(vec32);
		if (entity.horizontalCollision && entity.isFree(vec32.x, vec32.y + (double)0.6F - entity.getY() + d9, vec32.z)) {
			entity.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
		}
		return true;
	}

	public void setItemMovement(ItemEntity entity) {
		Vec3 vec3 = entity.getDeltaMovement();
		entity.setDeltaMovement(vec3.x * (double)0.99F, vec3.y + (double)(vec3.y < (double)0.06F ? 5.0E-4F : 0.0F), vec3.z * (double)0.99F);
	}
}
