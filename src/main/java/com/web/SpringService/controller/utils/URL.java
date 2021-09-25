package com.web.SpringService.controller.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	public static List<Integer> decodeIntList(String s) {
		return Arrays.asList(s.split(",")).stream()
				.map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
