package com.rcx.funkyfluids;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.rcx.funkyfluids.blocks.OobleckBlock;
import com.rcx.funkyfluids.blocks.RedstoneSuspensionBlock;
import com.rcx.funkyfluids.blocks.SillyPuttyBlock;
import com.rcx.funkyfluids.blocks.SolidLiquidBlock;
import com.rcx.funkyfluids.datagen.FunkyFluidsFluidTags;
import com.rcx.funkyfluids.entities.FallingSillyPuttyEntity;
import com.rcx.funkyfluids.fluids.FlowingMagnetroleum;
import com.rcx.funkyfluids.fluids.FlowingSillyPutty;
import com.rcx.funkyfluids.fluidtypes.FunkyFluidType;
import com.rcx.funkyfluids.fluidtypes.FunkyFluidType.FunkyFluidInfo;
import com.rcx.funkyfluids.fluidtypes.LowFrictionType;
import com.rcx.funkyfluids.fluidtypes.NormalPhysicsType;
import com.rcx.funkyfluids.fluidtypes.OobleckType;
import com.rcx.funkyfluids.fluidtypes.SillyPuttyType;
import com.rcx.funkyfluids.items.FunkyFluidsBucketItem;
import com.rcx.funkyfluids.util.ConsumingShapelessRecipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidInteractionRegistry.InteractionInformation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

	public static FluidStuff addFluid(String localizedName, FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		FluidStuff fluid = new FluidStuff(info.name, localizedName, info.color, type.apply(prop, info), block, fluidProperties, source, flowing, properties);
		fluidList.add(fluid);
		return fluid;
	}

	public static FluidStuff addFluid(String localizedName, FunkyFluidInfo info, Block.Properties properties, BiFunction<FluidType.Properties, FunkyFluidInfo, FluidType> type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, FluidType.Properties prop) {
		return addFluid(localizedName, info, properties, type, block, ForgeFlowingFluid.Source::new, ForgeFlowingFluid.Flowing::new, fluidProperties, prop);
	}

	public static final FluidStuff OOBLECK = addFluid("Oobleck", new FunkyFluidInfo("oobleck", 0xE8F3F4, 0.1F, 1.5F), Block.Properties.of().mapColor(MapColor.WOOL).noCollission().replaceable().liquid().pushReaction(PushReaction.DESTROY).noLootTable(), OobleckType::new, OobleckBlock::new,
			prop -> prop.explosionResistance(1000F).tickRate(20),
			FluidType.Properties.create()
			.canExtinguish(true)
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
			.canHydrate(true)
			.viscosity(3000)
			.motionScale(0.007D));

	public static final FluidStuff MELONADE = addFluid("Melonade", new FunkyFluidInfo("melonade", 0xDF2121, -8.0F, 10.0F), Block.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().replaceable().liquid().pushReaction(PushReaction.DESTROY).noLootTable(), FunkyFluidType::new, LiquidBlock::new,
			prop -> prop.explosionResistance(1000F),
			FluidType.Properties.create()
			.canExtinguish(true)
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
			.canHydrate(true));

	public static final FluidStuff LIQUID_CRYSTAL = addFluid("Liquid Crystal", new FunkyFluidInfo("liquid_crystal", 0x926DD9, -8.0F, 10.0F), Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).noOcclusion().liquid().pushReaction(PushReaction.BLOCK).noLootTable(), NormalPhysicsType::new, SolidLiquidBlock::new,
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

	public static final FluidStuff SILLY_PUTTY = addFluid("Silly Putty", new FunkyFluidInfo("silly_putty", 0xF02FF3, 0.1F, 1.0F), Block.Properties.of().mapColor(MapColor.COLOR_MAGENTA).noOcclusion().replaceable().liquid().pushReaction(PushReaction.DESTROY).noLootTable(), SillyPuttyType::new, SillyPuttyBlock::new, ForgeFlowingFluid.Source::new, FlowingSillyPutty::new,
			prop -> prop.explosionResistance(1000F).tickRate(70).slopeFindDistance(1).levelDecreasePerBlock(4),
			FluidType.Properties.create()
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.SLIME_BLOCK_PLACE)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.SLIME_BLOCK_BREAK)
			.viscosity(8000)
			.motionScale(0.0001D));

	public static final FluidStuff REDSTONE_SUSPENSION = addFluid("Redstone Suspension", new FunkyFluidInfo("redstone_suspension", 0xAA0F01, -28.0F, 40.0F), Block.Properties.of().mapColor(MapColor.COLOR_RED).noCollission().replaceable().liquid().pushReaction(PushReaction.DESTROY).noLootTable(), FunkyFluidType::new, RedstoneSuspensionBlock::new,
			prop -> prop.explosionResistance(1000F),
			FluidType.Properties.create()
			.canExtinguish(true)
			.supportsBoating(true)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
			.canHydrate(true));

	public static final FluidStuff MAGNETROLEUM = addFluid("Magnetroleum", new FunkyFluidInfo("magnetroleum", 0x8B3BAD, -28.0F, 10.0F), Block.Properties.of().mapColor(MapColor.COLOR_PURPLE).noCollission().replaceable().liquid().pushReaction(PushReaction.DESTROY).noLootTable(), LowFrictionType::new, LiquidBlock::new, ForgeFlowingFluid.Source::new, FlowingMagnetroleum::new,
			prop -> prop.explosionResistance(1000F).tickRate(30).levelDecreasePerBlock(2),
			FluidType.Properties.create()
			.supportsBoating(true)
			.temperature(340)
			.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
			.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
			.motionScale(-0.04D));


	public static <T extends Entity> RegistryObject<EntityType<T>> register(String name, Builder<T> builder) {
		return ENTITY_TYPES.register(name, () -> builder.build(FunkyFluids.MODID + ":" + name));
	}

	public static final RegistryObject<EntityType<FallingSillyPuttyEntity>> FALLING_SILLY_PUTTY = register("falling_silly_putty", Builder.<FallingSillyPuttyEntity>of(FallingSillyPuttyEntity::new, MobCategory.MISC).sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20));


	public static void registerFluidInteractions() {
		FluidInteractionRegistry.addInteraction(ForgeMod.LAVA_TYPE.get(), new InteractionInformation(
				(level, currentPos, relativePos, currentState) -> level.getFluidState(relativePos).is(FunkyFluidsFluidTags.WATERY),
				fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState()
				));
	}

	public static void registerDispenserBehaviour(final FMLCommonSetupEvent event) {
		DispenseItemBehavior dispenseBucket = new DefaultDispenseItemBehavior() {
			private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

			@Override
			public ItemStack execute(BlockSource source, ItemStack stack) {
				DispensibleContainerItem container = (DispensibleContainerItem)stack.getItem();
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				Level level = source.getLevel();
				if (container.emptyContents(null, level, blockpos, null)) {
					container.checkExtraContent(null, level, stack, blockpos);
					return new ItemStack(Items.BUCKET);
				} else {
					return this.defaultDispenseItemBehavior.dispense(source, stack);
				}
			}
		};
		event.enqueueWork(() -> {
			for (FluidStuff fluid : fluidList) {
				DispenserBlock.registerBehavior(fluid.FLUID_BUCKET.get(), dispenseBucket);
			}
		});
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

		public FluidStuff(String name, String localizedName, int color, FluidType type, BiFunction<Supplier<? extends FlowingFluid>, BlockBehaviour.Properties, LiquidBlock> block, @Nullable Consumer<ForgeFlowingFluid.Properties> fluidProperties, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Source> source, Function<ForgeFlowingFluid.Properties, ForgeFlowingFluid.Flowing> flowing, Block.Properties properties) {
			this.name = name;
			this.localizedName = localizedName;
			this.color = color;

			FLUID = FLUIDS.register(name, () -> source.apply(getFluidProperties()));
			FLUID_FLOW = FLUIDS.register("flowing_" + name, () -> flowing.apply(getFluidProperties()));
			TYPE = FLUIDTYPES.register(name, () -> type);

			PROPERTIES = new ForgeFlowingFluid.Properties(TYPE, FLUID, FLUID_FLOW);
			if (fluidProperties != null)
				fluidProperties.accept(PROPERTIES);

			FLUID_BLOCK = BLOCKS.register(name + "_block", () -> block.apply(FLUID, properties.lightLevel((state) -> { return type.getLightLevel(); }).randomTicks().strength(100.0F).noLootTable()));
			FLUID_BUCKET = ITEMS.register(name + "_bucket", () -> new FunkyFluidsBucketItem(FLUID, new BucketItem.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

			PROPERTIES.bucket(FLUID_BUCKET).block(FLUID_BLOCK);
		}

		public ForgeFlowingFluid.Properties getFluidProperties() {
			return PROPERTIES;       
		}
	}
}
