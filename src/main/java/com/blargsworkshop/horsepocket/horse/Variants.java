package com.blargsworkshop.horsepocket.horse;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

public enum Variants {
	INSTANCE;
	
	private Map<Integer, String> vars = new HashMap<>();
	private Map<Integer, String> stowedRelease = new HashMap<>();
		
	Variants() {
		vars.put(0, "White");
		vars.put(1, "Creamy");
		vars.put(2, "Chestnut");
		vars.put(3, "Brown");
		vars.put(4, "Black");
		vars.put(5, "Gray");
		vars.put(6, "Dark Brown");
		
		vars.put(256, "White");
		vars.put(257, "Creamy with white accents");
		vars.put(258, "Chestnut with white accents");
		vars.put(259, "Brown with white accents");
		vars.put(260, "Black with white accents");
		vars.put(261, "Gray with white accents");
		vars.put(262, "Dark Brown with white accents");
		
		vars.put(512, "White");
		vars.put(513, "Creamy with white fields");
		vars.put(514, "Chestnut with white fields");
		vars.put(515, "Brown with white fields");
		vars.put(516, "Black with white fields");
		vars.put(517, "Gray with white fields");
		vars.put(518, "Dark Brown with white fields");
		
		vars.put(768, "White");
		vars.put(769, "Creamy with white dots");
		vars.put(770, "Chestnut with white dots");
		vars.put(771, "Brown with white dots");
		vars.put(772, "Black with white dots");
		vars.put(773, "Gray with white dots");
		vars.put(774, "Dark Brown with white dots");
		
		vars.put(1024, "White with black dots");
		vars.put(1025, "Creamy with black dots");
		vars.put(1026, "Chestnut with black dots");
		vars.put(1027, "Brown with black dots");
		vars.put(1028, "Black");
		vars.put(1029, "Gray with black dots");
		vars.put(1030, "Dark Brown with black dots");
		
		stowedRelease.put(0, "a White horse");
		stowedRelease.put(1, "a Creamy horse");
		stowedRelease.put(2, "a Chestnut horse");
		stowedRelease.put(3, "a Brown horse");
		stowedRelease.put(4, "a Black horse");
		stowedRelease.put(5, "a Gray horse");
		stowedRelease.put(6, "a Dark Brown horse");

		stowedRelease.put(256, "a White horse");
		stowedRelease.put(257, "a Creamy horse with white accents");
		stowedRelease.put(258, "a Chestnut horse with white accents");
		stowedRelease.put(259, "a Brown horse with white accents");
		stowedRelease.put(260, "a Black horse with white accents");
		stowedRelease.put(261, "a Gray horse with white accents");
		stowedRelease.put(262, "a Dark Brown horse with white accents");

		stowedRelease.put(512, "a White horse");
		stowedRelease.put(513, "a Creamy horse with white fields");
		stowedRelease.put(514, "a Chestnut horse with white fields");
		stowedRelease.put(515, "a Brown horse with white fields");
		stowedRelease.put(516, "a Black horse with white fields");
		stowedRelease.put(517, "a Gray horse with white fields");
		stowedRelease.put(518, "a Dark Brown horse with white fields");

		stowedRelease.put(768, "a White horse");
		stowedRelease.put(769, "a Creamy horse with white dots");
		stowedRelease.put(770, "a Chestnut horse with white dots");
		stowedRelease.put(771, "a Brown horse with white dots");
		stowedRelease.put(772, "a Black horse with white dots");
		stowedRelease.put(773, "a Gray horse with white dots");
		stowedRelease.put(774, "a Dark Brown horse with white dots");

		stowedRelease.put(1024, "a White horse with black dots");
		stowedRelease.put(1025, "a Creamy horse with black dots");
		stowedRelease.put(1026, "a Chestnut horse with black dots");
		stowedRelease.put(1027, "a Brown horse with black dots");
		stowedRelease.put(1028, "a Black horse");
		stowedRelease.put(1029, "a Gray horse with black dots");
		stowedRelease.put(1030, "a Dark Brown horse with black dots");
	}
	
	public String getNameByIndex(@NotNull Integer index) {
		return vars.getOrDefault(index, "Unknown " + index);
	}
	
	public String getUsageWordingByIndex(@NotNull Integer index) {
		return stowedRelease.getOrDefault(index, "Unknown " + index);
	}
}
