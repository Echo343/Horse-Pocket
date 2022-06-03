package com.blargsworkshop.horsepocket.item;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.blargsworkshop.common.text.Chat;
import com.blargsworkshop.horsepocket.HorsePocket;
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
		public static final String ENTITY_DATA = "entity_data";
		public static final String ENTITY_TYPE = "entity_type";
		public static final String HAS_ENTITY = "has_entity";
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
				Entity living = optional.get().create(context.getLevel());
				if (living != null) {
					living.load(compound.getCompound(Tag.ENTITY_DATA));
					// TODO top of block only?
					BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
					living.setPos(pos.getX() + 0.5, pos.getY() + 0.01, pos.getZ() + 0.5);
					context.getLevel().addFreshEntity(living);
					compound.putBoolean(Tag.HAS_ENTITY, false);

					if (context.getPlayer().level.isClientSide) {
						if (compound.getBoolean("has_custom_name")) {
							MutableComponent entityName = Component.Serializer.fromJson(compound.getString("entity_name"));
							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released " + entityName.getString());
						} else if (compound.getString(Tag.ENTITY_TYPE).equalsIgnoreCase(MINECRAFT_HORSE)) {
							int variant = compound.getCompound(Tag.ENTITY_DATA).getInt(VARIANT);
							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released " + Variants.INSTANCE.getDescriptionByVariant(variant));
						} else {
							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released a " + living.getType().getRegistryName().getPath());
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
			MutableComponent entityName = Component.Serializer.fromJson(compound.getString("entity_name"));
			if (compound.getBoolean("has_custom_name")) {
				entityName.append(" (" + compound.getString("entity_display_name") + ")");
			}
			switch (compound.getString(Tag.ENTITY_TYPE).toLowerCase()) {
				case MINECRAFT_HORSE:
					components.add(new TextComponent(entityName.getString()).withStyle(ChatFormatting.YELLOW));
					int variant = compound.getCompound(Tag.ENTITY_DATA).getInt(VARIANT);
					components.add(new TextComponent(Variants.INSTANCE.getDescriptionByVariant(variant)).withStyle(ChatFormatting.YELLOW));
					break;
				case MINECRAFT_PIG:
					components.add(new TextComponent(entityName.getString()).withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ffc0cb"))));
					break;
				case MINECRAFT_STRIDER:
					components.add(new TextComponent(entityName.getString()).withStyle(ChatFormatting.DARK_RED));
					break;
				default:
					components.add(new TextComponent(entityName.getString()).withStyle(ChatFormatting.AQUA));
					break;
			}
		}
	}

}
