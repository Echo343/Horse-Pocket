package com.blargsworkshop.horsepocket.item;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.blargsworkshop.common.text.Chat;
import com.blargsworkshop.horsepocket.HorsePocket;
import com.blargsworkshop.horsepocket.horse.Variants;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class PocketItem extends Item {

	public PocketItem() {
		super(new Item.Properties().tab(HorsePocket.TAB).stacksTo(1));
	}
	
	@Override
    public InteractionResult useOn(UseOnContext context) {
        CompoundTag compound = context.getItemInHand().getOrCreateTag();
        if (compound.getBoolean("has_entity")) {
            Optional<EntityType<?>> optional = EntityType.byString(compound.getString("entity_type"));
            if (optional.isPresent()) {
                Entity living = optional.get().create(context.getLevel());
                if (living != null) {
                    living.load(compound.getCompound("entity_data"));
                    // TODO top of block only?
                    BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
                    living.setPos(pos.getX() + 0.5, pos.getY() + 0.01, pos.getZ() + 0.5);
                    context.getLevel().addFreshEntity(living);
                    compound.putBoolean("has_entity", false);
    	            if (context.getPlayer().level.isClientSide) {
    	            	if (compound.getString("entity_type").equalsIgnoreCase("minecraft:horse")) {
	    	            	int variant = compound.getCompound("entity_data").getInt("Variant");
	    	            	Chat.addUnlocalizedChatMessage(context.getPlayer(), "Released " + Variants.INSTANCE.getUsageWordingByIndex(variant));
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
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity living, InteractionHand hand) {
        CompoundTag compound = stack.getOrCreateTag();
        if (!compound.getBoolean("has_entity")) {
        	if (living instanceof Saddleable && ((Saddleable)living).isSaddled() && living.isAlive()) {
        		if (living.isPassenger()) {        			
        			living.stopRiding();
        		}
        		living.ejectPassengers();

	            compound.putString("entity_type", living.getType().getRegistryName().toString());
	            compound.put("entity_data", living.saveWithoutId(new CompoundTag()));
	            compound.putString("entity_name", Component.Serializer.toJson(living.hasCustomName() ? living.getCustomName() : living.getDisplayName()));
	            compound.putBoolean("has_entity", true);
	            living.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
	
	            if (player.level.isClientSide) {
	            	if (compound.getString("entity_type").equalsIgnoreCase("minecraft:horse")) {
		            	int variant = compound.getCompound("entity_data").getInt("Variant");
		            	Chat.addUnlocalizedChatMessage(player, "Stowed " + Variants.INSTANCE.getUsageWordingByIndex(variant));
	            	} else {
	            		Chat.addUnlocalizedChatMessage(player, "Stowed a " + living.getType().getRegistryName().getPath());
	            	}
	            }
	            
	            player.setItemInHand(hand, stack);
	            return InteractionResult.sidedSuccess(player.level.isClientSide);
        	}
        }
        return InteractionResult.PASS;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        CompoundTag compound = stack.getOrCreateTag();
        if (compound.getBoolean("has_entity")) {
        	MutableComponent entityName = Component.Serializer.fromJson(compound.getString("entity_name"));
        	switch (compound.getString("entity_type").toLowerCase()) {
        		case "minecraft:horse":
        			components.add(new TextComponent(entityName.getString()).withStyle(ChatFormatting.DARK_AQUA));
        			int variant = compound.getCompound("entity_data").getInt("Variant");
        			components.add(new TextComponent(Variants.INSTANCE.getNameByIndex(variant)).withStyle(ChatFormatting.DARK_AQUA));
        			break;
        		case "minecraft:pig":
        			components.add(new TextComponent(entityName.getString()).withStyle(ChatFormatting.LIGHT_PURPLE));
        			break;
        		case "minecraft:strider":
        			components.add(new TextComponent(entityName.getString()).withStyle(ChatFormatting.DARK_RED));
        			break;
    			default:
    				components.add(new TextComponent(entityName.getString()).withStyle(ChatFormatting.YELLOW));
    				break;
        	}
        }
    }

}
