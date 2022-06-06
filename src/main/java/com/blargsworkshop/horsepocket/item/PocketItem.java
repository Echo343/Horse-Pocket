package com.blargsworkshop.horsepocket.item;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.blargsworkshop.horsepocket.HorsePocket;
import com.blargsworkshop.horsepocket.enums.Variants;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

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
					Level level2 = context.getLevel();
					if (!level2.isClientSide) {
						ServerLevel level = (ServerLevel)level2;
						entity.load(compound.getCompound(Tag.ENTITY_DATA));
						// TODO top of block only?
//						BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
//						entity.setPos(pos.getX() + 0.5, pos.getY() + 0.01, pos.getZ() + 0.5);
						BlockPos blockpos = context.getClickedPos();
						Direction direction = context.getClickedFace();
						BlockState blockstate = level.getBlockState(blockpos);
						BlockPos blockpos1;
						if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
							blockpos1 = blockpos;
						} else {
							blockpos1 = blockpos.relative(direction);
						}
						if (!Objects.equals(blockpos, blockpos1)) {
							entity.setPos(blockpos1.getX() + 0.5D, blockpos1.getY() + 1D, blockpos1.getZ() + 0.5D);

						} else {
						}
						Double d0;
						if (!Objects.equals(blockpos, blockpos1)) {
							entity.setPos(blockpos1.getX() + 0.5D, blockpos1.getY() + 1D, blockpos1.getZ() + 0.5D);
							d0 = getYOffset(level, blockpos1, direction == Direction.UP, entity.getBoundingBox());
						} else {
							d0 = 0.0D;
						}

						entity.moveTo(blockpos1.getX() + 0.5D, blockpos1.getY() + d0, blockpos1.getZ() + 0.5D, Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);

						level.addFreshEntity(entity);

					}
					compound.putBoolean(Tag.HAS_ENTITY, false);

//					if (context.getPlayer().level.isClientSide) {
//						if (entity.hasCustomName()) {
//							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released " + entity.getCustomName().getString());
//						} else if (entity.getType().getRegistryName().toString().equalsIgnoreCase(MINECRAFT_HORSE)) {
//							int variant = compound.getCompound(Tag.ENTITY_DATA).getInt(VARIANT);
//							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released " + Variants.INSTANCE.getDescriptionByVariant(variant).toLowerCase());
//						} else {
//							Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released a " + entity.getType().getRegistryName().getPath());
//						}
//					}

					return InteractionResult.sidedSuccess(context.getPlayer().level.isClientSide);
				}
			}
		}
		return super.useOn(context);
	}

	protected static double getYOffset(LevelReader p_20626_, BlockPos p_20627_, boolean p_20628_, AABB p_20629_) {
		AABB aabb = new AABB(p_20627_);
		if (p_20628_) {
			aabb = aabb.expandTowards(0.0D, -1.0D, 0.0D);
		}

		Iterable<VoxelShape> iterable = p_20626_.getCollisions((Entity) null, aabb);
		return 1.0D + Shapes.collide(Direction.Axis.Y, p_20629_, iterable, p_20628_ ? -2.0D : -1.0D);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
		CompoundTag compound = stack.getOrCreateTag();

		if (compound.getBoolean(Tag.HAS_ENTITY)) {
			MutableComponent entityName = null;

			if (compound.getBoolean(Tag.HAS_CUSTOM_NAME)) {
				entityName = new TranslatableComponent("text.tooltip.custom_name_type_name", compound.getString(Tag.CUSTOM_NAME), compound.getString(Tag.TYPE_NAME));

			} else if (compound.getString(Tag.ENTITY_TYPE).equalsIgnoreCase(MINECRAFT_HORSE)) {
				int variant = compound.getCompound(Tag.ENTITY_DATA).getInt(VARIANT);
				entityName = new TranslatableComponent(Variants.INSTANCE.getDescriptionByVariant(variant));

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
