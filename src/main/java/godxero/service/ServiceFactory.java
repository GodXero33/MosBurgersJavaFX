package godxero.service;

import godxero.service.custom.impl.AdminServiceImpl;
import godxero.service.custom.impl.CustomerServiceImpl;
import godxero.service.custom.impl.FoodItemServiceImpl;
import godxero.service.custom.impl.OrderServiceImpl;
import godxero.util.ServiceType;

public class ServiceFactory {
	private static ServiceFactory instance;

	private ServiceFactory () {}

	public static ServiceFactory getInstance () {
		if (ServiceFactory.instance == null) ServiceFactory.instance = new ServiceFactory();

		return ServiceFactory.instance;
	}

	@SuppressWarnings("unchecked")
	public <T extends SuperService> T getServiceType (ServiceType serviceType) {
		return switch (serviceType) {
			case ServiceType.ADMIN -> (T) new AdminServiceImpl();
			case ServiceType.CUSTOMER -> (T) new CustomerServiceImpl();
			case ServiceType.FOOD_ITEM -> (T) new FoodItemServiceImpl();
			case ServiceType.ORDER -> (T) new OrderServiceImpl();
		};
	}
}
