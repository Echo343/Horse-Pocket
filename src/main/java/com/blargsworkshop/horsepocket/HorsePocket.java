package com.blargsworkshop.horsepocket;

import java.util.Arrays;
import java.util.Optional;

import com.blargsworkshop.horsepocket.init.Registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("horsepocket")
public class HorsePocket {

	public static final boolean DEBUG = false;

	// Directly reference a log4j logger.
//    private static final Logger LOGGER = LogManager.getLogger();

	public static final String MOD_ID = "horsepocket";
	public static final CreativeModeTab TAB;

	static {
		Optional<CreativeModeTab> op = Arrays.stream(CreativeModeTab.TABS).filter((tab) -> {
			return tab.getRecipeFolderName().equalsIgnoreCase("blargsTab");
		}).findFirst();

		if (op.isPresent()) {
			TAB = op.get();
		} else {
			TAB = new CreativeModeTab("blargsTab") {
				@Override
				@OnlyIn(Dist.CLIENT)
				public ItemStack makeIcon() {
					return new ItemStack(Blocks.SMITHING_TABLE);
				}
			};
		}
	}

	public HorsePocket() {
		Registry.init();
	}
}
