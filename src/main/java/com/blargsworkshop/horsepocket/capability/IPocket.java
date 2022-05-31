package com.blargsworkshop.horsepocket.capability;

import net.minecraft.world.entity.LivingEntity;

public interface IPocket {
	
	public boolean getTestFlag();
	public void setTestFlag(boolean bob);
	
	public boolean hasSaddledEntity();
	
	public void setSaddledEntity(LivingEntity entity);

}
