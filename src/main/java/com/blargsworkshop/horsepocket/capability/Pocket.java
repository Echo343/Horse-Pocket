package com.blargsworkshop.horsepocket.capability;

import net.minecraft.world.entity.LivingEntity;

public class Pocket implements IPocket {
	
	private LivingEntity entity = null;

	@Override
	public boolean hasSaddledEntity() {
		return entity != null;
	}

	@Override
	public void setSaddledEntity(LivingEntity entity) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean isSet = false;

	@Override
	public boolean getTestFlag() {
		return this.isSet;
	}

	@Override
	public void setTestFlag(boolean bob) {
		this.isSet = bob;
	}

}
