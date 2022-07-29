package com.rcx.funkyfluids.datagen;

import java.util.function.Consumer;

import com.rcx.funkyfluids.FunkyFluids;
import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.util.ConsumerWrapperBuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class FunkyFluidsRecipes extends RecipeProvider implements IConditionBuilder {

	public FunkyFluidsRecipes(DataGenerator gen) {
		super(gen);
	}

	@Override
	public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(FunkyFluidsResources.oobleck.FLUID_BUCKET.get())
		.requires(Items.POTATO)
		.requires(Items.POTATO)
		.requires(Items.WATER_BUCKET, 1)
		.unlockedBy("has_item", has(Items.WATER_BUCKET))
		.save(ConsumerWrapperBuilder.wrap(FunkyFluidsResources.consumingShapeless.get()).build(consumer), getResource("oobleck_bucket"));

		ShapelessRecipeBuilder.shapeless(FunkyFluidsResources.melonade.FLUID_BUCKET.get())
		.requires(Items.MELON_SLICE)
		.requires(Items.MELON_SLICE)
		.requires(Items.BUCKET)
		.unlockedBy("has_item", has(Items.BUCKET))
		.save(consumer, getResource("melonade_bucket"));
	}

	public ResourceLocation getResource(String name) {
		return new ResourceLocation(FunkyFluids.MODID, name);
	}
}
