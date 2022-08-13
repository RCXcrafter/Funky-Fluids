package com.rcx.funkyfluids.datagen;

import java.util.function.Supplier;

import com.rcx.funkyfluids.FunkyFluids;
import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.FunkyFluidsResources.FluidStuff;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

public class FunkyFluidsLang extends LanguageProvider {

	public FunkyFluidsLang(DataGenerator gen) {
		super(gen, FunkyFluids.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		for (FluidStuff fluid : FunkyFluidsResources.fluidList) {
			addBlock(fluid.FLUID_BLOCK, fluid.localizedName);
			addFluid(fluid.name, fluid.localizedName);
			addItem(fluid.FLUID_BUCKET, fluid.localizedName + " Bucket");
		}
		addLore(FunkyFluidsResources.OOBLECK.FLUID_BUCKET, "Non-Newtonian");
		addLore(FunkyFluidsResources.MELONADE.FLUID_BUCKET, "When life gives you melons...");
		addLore(FunkyFluidsResources.LIQUID_CRYSTAL.FLUID_BUCKET, "Solid Stuff");
		addLore(FunkyFluidsResources.SILLY_PUTTY.FLUID_BUCKET, "A particularly nonsensical kind of putty");
		addLore(FunkyFluidsResources.REDSTONE_SUSPENSION.FLUID_BUCKET, "Emits redstone signals");
		addLore(FunkyFluidsResources.MAGNETROLEUM.FLUID_BUCKET, "May contain traces of gravity");
	}

	public void addFluid(String name, String localizedName) {
		add("fluid." + FunkyFluids.MODID + "." + name, localizedName);
		add("fluid_type." + FunkyFluids.MODID + "." + name, localizedName);
	}

	public void addLore(Supplier<? extends Item> key, String lore) {
		add(key.get().getDescriptionId() + ".lore", lore);
	}
}
