package godxero.dto;

public enum FoodItemCategory {
	BURGERS, SUBMARINES, BEVERAGES, OTHER;

	public static FoodItemCategory getCategory (String category) {
		if (category == null) return null;

		return switch (category.toUpperCase()) {
			case "BURGERS" -> FoodItemCategory.BURGERS;
			case "SUBMARINES" -> FoodItemCategory.SUBMARINES;
			case "BEVERAGES" -> FoodItemCategory.BEVERAGES;
			case "OTHER" -> FoodItemCategory.OTHER;
			default -> null;
		};
	}

	public static int asIndex (FoodItemCategory category) {
		if (category == null) return -1;

		return switch (category) {
			case FoodItemCategory.BURGERS -> 0;
			case FoodItemCategory.SUBMARINES -> 1;
			case FoodItemCategory.BEVERAGES -> 2;
			case FoodItemCategory.OTHER -> 3;
		};
	}
}
