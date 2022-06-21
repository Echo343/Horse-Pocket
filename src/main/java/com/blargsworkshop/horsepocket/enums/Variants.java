package com.blargsworkshop.horsepocket.enums;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

public enum Variants {
	INSTANCE;
	
	private Map<Integer, String> vars = new HashMap<>();
		
	Variants() {
		vars.put(0, "a_white_horse");
		vars.put(1, "a_creamy_horse");
		vars.put(2, "a_chestnut_horse");
		vars.put(3, "a_brown_horse");
		vars.put(4, "a_black_horse");
		vars.put(5, "a_gray_horse");
		vars.put(6, "a_dark_brown_horse");
		
		vars.put(256, "a_white_horse_with_white_accents");
		vars.put(257, "a_creamy_horse_with_white_accents");
		vars.put(258, "a_chestnut_horse_with_white_accents");
		vars.put(259, "a_brown_horse_with_white_accents");
		vars.put(260, "a_black_horse_with_white_accents");
		vars.put(261, "a_gray_horse_with_white_accents");
		vars.put(262, "a_dark_brown_horse_with_white_accents");
		
		vars.put(512, "a_white_horse_with_white_fields");
		vars.put(513, "a_creamy_horse_with_white_fields");
		vars.put(514, "a_chestnut_horse_with_white_fields");
		vars.put(515, "a_brown_horse_with_white_fields");
		vars.put(516, "a_black_horse_with_white_fields");
		vars.put(517, "a_gray_horse_with_white_fields");
		vars.put(518, "a_dark_brown_horse_with_white_fields");
		
		vars.put(768, "a_white_horse_with_white_dots");
		vars.put(769, "a_creamy_horse_with_white_dots");
		vars.put(770, "a_chestnut_horse_with_white_dots");
		vars.put(771, "a_brown_horse_with_white_dots");
		vars.put(772, "a_black_horse_with_white_dots");
		vars.put(773, "a_gray_horse_with_white_dots");
		vars.put(774, "a_dark_brown_horse_with_white_dots");
		
		vars.put(1024, "a_white_horse_with_black_dots");
		vars.put(1025, "a_creamy_horse_with_black_dots");
		vars.put(1026, "a_chestnut_horse_with_black_dots");
		vars.put(1027, "a_brown_horse_with_black_dots");
		vars.put(1028, "a_black_horse_with_black_dots");
		vars.put(1029, "a_gray_horse_with_black_dots");
		vars.put(1030, "a_dark_brown_horse_with_black_dots");
	}
	
	public String getDescriptionByVariant(@NotNull Integer index) {
		return vars.containsKey(index) ? "text.variant." + vars.get(index) : "Unknown - " + index;
	}	
}
