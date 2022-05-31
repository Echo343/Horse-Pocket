package com.blargsworkshop.horsepocket;

import com.blargsworkshop.horsepocket.init.Registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("horsepocket")
public class HorsePocket
{
	public static final boolean DEBUG = !false;
	
    // Directly reference a log4j logger.
//    private static final Logger LOGGER = LogManager.getLogger();
    
    public static final String MOD_ID = "horsepocket";

    public HorsePocket() {
        // Register the setup method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
        // Register the doClientStuff method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        
        Registry.init();

        // Register ourselves for server and other game events we are interested in
//        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        //Networking.registerMessages();
    }
    
    public static final CreativeModeTab TAB = new CreativeModeTab("blargsTab") {
        @Override
		@OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.SMITHING_TABLE);
        }
    };

//    private void doClientStuff(final FMLClientSetupEvent event) {
//    	ItemModelsProperties.func_239418_a_(Registry.Items.POCKET, new ResourceLocation(HorsePocket.MOD_ID, "stowed"), 
//				(stack, world, entity) -> {
//					if (entity instanceof PlayerEntity) {
//	    				IPocket pocket = stack.getCapability(PocketProvider.POCKET_CAPABILITY).orElse(null);
//	    				if (pocket != null) {
//	    					return pocket.getTestFlag() ? 1F : 0F;
//	    				}
//					}
//					return 0F;
//				});
//    }
   
//    @SubscribeEvent
//	public void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
//		if (event.getObject().getItem() instanceof HorsePocketItem) {
//			event.addCapability(new ResourceLocation(HorsePocket.MOD_ID, "pocket-capability"), new PocketProvider());
//		}
//	}
}
