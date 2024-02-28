package com.rcx.funkyfluids.datagen;

import java.util.function.Consumer;

import com.rcx.funkyfluids.FunkyFluids;
import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.util.ConsumerWrapperBuilder;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class FunkyFluidsRecipes extends RecipeProvider implements IConditionBuilder {

	public FunkyFluidsRecipes(PackOutput gen) {
		super(gen);
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FunkyFluidsResources.OOBLECK.FLUID_BUCKET.get())
		.requires(Tags.Items.CROPS_POTATO)
		.requires(Tags.Items.CROPS_POTATO)
		.requires(Items.WATER_BUCKET)
		.unlockedBy("has_item", has(Items.WATER_BUCKET))
		.save(ConsumerWrapperBuilder.wrap(FunkyFluidsResources.CONSUMING_SHAPELESS.get()).build(consumer), getResource("oobleck_bucket"));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FunkyFluidsResources.MELONADE.FLUID_BUCKET.get())
		.requires(Items.MELON_SLICE)
		.requires(Items.MELON_SLICE)
		.requires(Items.SUGAR)
		.requires(Items.BUCKET)
		.unlockedBy("has_item", has(Items.BUCKET))
		.save(consumer, getResource("melonade_bucket"));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FunkyFluidsResources.LIQUID_CRYSTAL.FLUID_BUCKET.get())
		.requires(Tags.Items.GEMS_AMETHYST)
		.requires(Tags.Items.GEMS_AMETHYST)
		.requires(Items.LAVA_BUCKET)
		.unlockedBy("has_item", has(Items.LAVA_BUCKET))
		.save(ConsumerWrapperBuilder.wrap(FunkyFluidsResources.CONSUMING_SHAPELESS.get()).build(consumer), getResource("liquid_crystal_bucket"));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FunkyFluidsResources.SILLY_PUTTY.FLUID_BUCKET.get())
		.requires(Tags.Items.SLIMEBALLS)
		.requires(Items.PHANTOM_MEMBRANE)
		.requires(Items.BUCKET)
		.unlockedBy("has_item", has(Items.BUCKET))
		.save(consumer, getResource("silly_putty_bucket"));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, FunkyFluidsResources.REDSTONE_SUSPENSION.FLUID_BUCKET.get())
		.requires(Tags.Items.DUSTS_REDSTONE)
		.requires(Tags.Items.DUSTS_REDSTONE)
		.requires(Items.WATER_BUCKET)
		.unlockedBy("has_item", has(Items.WATER_BUCKET))
		.save(ConsumerWrapperBuilder.wrap(FunkyFluidsResources.CONSUMING_SHAPELESS.get()).build(consumer), getResource("redstone_suspension_bucket"));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, FunkyFluidsResources.MAGNETROLEUM.FLUID_BUCKET.get())
		.requires(Tags.Items.DUSTS_REDSTONE)
		.requires(Tags.Items.INGOTS_IRON)
		.requires(Tags.Items.GEMS_LAPIS)
		.requires(Items.LAVA_BUCKET)
		.unlockedBy("has_item", has(Items.LAVA_BUCKET))
		.save(ConsumerWrapperBuilder.wrap(FunkyFluidsResources.CONSUMING_SHAPELESS.get()).build(consumer), getResource("magnetroleum_bucket"));

		//createCompatRecipes(consumer);
	}

	public ResourceLocation getResource(String name) {
		return new ResourceLocation(FunkyFluids.MODID, name);
	}

	/*protected final List<GeneratedRecipe> all = new ArrayList<>();

	public void createCompatRecipes(Consumer<FinishedRecipe> consumer) {
		create("oobleck_mixing", b -> b.require(Tags.Items.CROPS_POTATO).require(Tags.Items.CROPS_POTATO).require(FluidTags.WATER, 1000)
				.output(FunkyFluidsResources.OOBLECK.FLUID.get(), 1000)
				.requiresHeat(HeatCondition.NONE));

		create("melonade_mixing", b -> b.require(Items.MELON_SLICE).require(Items.MELON_SLICE).require(Items.SUGAR)
				.output(FunkyFluidsResources.MELONADE.FLUID.get(), 1000)
				.requiresHeat(HeatCondition.NONE));

		create("liquid_crystal_mixing", b -> b.require(Tags.Items.GEMS_AMETHYST).require(Tags.Items.GEMS_AMETHYST).require(FluidTags.LAVA, 1000)
				.output(FunkyFluidsResources.LIQUID_CRYSTAL.FLUID.get(), 1000)
				.requiresHeat(HeatCondition.NONE));

		create("silly_putty_mixing", b -> b.require(Tags.Items.SLIMEBALLS).require(Items.PHANTOM_MEMBRANE)
				.output(FunkyFluidsResources.SILLY_PUTTY.FLUID.get(), 1000)
				.requiresHeat(HeatCondition.NONE));

		create("redstone_suspension_mixing", b -> b.require(Tags.Items.DUSTS_REDSTONE).require(Tags.Items.DUSTS_REDSTONE).require(FluidTags.WATER, 1000)
				.output(FunkyFluidsResources.REDSTONE_SUSPENSION.FLUID.get(), 1000)
				.requiresHeat(HeatCondition.NONE));

		create("magnetroleum_mixing", b -> b.require(Tags.Items.DUSTS_REDSTONE).require(Tags.Items.INGOTS_IRON).require(Tags.Items.GEMS_LAPIS).require(FluidTags.LAVA, 1000)
				.output(FunkyFluidsResources.MAGNETROLEUM.FLUID.get(), 1000)
				.requiresHeat(HeatCondition.NONE));

		all.forEach(c -> c.register(consumer));
	}

	public <T extends ProcessingRecipe<?>> GeneratedRecipe createWithDeferredId(Supplier<ResourceLocation> name,
			UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		ProcessingRecipeSerializer<T> serializer = AllRecipeTypes.MIXING.getSerializer();
		GeneratedRecipe generatedRecipe =
				c -> transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name.get()))
				.build(c);
				all.add(generatedRecipe);
				return generatedRecipe;
	}

	public <T extends ProcessingRecipe<?>> GeneratedRecipe create(ResourceLocation name,
			UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return createWithDeferredId(() -> name, transform);
	}

	public <T extends ProcessingRecipe<?>> GeneratedRecipe create(String name,
			UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return create(getResource(name), transform);
	}*/
}
