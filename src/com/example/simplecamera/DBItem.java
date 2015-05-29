package com.example.simplecamera;

public class DBItem {

	private static String name;
	private static double latitude;
	private static double longitude;
	private static String path;

	public DBItem(String name, double lon, double lat, String path) {
		DBItem.name = name;
		DBItem.longitude = lon;
		DBItem.latitude = lat;
		DBItem.path = path;
	}

	public static String getName() {
		return name;
	}

	public static double getLat() {
		return latitude;
	}

	public static double getLong() {
		return longitude;
	}

	public static String getPath() {
		return path;
	}
	
	
}
