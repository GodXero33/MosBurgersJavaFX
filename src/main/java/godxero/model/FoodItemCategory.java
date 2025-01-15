package godxero.model;

public enum FoodItemCategory {
	BURGERS, SUBMARINES, BEVERAGES, OTHER;

	public static FoodItemCategory getCategory (String category) {
		if (category == null) return null;

		return switch (category.toUpperCase()) {
			case "BURGERS" -> BURGERS;
			case "SUBMARINES" -> SUBMARINES;
			case "BEVERAGES" -> BEVERAGES;
			case "OTHER" -> OTHER;
			default -> null;
		};
	}
}
