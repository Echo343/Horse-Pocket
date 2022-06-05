package com.blargsworkshop.horsepocket.event;

import com.blargsworkshop.horsepocket.item.PocketItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "horsepocket")
public class InteractionEventHandler {

//	private static final String MINECRAFT_HORSE = "minecraft:horse";
//	private static final String VARIANT = "Variant";

	@SubscribeEvent
	public static void onInteractEntity(PlayerInteractEvent.EntityInteract event) {

		if (event.getItemStack().getItem() instanceof PocketItem) {
			ItemStack stack = event.getItemStack();
			CompoundTag compound = stack.getOrCreateTag();

			if (!compound.getBoolean(PocketItem.Tag.HAS_ENTITY)) {
				Entity entity = event.getTarget();

				if (entity instanceof Saddleable && ((Saddleable) entity).isSaddled() && entity.isAlive()) {
					Player player = event.getPlayer();
					if (entity.isPassenger()) {
						entity.stopRiding();
					}
					entity.ejectPassengers();
					
					compound.putBoolean(PocketItem.Tag.HAS_ENTITY, true);
					compound.putString(PocketItem.Tag.ENTITY_TYPE, entity.getType().getRegistryName().toString());
					compound.put(PocketItem.Tag.ENTITY_DATA, entity.saveWithoutId(new CompoundTag()));
					compound.putString(PocketItem.Tag.TYPE_NAME, entity.getType().getDescription().getString());
					compound.putBoolean(PocketItem.Tag.HAS_CUSTOM_NAME, entity.hasCustomName());
					if (entity.hasCustomName()) {
						compound.putString(PocketItem.Tag.CUSTOM_NAME, entity.getCustomName().getString());
					}

//					if (player.level.isClientSide) {
//						if (entity.hasCustomName()) {
//							Chat.addUnlocalizedChatMessage(player, "Stowed " + entity.getCustomName().getString());
//						} else if (entity.getType().getRegistryName().toString().equalsIgnoreCase(MINECRAFT_HORSE)) {
//							int variant = compound.getCompound(PocketItem.Tag.ENTITY_DATA).getInt(VARIANT);
//							Chat.addUnlocalizedChatMessage(player, "Stowed " + Variants.INSTANCE.getDescriptionByVariant(variant).toLowerCase());
//						} else {
//							Chat.addUnlocalizedChatMessage(player, "Stowed a " + entity.getType().getDescription().getString().toLowerCase());
//						}
//					}

					entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
					player.setItemInHand(event.getHand(), stack);
					event.setCancellationResult(InteractionResult.sidedSuccess(event.getPlayer().level.isClientSide));
				}
			}
		}
	}

}
