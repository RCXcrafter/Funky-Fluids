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
		addLore(FunkyFluidsResources.oobleck.FLUID_BUCKET, "Non-Newtonian");
		addLore(FunkyFluidsResources.melonade.FLUID_BUCKET, "When life gives you melons...");
		addLore(FunkyFluidsResources.liquidCrystal.FLUID_BUCKET, "Solid Stuff");
	}

	public void addFluid(String name, String localizedName) {
		add("fluid." + FunkyFluids.MODID + "." + name, localizedName);
	}

	public void addLore(Supplier<? extends Item> key, String lore) {
		add(key.get().getDescriptionId() + ".lore", lore);
	}
}
