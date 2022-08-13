package com.rcx.funkyfluids.util;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;

public class FunkyFluidsMaterials {

	public static final Material OOBLECK_MATERIAL = new FunkyFluidsMaterials.Builder(MaterialColor.WOOL).notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
	public static final Material RED_FLUID_MATERIAL = new FunkyFluidsMaterials.Builder(MaterialColor.COLOR_RED).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
	public static final Material LIQUID_CRYSTAL_MATERIAL = new FunkyFluidsMaterials.Builder(MaterialColor.COLOR_PURPLE).notPushable().liquid().build();
	public static final Material SILLY_PUTTY_MATERIAL = new FunkyFluidsMaterials.Builder(MaterialColor.COLOR_MAGENTA).notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();
	public static final Material PURPLE_FLUID_MATERIAL = new FunkyFluidsMaterials.Builder(MaterialColor.COLOR_PURPLE).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();

	public static class Builder {
		private PushReaction pushReaction = PushReaction.NORMAL;
		private boolean blocksMotion = true;
		private boolean flammable;
		private boolean liquid;
		private boolean replaceable;
		private boolean solid = true;
		private final MaterialColor color;
		private boolean solidBlocking = true;

		public Builder(MaterialColor pColor) {
			this.color = pColor;
		}

		public FunkyFluidsMaterials.Builder liquid() {
			this.liquid = true;
			return this;
		}

		public FunkyFluidsMaterials.Builder nonSolid() {
			this.solid = false;
			return this;
		}

		public FunkyFluidsMaterials.Builder noCollider() {
			this.blocksMotion = false;
			return this;
		}

		public FunkyFluidsMaterials.Builder notSolidBlocking() {
			this.solidBlocking = false;
			return this;
		}

		public FunkyFluidsMaterials.Builder flammable() {
			this.flammable = true;
			return this;
		}

		public FunkyFluidsMaterials.Builder replaceable() {
			this.replaceable = true;
			return this;
		}

		public FunkyFluidsMaterials.Builder destroyOnPush() {
			this.pushReaction = PushReaction.DESTROY;
			return this;
		}

		public FunkyFluidsMaterials.Builder notPushable() {
			this.pushReaction = PushReaction.BLOCK;
			return this;
		}

		public Material build() {
			return new Material(this.color, this.liquid, this.solid, this.blocksMotion, this.solidBlocking, this.flammable, this.replaceable, this.pushReaction);
		}
	}
}
