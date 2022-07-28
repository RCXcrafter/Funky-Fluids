package com.rcx.funkyfluids.datagen;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.rcx.funkyfluids.FunkyFluids;

import net.minecraft.core.Registry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class FunkyFluidsBlockLootTables extends BlockLoot {

	@Nonnull
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ForgeRegistries.BLOCKS.getValues().stream()
				.filter((block) -> FunkyFluids.MODID.equals(Objects.requireNonNull(Registry.BLOCK.getKey(block)).getNamespace()))
				.collect(Collectors.toList());
	}

	@Override
	protected void addTables() {
	}
}
