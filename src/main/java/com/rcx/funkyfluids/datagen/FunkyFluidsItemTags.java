package com.rcx.funkyfluids.datagen;

import com.rcx.funkyfluids.FunkyFluids;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FunkyFluidsItemTags extends ItemTagsProvider {

	public FunkyFluidsItemTags(DataGenerator gen, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper) {
		super(gen, blockTags, FunkyFluids.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {

	}
}
