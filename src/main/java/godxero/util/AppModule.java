package godxero.util;

import com.google.inject.AbstractModule;
import godxero.repository.custom.AdminRepository;
import godxero.repository.custom.CustomerRepository;
import godxero.repository.custom.FoodItemRepository;
import godxero.repository.custom.OrderRepository;
import godxero.repository.custom.impl.AdminRepositoryImpl;
import godxero.repository.custom.impl.CustomerRepositoryImpl;
import godxero.repository.custom.impl.FoodItemRepositoryImpl;
import godxero.repository.custom.impl.OrderRepositoryImpl;
import godxero.service.custom.AdminService;
import godxero.service.custom.CustomerService;
import godxero.service.custom.FoodItemService;
import godxero.service.custom.OrderService;
import godxero.service.custom.impl.AdminServiceImpl;
import godxero.service.custom.impl.CustomerServiceImpl;
import godxero.service.custom.impl.FoodItemServiceImpl;
import godxero.service.custom.impl.OrderServiceImpl;

public class AppModule extends AbstractModule {
	@Override
	protected void configure () {
		this.bind(AdminService.class).to(AdminServiceImpl.class);
		this.bind(CustomerService.class).to(CustomerServiceImpl.class);
		this.bind(FoodItemService.class).to(FoodItemServiceImpl.class);
		this.bind(OrderService.class).to(OrderServiceImpl.class);

		this.bind(AdminRepository.class).to(AdminRepositoryImpl.class);
		this.bind(CustomerRepository.class).to(CustomerRepositoryImpl.class);
		this.bind(FoodItemRepository.class).to(FoodItemRepositoryImpl.class);
		this.bind(OrderRepository.class).to(OrderRepositoryImpl.class);
	}
}
