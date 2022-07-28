package com.rcx.funkyfluids.datagen;

import com.rcx.funkyfluids.FunkyFluids;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FunkyFluidsFluidTags extends FluidTagsProvider {

	public FunkyFluidsFluidTags(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, FunkyFluids.MODID, existingFileHelper);
	}

	@Override
	public void addTags() {

	}
}
