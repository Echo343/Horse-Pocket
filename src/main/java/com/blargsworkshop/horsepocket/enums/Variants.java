package com.blargsworkshop.horsepocket.enums;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

public enum Variants {
	INSTANCE;
	
	private Map<Integer, String> vars = new HashMap<>();
		
	Variants() {
		vars.put(0, "a White horse");
		vars.put(1, "a Creamy horse");
		vars.put(2, "a Chestnut horse");
		vars.put(3, "a Brown horse");
		vars.put(4, "a Black horse");
		vars.put(5, "a Gray horse");
		vars.put(6, "a Dark Brown horse");

		vars.put(256, "a White horse");
		vars.put(257, "a Creamy horse with white accents");
		vars.put(258, "a Chestnut horse with white accents");
		vars.put(259, "a Brown horse with white accents");
		vars.put(260, "a Black horse with white accents");
		vars.put(261, "a Gray horse with white accents");
		vars.put(262, "a Dark Brown horse with white accents");

		vars.put(512, "a White horse");
		vars.put(513, "a Creamy horse with white fields");
		vars.put(514, "a Chestnut horse with white fields");
		vars.put(515, "a Brown horse with white fields");
		vars.put(516, "a Black horse with white fields");
		vars.put(517, "a Gray horse with white fields");
		vars.put(518, "a Dark Brown horse with white fields");

		vars.put(768, "a White horse");
		vars.put(769, "a Creamy horse with white dots");
		vars.put(770, "a Chestnut horse with white dots");
		vars.put(771, "a Brown horse with white dots");
		vars.put(772, "a Black horse with white dots");
		vars.put(773, "a Gray horse with white dots");
		vars.put(774, "a Dark Brown horse with white dots");

		vars.put(1024, "a White horse with black dots");
		vars.put(1025, "a Creamy horse with black dots");
		vars.put(1026, "a Chestnut horse with black dots");
		vars.put(1027, "a Brown horse with black dots");
		vars.put(1028, "a Black horse");
		vars.put(1029, "a Gray horse with black dots");
		vars.put(1030, "a Dark Brown horse with black dots");
	}
	
	public String getDescriptionByVariant(@NotNull Integer index) {
		return vars.getOrDefault(index, "Unknown " + index);
	}	
}
