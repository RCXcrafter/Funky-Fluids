package com.rcx.funkyfluids;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.rcx.funkyfluids.blocks.OobleckBlock;
import com.rcx.funkyfluids.fluidtypes.OobleckType;

import net.minecraft.data.models.blockstates.PropertyDispatch.TriFunction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FunkyFluidsResources {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FunkyFluids.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FunkyFluids.MODID);
	public static final DeferredRegister<FluidType> FLUIDTYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, FunkyFluids.MODID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, FunkyFluids.MODID);


	public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

	public static FluidStuff addFluid(String name, String localizedName, int color, Material material, TriFunction<FluidType.Properties, String, Integer, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, FluidType.Properties prop) {
		FluidStuff fluid = new FluidStuff(name, localizedName, color, type.apply(prop, name, color), block, material);
		fluidList.add(fluid);
		return fluid;
	}

	public static final FluidStuff oobleck = addFluid("oobleck", "Oobleck", 0xFFFFFF, Material.WATER, OobleckType::new, OobleckBlock::new, FluidType.Properties.create()
			.canExtinguish(true)
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
			.canHydrate(true)
			.motionScale(0.007D));


	public static class FluidStuff {

		public final ForgeFlowingFluid.Properties PROPERTIES;

		public final RegistryObject<ForgeFlowingFluid.Source> FLUID;
		public final RegistryObject<ForgeFlowingFluid.Flowing> FLUID_FLOW;
		public final RegistryObject<FluidType> TYPE;

		public final RegistryObject<LiquidBlock> FLUID_BLOCK;

		public final RegistryObject<BucketItem> FLUID_BUCKET;

		public final String name;
		public final String localizedName;
		public final int color;

		public FluidStuff(String name, String localizedName, int color, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Material material) {
			this.name = name;
			this.localizedName = localizedName;
			this.color = color;

			FLUID = FLUIDS.register(name, () -> new ForgeFlowingFluid.Source(getFluidProperties()));
			FLUID_FLOW = FLUIDS.register("flowing_" + name, () -> new ForgeFlowingFluid.Flowing(getFluidProperties()));
			TYPE = FLUIDTYPES.register(name, () -> type);

			PROPERTIES = new ForgeFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);

			FLUID_BLOCK = BLOCKS.register(name + "_block", () -> block.apply(FLUID, Block.Properties.of(material).lightLevel((state) -> { return type.getLightLevel(); }).randomTicks().strength(100.0F).noLootTable()));
			FLUID_BUCKET = ITEMS.register(name + "_bucket", () -> new BucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

			PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK).explosionResistance(1000F).tickRate(9);
		}

		public ForgeFlowingFluid.Properties getFluidProperties() {
			return PROPERTIES;       
		}
	}
}