package com.rcx.funkyfluids.datagen;

import com.google.gson.JsonObject;
import com.rcx.funkyfluids.FunkyFluids;
import com.rcx.funkyfluids.FunkyFluidsResources;
import com.rcx.funkyfluids.FunkyFluidsResources.FluidStuff;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class FunkyFluidsItemModels extends ItemModelProvider {

	public FunkyFluidsItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, FunkyFluids.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		for (FluidStuff fluid : FunkyFluidsResources.fluidList) {
			//bucketModel(fluid.FLUID_BUCKET, fluid.name);
			itemWithModel(fluid.FLUID_BUCKET, "item/generated");
		}
	}

	public void itemWithModel(RegistryObject<? extends Item> registryObject, String model) {
		ResourceLocation id = registryObject.getId();
		ResourceLocation textureLocation = new ResourceLocation(id.getNamespace(), "item/" + id.getPath());
		singleTexture(id.getPath(), new ResourceLocation(model), "layer0", textureLocation);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void bucketModel(RegistryObject<? extends BucketItem> registryObject, String name) {
		ModelBuilder builder = getBuilder(registryObject.getId().getPath()).parent(getExistingFile(new ResourceLocation(FunkyFluids.MODID, "item/bucket_fluid")));

		//I'm not sure how this works but it works
		builder.customLoader((t, u) ->  new CustomLoaderBuilder(((ModelBuilder) t).getLocation(), (ModelBuilder) t, (ExistingFileHelper) u) {
			public JsonObject toJson(JsonObject json) {
				json.addProperty("loader", "forge:fluid_container");
				json.addProperty("fluid", FunkyFluids.MODID + ":" + name);
				return json;
			}
		});
	}
}
