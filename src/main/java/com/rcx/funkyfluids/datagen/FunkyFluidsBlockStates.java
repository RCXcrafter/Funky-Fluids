package com.rcx.funkyfluids.datagen;

import com.rcx.funkyfluids.FunkyFluids;
import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.FunkyFluidsResources.FluidStuff;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class FunkyFluidsBlockStates extends BlockStateProvider {

	public FunkyFluidsBlockStates(PackOutput gen, ExistingFileHelper exFileHelper) {
		super(gen, FunkyFluids.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		//this is just to give them proper particles
		for (FluidStuff fluid : FunkyFluidsResources.fluidList) {
			fluid(fluid.FLUID_BLOCK, fluid.name);
		}
	}

	public void blockWithItem(RegistryObject<? extends Block> registryObject) {
		//block model
		simpleBlock(registryObject.get());
		//itemblock model
		ResourceLocation id = registryObject.getId();
		ResourceLocation textureLocation = new ResourceLocation(id.getNamespace(), "block/" + id.getPath());
		itemModels().cubeAll(id.getPath(), textureLocation);
	}

	public void fluid(RegistryObject<? extends Block> fluid, String name) {
		simpleBlock(fluid.get(), models().cubeAll(name, new ResourceLocation(FunkyFluids.MODID, ModelProvider.BLOCK_FOLDER + "/fluid/" + name + "_still")));
	}
}
