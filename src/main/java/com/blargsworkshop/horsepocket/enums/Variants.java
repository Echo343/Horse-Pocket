package com.blargsworkshop.horsepocket.enums;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

public enum Variants {
	INSTANCE;
	
	private Map<Integer, String> vars = new HashMap<>();
		
	Variants() {
		vars.put(0, "A White horse");
		vars.put(1, "A Creamy horse");
		vars.put(2, "A Chestnut horse");
		vars.put(3, "A Brown horse");
		vars.put(4, "A Black horse");
		vars.put(5, "A Gray horse");
		vars.put(6, "A Dark Brown horse");

		vars.put(256, "A White horse");
		vars.put(257, "A Creamy horse with white accents");
		vars.put(258, "A Chestnut horse with white accents");
		vars.put(259, "A Brown horse with white accents");
		vars.put(260, "A Black horse with white accents");
		vars.put(261, "A Gray horse with white accents");
		vars.put(262, "A Dark Brown horse with white accents");

		vars.put(512, "A White horse");
		vars.put(513, "A Creamy horse with white fields");
		vars.put(514, "A Chestnut horse with white fields");
		vars.put(515, "A Brown horse with white fields");
		vars.put(516, "A Black horse with white fields");
		vars.put(517, "A Gray horse with white fields");
		vars.put(518, "A Dark Brown horse with white fields");

		vars.put(768, "A White horse");
		vars.put(769, "A Creamy horse with white dots");
		vars.put(770, "A Chestnut horse with white dots");
		vars.put(771, "A Brown horse with white dots");
		vars.put(772, "A Black horse with white dots");
		vars.put(773, "A Gray horse with white dots");
		vars.put(774, "A Dark Brown horse with white dots");

		vars.put(1024, "A White horse with black dots");
		vars.put(1025, "A Creamy horse with black dots");
		vars.put(1026, "A Chestnut horse with black dots");
		vars.put(1027, "A Brown horse with black dots");
		vars.put(1028, "A Black horse");
		vars.put(1029, "A Gray horse with black dots");
		vars.put(1030, "A Dark Brown horse with black dots");
	}
	
	public String getDescriptionByVariant(@NotNull Integer index) {
		return vars.getOrDefault(index, "Unknown " + index);
	}	
}
