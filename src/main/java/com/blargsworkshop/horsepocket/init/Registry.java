package com.blargsworkshop.horsepocket.init;

import com.blargsworkshop.horsepocket.HorsePocket;
import com.blargsworkshop.horsepocket.item.PocketItem;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registry {
	static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HorsePocket.MOD_ID);
	
	public static void init() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	static {
		ITEMS.register("pocket", PocketItem::new);
	}

	// TODO - I'm just making new RegistryObjects down below which is unnecessary since the above register commands return a RegistryObject.

	// Items
	public static class Items {
		public static final RegistryObject<Item> POCKET = RegistryObject.create(new ResourceLocation(Sleepstone.MOD_ID, "pocket"), ForgeRegistries.ITEMS);
	}
}
