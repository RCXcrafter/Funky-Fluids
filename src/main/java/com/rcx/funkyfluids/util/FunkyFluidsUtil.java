package com.rcx.funkyfluids.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FunkyFluidsUtil {

	public static void spawnSprintParticle(LivingEntity entity) {
		int i = Mth.floor(entity.getX());
		int j = Mth.floor(entity.getY() - (double)0.2F);
		int k = Mth.floor(entity.getZ());
		BlockPos blockpos = new BlockPos(i, j, k);
		BlockState blockstate = entity.level().getBlockState(blockpos);
		if(!blockstate.addRunningEffects(entity.level(), blockpos, entity)) {
			Vec3 vec3 = entity.getDeltaMovement();
			entity.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(blockpos), entity.getX() + (entity.level().getRandom().nextDouble() - 0.5D) * (double)entity.getBbWidth(), entity.getY() + 0.1D, entity.getZ() + (entity.level().getRandom().nextDouble() - 0.5D) * (double)entity.getBbWidth(), vec3.x * -4.0D, 1.5D, vec3.z * -4.0D);
		}
	}
}
