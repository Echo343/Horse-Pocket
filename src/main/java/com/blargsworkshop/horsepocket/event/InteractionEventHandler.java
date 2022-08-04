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
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "horsepocket")
public class InteractionEventHandler {

	private static boolean isRiddenByPlayer(Entity entity) {
		return entity.hasPassenger((passenger) -> {
			return passenger instanceof Player;
		});
	}

	@SubscribeEvent
	public static void onInteractEntity(PlayerInteractEvent.EntityInteract event) {

		if (event.getItemStack().getItem() instanceof PocketItem) {
			ItemStack stack = event.getItemStack();
			CompoundTag compound = stack.getOrCreateTag();

			if (!compound.getBoolean(PocketItem.Tag.HAS_ENTITY)) {
				Entity entity = event.getTarget();

				if (entity instanceof Saddleable && ((Saddleable) entity).isSaddled() && entity.isAlive() && !isRiddenByPlayer(entity)) {
					Player player = event.getEntity();
					if (entity.isPassenger()) {
						entity.stopRiding();
					}
					entity.ejectPassengers();

					compound.putBoolean(PocketItem.Tag.HAS_ENTITY, true);
					compound.putString(PocketItem.Tag.ENTITY_TYPE, ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString());
					compound.put(PocketItem.Tag.ENTITY_DATA, entity.saveWithoutId(new CompoundTag()));
					compound.putString(PocketItem.Tag.TYPE_NAME, entity.getType().getDescription().getString());
					compound.putBoolean(PocketItem.Tag.HAS_CUSTOM_NAME, entity.hasCustomName());
					if (entity.hasCustomName()) {
						compound.putString(PocketItem.Tag.CUSTOM_NAME, entity.getCustomName().getString());
					}

					entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
					player.setItemInHand(event.getHand(), stack);
					event.setCancellationResult(InteractionResult.sidedSuccess(event.getEntity().level.isClientSide));
				}
			}
		}
	}

}
