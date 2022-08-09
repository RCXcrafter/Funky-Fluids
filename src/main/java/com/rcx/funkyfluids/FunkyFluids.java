package com.rcx.funkyfluids;

import com.rcx.funkyfluids.datagen.FunkyFluidsBlockStates;
import com.rcx.funkyfluids.datagen.FunkyFluidsBlockTags;
import com.rcx.funkyfluids.datagen.FunkyFluidsFluidTags;
import com.rcx.funkyfluids.datagen.FunkyFluidsItemModels;
import com.rcx.funkyfluids.datagen.FunkyFluidsItemTags;
import com.rcx.funkyfluids.datagen.FunkyFluidsLang;
import com.rcx.funkyfluids.datagen.FunkyFluidsLootTables;
import com.rcx.funkyfluids.datagen.FunkyFluidsRecipes;
import com.rcx.funkyfluids.entities.FallingSillyPuttyRenderer;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FunkyFluids.MODID)
public class FunkyFluids {

	public static final String MODID = "funkyfluids";

	//private static final Logger LOGGER = LogUtils.getLogger();

	public FunkyFluids() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::gatherData);

		FunkyFluidsResources.RECIPE_SERIALIZERS.register(modEventBus);
		FunkyFluidsResources.BLOCKS.register(modEventBus);
		FunkyFluidsResources.ITEMS.register(modEventBus);
		FunkyFluidsResources.FLUIDTYPES.register(modEventBus);
		FunkyFluidsResources.FLUIDS.register(modEventBus);
		FunkyFluidsResources.ENTITY_TYPES.register(modEventBus);

		//MinecraftForge.EVENT_BUS.register(this);
	}

	public void commonSetup(final FMLCommonSetupEvent event) {
		//FunkyFluidsPacketHandler.init();
		FunkyFluidsResources.registerFluidInteractions();
	}

	public void gatherData(GatherDataEvent event) {
		DataGenerator gen = event.getGenerator();

		if (event.includeClient()) {
			gen.addProvider(true, new FunkyFluidsLang(gen));
			ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
			ItemModelProvider itemModels = new FunkyFluidsItemModels(gen, existingFileHelper);
			gen.addProvider(true, itemModels);
			gen.addProvider(true, new FunkyFluidsBlockStates(gen, existingFileHelper));
		} if (event.includeServer()) {
			gen.addProvider(true, new FunkyFluidsLootTables(gen));
			gen.addProvider(true, new FunkyFluidsRecipes(gen));
			BlockTagsProvider blockTags = new FunkyFluidsBlockTags(gen, event.getExistingFileHelper());
			gen.addProvider(true, blockTags);
			gen.addProvider(true, new FunkyFluidsItemTags(gen, blockTags, event.getExistingFileHelper()));
			gen.addProvider(true, new FunkyFluidsFluidTags(gen, event.getExistingFileHelper()));
		}
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModEvents {

		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			ItemBlockRenderTypes.setRenderLayer(FunkyFluidsResources.MELONADE.FLUID.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FunkyFluidsResources.MELONADE.FLUID_FLOW.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FunkyFluidsResources.LIQUID_CRYSTAL.FLUID.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(FunkyFluidsResources.LIQUID_CRYSTAL.FLUID_FLOW.get(), RenderType.translucent());

			EntityRenderers.register(FunkyFluidsResources.FALLING_SILLY_PUTTY.get(), FallingSillyPuttyRenderer::new);

			//MinecraftForge.EVENT_BUS.addListener(ClientModEvents::handleClientJump);
		}

		/*private static void handleClientJump(PlayerTickEvent event) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.player != null && minecraft.player == event.player && event.phase == Phase.START && event.side == LogicalSide.CLIENT && !minecraft.player.isSpectator() && minecraft.options.keyJump.isDown()) {
				double depth = minecraft.player.getFluidTypeHeight(FunkyFluidsResources.oobleck.TYPE.get());
				if (depth > 0.0d && depth < 0.2d && !minecraft.player.onClimbable() && !minecraft.player.isInWaterOrBubble())
					FunkyFluidsPacketHandler.INSTANCE.sendToServer(new PacketOobleckJump());
			}
		}*/
	}
}
