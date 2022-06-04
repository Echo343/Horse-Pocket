package com.blargsworkshop.horsepocket.item;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.blargsworkshop.horsepocket.HorsePocket;
import com.blargsworkshop.horsepocket.common.text.Chat;
import com.blargsworkshop.horsepocket.enums.Variants;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PocketItem extends Item {


	public static class Tag {
		public static final String HAS_ENTITY = "has_entity";
		public static final String ENTITY_TYPE = "entity_type";
		public static final String ENTITY_DATA = "entity_data";
		public static final String HAS_CUSTOM_NAME = "has_custom_name";
		public static final String CUSTOM_NAME = "entity_custom_name";
		public static final String TYPE_NAME = "entity_type_description";
	}

	private static final String MINECRAFT_HORSE = "minecraft:horse";
	private static final String MINECRAFT_PIG = "minecraft:pig";
	private static final String MINECRAFT_STRIDER = "minecraft:strider";
	private static final String VARIANT = "Variant";

	public PocketItem() {
		super(new Item.Properties().tab(HorsePocket.TAB).stacksTo(1));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		CompoundTag compound = context.getItemInHand().getOrCreateTag();
		
		if (compound.getBoolean(Tag.HAS_ENTITY)) {
			Optional<EntityType<?>> optional = EntityType.byString(compound.getString(Tag.ENTITY_TYPE));
			
			if (optional.isPresent()) {
				Entity entity = optional.get().create(context.getLevel());
				
				if (entity != null) {
					entity.load(compound.getCompound(Tag.ENTITY_DATA));
					// TODO top of block only?
					BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
					entity.setPos(pos.getX() + 0.5, pos.getY() + 0.01, pos.getZ() + 0.5);
					context.getLevel().addFreshEntity(entity);
					compound.putBoolean(Tag.HAS_ENTITY, false);

					if (context.getPlayer().level.isClientSide) {
						if (entity.hasCustomName()) {
							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released " + entity.getCustomName().getString());
						} else if (entity.getType().getRegistryName().toString().equalsIgnoreCase(MINECRAFT_HORSE)) {
							int variant = compound.getCompound(Tag.ENTITY_DATA).getInt(VARIANT);
							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released " + Variants.INSTANCE.getDescriptionByVariant(variant).toLowerCase());
						} else {
							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released a " + entity.getType().getRegistryName().getPath());
						}
					}

					return InteractionResult.sidedSuccess(context.getPlayer().level.isClientSide);
				}
			}
		}
		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
		CompoundTag compound = stack.getOrCreateTag();
		
		if (compound.getBoolean(Tag.HAS_ENTITY)) {
			MutableComponent entityName = null;
			
			if (compound.getBoolean(Tag.HAS_CUSTOM_NAME)) {
				entityName = new TextComponent(compound.getString(Tag.CUSTOM_NAME));
				entityName.append(" (" + compound.getString(Tag.TYPE_NAME) + ")");
				
			} else if (compound.getString(Tag.ENTITY_TYPE).equalsIgnoreCase(MINECRAFT_HORSE)) {
				int variant = compound.getCompound(Tag.ENTITY_DATA).getInt(VARIANT);
				entityName = new TextComponent(Variants.INSTANCE.getDescriptionByVariant(variant));
				
			} else {
				entityName = new TextComponent(compound.getString(Tag.TYPE_NAME));
			}
			
			// Adjust Color
			switch (compound.getString(Tag.ENTITY_TYPE).toLowerCase()) {
				case MINECRAFT_HORSE:
					components.add(entityName.withStyle(ChatFormatting.DARK_GREEN));
					break;
				case MINECRAFT_PIG:
					// Pink
					components.add(entityName.withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ffc0cb"))));
					break;
				case MINECRAFT_STRIDER:
					components.add(entityName.withStyle(ChatFormatting.DARK_RED));
					break;
				default:
					components.add(entityName.withStyle(ChatFormatting.YELLOW));
					break;
			}
		}
	}

}
