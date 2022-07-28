package com.rcx.funkyfluids.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketOobleckJump {

	public static void encode(PacketOobleckJump msg, FriendlyByteBuf buf) {}

	public static PacketOobleckJump decode(FriendlyByteBuf buf) {
		return new PacketOobleckJump();
	}

	public static void handle(PacketOobleckJump msg, Supplier<NetworkEvent.Context> ctx) {
		if (ctx.get().getDirection().getReceptionSide().isServer()) {
			//ctx.get().enqueueWork(() -> OobleckType.jumpOnOobleck(ctx.get().getSender()));
		}
		ctx.get().setPacketHandled(true);
	}
}
