package com.blargsworkshop.horsepocket.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="horsepocket")
public class PocketCapability {
	public static final Capability<IPocket> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});
	
	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
		event.register(IPocket.class);
	}
	
	private PocketCapability() {}
}
