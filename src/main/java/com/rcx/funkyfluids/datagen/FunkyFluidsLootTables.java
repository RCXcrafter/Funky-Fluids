package com.rcx.funkyfluids.datagen;

import java.util.List;
import java.util.Set;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class FunkyFluidsLootTables extends LootTableProvider {

	public FunkyFluidsLootTables(PackOutput output) {
		super(output, Set.of(), List.of(
				new LootTableProvider.SubProviderEntry(FunkyFluidsBlockLootTables::new, LootContextParamSets.BLOCK)
				));
	}
}
