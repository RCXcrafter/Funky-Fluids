package com.rcx.funkyfluids.datagen;

import java.util.concurrent.CompletableFuture;

import com.rcx.funkyfluids.FunkyFluids;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FunkyFluidsBlockTags extends BlockTagsProvider {

	public FunkyFluidsBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, FunkyFluids.MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {

	}
}
