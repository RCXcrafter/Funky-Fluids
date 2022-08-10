package com.rcx.funkyfluids;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.rcx.funkyfluids.blocks.OobleckBlock;
import com.rcx.funkyfluids.blocks.SillyPuttyBlock;
import com.rcx.funkyfluids.blocks.SolidLiquidBlock;
import com.rcx.funkyfluids.entities.FallingSillyPuttyEntity;
import com.rcx.funkyfluids.fluids.FlowingSillyPutty;
import com.rcx.funkyfluids.fluidtypes.FunkyFluidType;
import com.rcx.funkyfluids.fluidtypes.FunkyFluidType.FunkyFluidInfo;
import com.rcx.funkyfluids.fluidtypes.NormalPhysicsType;
import com.rcx.funkyfluids.fluidtypes.OobleckType;
import com.rcx.funkyfluids.fluidtypes.SillyPuttyType;
import com.rcx.funkyfluids.items.FunkyFluidsBucketItem;
import com.rcx.funkyfluids.util.ConsumingShapelessRecipe;
import com.rcx.funkyfluids.util.FunkyFluidsMaterials;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidInteractionRegistry.InteractionInformation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FunkyFluidsResources {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.RECIPE_SERIALIZERS, FunkyFluids.MODID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FunkyFluids.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FunkyFluids.MODID);
	public static final DeferredRegister<FluidType> FLUIDTYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, FunkyFluids.MODID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, FunkyFluids.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FunkyFluids.MODID);

	public static final RegistryObject<RecipeSerializer<?>> CONSUMING_SHAPELESS = RECIPE_SERIALIZERS.register("crafting_shapeless_consuming", () -> new ConsumingShapelessRecipe.Serializer());


	public static List<FluidStuff> fluidList = new ArrayList<FluidStuff>();

	public static FluidStuff addFluid(String localizedName, FunkyFluidInfo info, Material material, BiFunction<FluidType.Properties, FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		FluidStuff fluid = new FluidStuff(info.name, localizedName, info.color, type.apply(prop, info), block, fluidProperties, source, flowing, material);
		fluidList.add(fluid);
		return fluid;
	}

	public static FluidStuff addFluid(String localizedName, FunkyFluidInfo info, Material material, BiFunction<FluidType.Properties, FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		FluidStuff fluid = new FluidStuff(info.name, localizedName, info.color, type.apply(prop, info), block, fluidProperties, ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new, material);
		fluidList.add(fluid);
		return fluid;
	}

	public static final FluidStuff OOBLECK = addFluid("Oobleck", new FunkyFluidInfo("oobleck", 0xE8F3F4, 0.1F, 1.5F), FunkyFluidsMaterials.OOBLECK_MATERIAL, OobleckType::new, OobleckBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(20),
			FluidType.Properties.create()
			.canExtinguish(true)
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
			.canHydrate(true)
			.viscosity(3000)
			.motionScale(0.007D));

	public static final FluidStuff MELONADE = addFluid("Melonade", new FunkyFluidInfo("melonade", 0xDF2121, -8.0F, 10.0F), FunkyFluidsMaterials.MELONADE_MATERIAL, FunkyFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F),
			FluidType.Properties.create()
			.canExtinguish(true)
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
			.canHydrate(true));

	public static final FluidStuff LIQUID_CRYSTAL = addFluid("Liquid Crystal", new FunkyFluidInfo("liquid_crystal", 0x926DD9, -8.0F, 10.0F), FunkyFluidsMaterials.LIQUID_CRYSTAL_MATERIAL, NormalPhysicsType::new, SolidLiquidBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).levelDecreasePerBlock(2),
			FluidType.Properties.create()
			.canPushEntity(false)
			.lightLevel(10)
			.motionScale(0)
			.canSwim(false)
			.canDrown(false)
			.viscosity(4000)
			.temperature(500)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.AMETHYST_BLOCK_PLACE)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.AMETHYST_BLOCK_HIT));

	public static final FluidStuff SILLY_PUTTY = addFluid("Silly Putty", new FunkyFluidInfo("silly_putty", 0xF02FF3, 0.1F, 1.0F), FunkyFluidsMaterials.SILLY_PUTTY_MATERIAL, SillyPuttyType::new, SillyPuttyBlock::new, ForgeFlowingFluid.Source::new, FlowingSillyPutty::new,
			prop -> prop.explosionResistance(1000F).tickRate(70).slopeFindDistance(1).levelDecreasePerBlock(4),
			FluidType.Properties.create()
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.SLIME_BLOCK_PLACE)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.SLIME_BLOCK_BREAK)
			.viscosity(8000)
			.motionScale(0.0005D));


	public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Builder<T> builder) {
		return ENTITY_TYPES.register(name, () -> builder.build(FunkyFluids.MODID + ":" + name));
	}

	public static final RegistryObject<EntityType<FallingSillyPuttyEntity>> FALLING_SILLY_PUTTY = register("falling_silly_putty", Builder.<FallingSillyPuttyEntity>of(FallingSillyPuttyEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20));


	public static void registerFluidInteractions() {
		//oobleck interacts with lava just like water does because it's mostly water
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new InteractionInformation(
				OOBLECK.TYPE.get(),
				fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState()
				));
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new InteractionInformation(
				MELONADE.TYPE.get(),
				fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState()
				));
	}

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

		public FluidStuff(String name, String localizedName, int color, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, Material material) {
			this.name = name;
			this.localizedName = localizedName;
			this.color = color;

			FLUID = FLUIDS.register(name, () -> source.apply(getFluidProperties()));
			FLUID_FLOW = FLUIDS.register("flowing_" + name, () -> flowing.apply(getFluidProperties()));
			TYPE = FLUIDTYPES.register(name, () -> type);

			PROPERTIES = new ForgeFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);
			if (fluidProperties != null)
				fluidProperties.accept(PROPERTIES);

			FLUID_BLOCK = BLOCKS.register(name + "_block", () -> block.apply(FLUID, Block.Properties.of(material).lightLevel((state) -> { return type.getLightLevel(); }).randomTicks().strength(100.0F).noLootTable()));
			FLUID_BUCKET = ITEMS.register(name + "_bucket", () -> new FunkyFluidsBucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(CreativeModeTab.TAB_MISC)));

			PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
		}

		public ForgeFlowingFluid.Properties getFluidProperties() {
			return PROPERTIES;       
		}
	}
}
