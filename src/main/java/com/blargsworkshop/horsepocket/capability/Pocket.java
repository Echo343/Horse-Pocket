package com.blargsworkshop.horsepocket.capability;

import net.minecraft.nbt.CompoundTag;
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
	
	private static final String NBT_KEY_IS_SET = "damageDealt";
	private boolean isSet = false;

	@Override
	public boolean getTestFlag() {
		return this.isSet;
	}

	@Override
	public void setTestFlag(boolean bob) {
		this.isSet = bob;
	}

	@Override
	public CompoundTag serializeNBT() {
		final CompoundTag tag = new CompoundTag();
        tag.putBoolean(NBT_KEY_IS_SET, this.isSet);
        return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.isSet = nbt.getBoolean(NBT_KEY_IS_SET);
	}

}
