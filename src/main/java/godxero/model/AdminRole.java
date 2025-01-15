package godxero.model;

public enum AdminRole {
	CASHIER, MANAGER;

	public static AdminRole getRole (String role) {
		if (role == null) return null;

		return switch (role.toUpperCase()) {
			case "CASHIER" -> CASHIER;
			case "MANAGER" -> MANAGER;
			default -> null;
		};
	}
}
