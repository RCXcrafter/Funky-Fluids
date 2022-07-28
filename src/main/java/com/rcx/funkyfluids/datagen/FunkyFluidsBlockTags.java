package com.rcx.funkyfluids.datagen;

import com.rcx.funkyfluids.FunkyFluids;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FunkyFluidsBlockTags extends BlockTagsProvider {

	public FunkyFluidsBlockTags(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, FunkyFluids.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {

	}
}
