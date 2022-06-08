package com.blargsworkshop.horsepocket.event;

import com.blargsworkshop.horsepocket.HorsePocket;
import com.blargsworkshop.horsepocket.init.Registry;
import com.blargsworkshop.horsepocket.item.PocketItem;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "horsepocket", bus = Bus.MOD)
public class ClientIconHandler {

	@SubscribeEvent
	public static void doClientStuff(final FMLClientSetupEvent event) {
		ItemProperties.register(
				Registry.Items.POCKET,
				new ResourceLocation(HorsePocket.MOD_ID, "stowed"),
				(stack, level, entity, seed) -> {
					CompoundTag tag = stack.getOrCreateTag();
					if (tag.getBoolean(PocketItem.Tag.HAS_ENTITY)) {
						return 1F;
					}
					return 0F;
				}
		);
	}
}
