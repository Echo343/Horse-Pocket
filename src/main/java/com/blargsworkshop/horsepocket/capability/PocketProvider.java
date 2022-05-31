package com.blargsworkshop.horsepocket.capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.blargsworkshop.horsepocket.HorsePocket;
import com.blargsworkshop.horsepocket.item.PocketItem;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="horsepocket")
public class PocketProvider implements ICapabilityProvider {
	
	private static final ResourceLocation IDENTIFIER = new ResourceLocation(HorsePocket.MOD_ID, "pocket_capability");
	
	private final IPocket backend = new Pocket();
	private final LazyOptional<IPocket> optionalData = LazyOptional.of(() -> backend);
	
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		return PocketCapability.INSTANCE.orEmpty(cap, this.optionalData);
	}
	
	void invalidate() {
		this.optionalData.invalidate();
	}
	
	@SubscribeEvent
	public static void onAttachCapabilitiesToItemStack(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof PocketItem) {
			final PocketProvider provider = new PocketProvider();
			event.addCapability(PocketProvider.IDENTIFIER, provider);
			event.addListener(provider::invalidate);
		}
	}

}
