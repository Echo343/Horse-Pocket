package com.blargsworkshop.horsepocket.event;

import com.blargsworkshop.common.text.Chat;
import com.blargsworkshop.horsepocket.enums.Variants;
import com.blargsworkshop.horsepocket.item.PocketItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

	private static final String MINECRAFT_HORSE = "minecraft:horse";
	private static final String VARIANT = "Variant";

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

					compound.putString(PocketItem.Tag.ENTITY_TYPE, entity.getType().getRegistryName().toString());
					compound.putString("entity_display_name", entity.getType().toShortString());
					compound.put(PocketItem.Tag.ENTITY_DATA, entity.saveWithoutId(new CompoundTag()));
					compound.putBoolean("has_custom_name", entity.hasCustomName());
					compound.putString("entity_name", Component.Serializer.toJson(entity.hasCustomName() ? entity.getCustomName() : entity.getDisplayName()));
					compound.putBoolean(PocketItem.Tag.HAS_ENTITY, true);

					if (player.level.isClientSide) {
						if (entity.hasCustomName()) {
							Chat.addUnlocalizedChatMessage(player, "Stowed " + entity.getCustomName().getString());
						} else if (compound.getString(PocketItem.Tag.ENTITY_TYPE).equalsIgnoreCase(MINECRAFT_HORSE)) {
							int variant = compound.getCompound(PocketItem.Tag.ENTITY_DATA).getInt(VARIANT);
							Chat.addUnlocalizedChatMessage(player, "Stowed " + Variants.INSTANCE.getDescriptionByVariant(variant));
						} else {
							Chat.addUnlocalizedChatMessage(player, "Stowed a " + entity.getType().getRegistryName().getPath());
						}
					}

					entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
					player.setItemInHand(event.getHand(), stack);
					event.setCancellationResult(InteractionResult.sidedSuccess(event.getPlayer().level.isClientSide));
				}
			}
		}
	}

}
