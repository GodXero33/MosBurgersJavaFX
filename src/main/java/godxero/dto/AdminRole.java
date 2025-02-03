package godxero.dto;

public enum AdminRole {
	CASHIER, MANAGER;

	public static AdminRole getRole (String role) {
		if (role == null) return null;

		return switch (role.toUpperCase()) {
			case "CASHIER" -> AdminRole.CASHIER;
			case "MANAGER" -> AdminRole.MANAGER;
			default -> null;
		};
	}
}
