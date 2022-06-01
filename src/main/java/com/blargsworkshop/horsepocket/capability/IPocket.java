package com.blargsworkshop.horsepocket.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPocket extends INBTSerializable<CompoundTag> {
	
	public boolean getTestFlag();
	public void setTestFlag(boolean bob);
	
	public boolean hasSaddledEntity();
	
	public void setSaddledEntity(LivingEntity entity);

}
