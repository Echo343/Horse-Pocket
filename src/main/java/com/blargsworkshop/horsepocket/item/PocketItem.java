package com.blargsworkshop.horsepocket.item;

import com.blargsworkshop.common.text.Chat;
import com.blargsworkshop.common.utility.WorldHelper;
import com.blargsworkshop.horsepocket.HorsePocket;
import com.blargsworkshop.horsepocket.capability.PocketCapability;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PocketItem extends Item {

	public PocketItem() {
		super(new Item.Properties().tab(HorsePocket.TAB).stacksTo(1));
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);
		stack.getCapability(PocketCapability.INSTANCE).ifPresent((pocket) -> {
			pocket.setTestFlag(!pocket.getTestFlag());
			Chat.addUnlocalizedChatMessage(playerIn, (WorldHelper.isClient(worldIn) ? "client:" : "server:") + "getTestFlag(" + pocket.getTestFlag() + ")");
		});
		return InteractionResultHolder.pass(stack);
	}

}
