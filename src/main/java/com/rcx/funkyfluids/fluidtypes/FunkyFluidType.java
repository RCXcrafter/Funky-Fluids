package com.rcx.funkyfluids.fluidtypes;

import java.awt.Color;
import java.util.function.Consumer;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import com.rcx.funkyfluids.FunkyFluids;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

public class FunkyFluidType extends FluidType {

	public final ResourceLocation RENDER_OVERLAY;
	public final ResourceLocation TEXTURE_STILL;
	public final ResourceLocation TEXTURE_FLOW;
	public final ResourceLocation TEXTURE_OVERLAY;
	public final Vector3f FOG_COLOR;

	public FunkyFluidType(Properties properties, String name, int color) {
		super(properties);
		RENDER_OVERLAY = new ResourceLocation(FunkyFluids.MODID, "textures/overlay/" + name + ".png");
		TEXTURE_STILL = new ResourceLocation(FunkyFluids.MODID, "block/fluid/" + name + "_still");
		TEXTURE_FLOW = new ResourceLocation(FunkyFluids.MODID, "block/fluid/" + name + "_flow");
		TEXTURE_OVERLAY = new ResourceLocation(FunkyFluids.MODID, "block/fluid/" + name + "_overlay");
		Color colorObject = new Color(color);
		FOG_COLOR = new Vector3f(colorObject.getRed()/255F, colorObject.getGreen()/255F, colorObject.getBlue()/255F);
	}

	public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
		consumer.accept(new IClientFluidTypeExtensions() {
			@Override
			public ResourceLocation getStillTexture() {
				return TEXTURE_STILL;
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return TEXTURE_FLOW;
			}

			@Override
			public ResourceLocation getOverlayTexture() {
				return TEXTURE_OVERLAY;
			}

			@Override
			public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
				return RENDER_OVERLAY;
			}

			@Override
			public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
				return fluidFogColor;
			}

			@Override
			public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
				RenderSystem.setShaderFogStart(0.1F);
				RenderSystem.setShaderFogEnd(1.5F);
			}
		});
	}
}
