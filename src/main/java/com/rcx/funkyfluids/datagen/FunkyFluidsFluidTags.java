package com.rcx.funkyfluids.datagen;

import java.util.concurrent.CompletableFuture;

import com.rcx.funkyfluids.FunkyFluids;
import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.FunkyFluidsResources.FluidStuff;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FunkyFluidsFluidTags extends FluidTagsProvider {

	//tag for fluids that interact with lava the same way water does because they consist mostly of water
	public static TagKey<Fluid> WATERY = FluidTags.create(new ResourceLocation(FunkyFluids.MODID, "watery"));

	public FunkyFluidsFluidTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, FunkyFluids.MODID, existingFileHelper);
	}

	@Override
	public void addTags(HolderLookup.Provider provider) {
		for (FluidStuff fluid : FunkyFluidsResources.fluidList) {
			tag(FluidTags.create(new ResourceLocation(FunkyFluids.MODID, fluid.name))).add(fluid.FLUID.get()).add(fluid.FLUID_FLOW.get());
		}
		tag(WATERY)
		//.addTag(FluidTags.WATER)
		.add(FunkyFluidsResources.OOBLECK.FLUID.get())
		.add(FunkyFluidsResources.OOBLECK.FLUID_FLOW.get())
		.add(FunkyFluidsResources.MELONADE.FLUID.get())
		.add(FunkyFluidsResources.MELONADE.FLUID_FLOW.get())
		.add(FunkyFluidsResources.REDSTONE_SUSPENSION.FLUID.get())
		.add(FunkyFluidsResources.REDSTONE_SUSPENSION.FLUID_FLOW.get());
	}
}
