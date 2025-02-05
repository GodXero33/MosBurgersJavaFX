package godxero.repository;

import godxero.repository.custom.impl.AdminRepositoryImpl;
import godxero.repository.custom.impl.CustomerRepositoryImpl;
import godxero.repository.custom.impl.FoodItemRepositoryImpl;
import godxero.repository.custom.impl.OrderRepositoryImpl;
import godxero.util.RepositoryType;

public class RepositoryFactory {
	private static RepositoryFactory instance;

	private RepositoryFactory () {}

	public static RepositoryFactory getInstance () {
		if (RepositoryFactory.instance == null) RepositoryFactory.instance = new RepositoryFactory();

		return RepositoryFactory.instance;
	}

	@SuppressWarnings("unchecked")
	public <T extends SuperRepository> T getRepositoryType (RepositoryType repositoryType) {
		return switch (repositoryType) {
			case RepositoryType.ADMIN -> (T) AdminRepositoryImpl.getInstance();
			case RepositoryType.CUSTOMER -> (T) CustomerRepositoryImpl.getInstance();
			case RepositoryType.FOOD_ITEM -> (T) FoodItemRepositoryImpl.getInstance();
			case RepositoryType.ORDER -> (T) OrderRepositoryImpl.getInstance();
		};
	}
}
