package org.abrohamovich.littleshop.infrustructure.config;

import org.abrohamovich.littleshop.application.port.in.category.CreateCategoryUseCase;
import org.abrohamovich.littleshop.application.port.in.category.DeleteCategoryUseCase;
import org.abrohamovich.littleshop.application.port.in.category.GetCategoryUseCase;
import org.abrohamovich.littleshop.application.port.in.category.UpdateCategoryUseCase;
import org.abrohamovich.littleshop.application.port.in.customer.CreateCustomerUseCase;
import org.abrohamovich.littleshop.application.port.in.customer.DeleteCustomerUseCase;
import org.abrohamovich.littleshop.application.port.in.customer.GetCustomerUseCase;
import org.abrohamovich.littleshop.application.port.in.customer.UpdateCustomerUseCase;
import org.abrohamovich.littleshop.application.port.in.offer.CreateOfferUseCase;
import org.abrohamovich.littleshop.application.port.in.offer.DeleteOfferUseCase;
import org.abrohamovich.littleshop.application.port.in.offer.GetOfferUseCase;
import org.abrohamovich.littleshop.application.port.in.offer.UpdateOfferUseCase;
import org.abrohamovich.littleshop.application.port.in.order.*;
import org.abrohamovich.littleshop.application.port.in.supplier.CreateSupplierUseCase;
import org.abrohamovich.littleshop.application.port.in.supplier.DeleteSupplierUseCase;
import org.abrohamovich.littleshop.application.port.in.supplier.GetSupplierUseCase;
import org.abrohamovich.littleshop.application.port.in.supplier.UpdateSupplierUseCase;
import org.abrohamovich.littleshop.application.port.in.user.CreateUserUseCase;
import org.abrohamovich.littleshop.application.port.in.user.DeleteUserUseCase;
import org.abrohamovich.littleshop.application.port.in.user.GetUserUseCase;
import org.abrohamovich.littleshop.application.port.in.user.UpdateUserUseCase;
import org.abrohamovich.littleshop.application.port.out.persistence.*;
import org.abrohamovich.littleshop.application.usecase.category.CreateCategoryService;
import org.abrohamovich.littleshop.application.usecase.category.DeleteCategoryService;
import org.abrohamovich.littleshop.application.usecase.category.GetCategoryService;
import org.abrohamovich.littleshop.application.usecase.category.UpdateCategoryService;
import org.abrohamovich.littleshop.application.usecase.customer.CreateCustomerService;
import org.abrohamovich.littleshop.application.usecase.customer.DeleteCustomerService;
import org.abrohamovich.littleshop.application.usecase.customer.GetCustomerService;
import org.abrohamovich.littleshop.application.usecase.customer.UpdateCustomerService;
import org.abrohamovich.littleshop.application.usecase.offer.CreateOfferService;
import org.abrohamovich.littleshop.application.usecase.offer.DeleteOfferService;
import org.abrohamovich.littleshop.application.usecase.offer.GetOfferService;
import org.abrohamovich.littleshop.application.usecase.offer.UpdateOfferService;
import org.abrohamovich.littleshop.application.usecase.order.*;
import org.abrohamovich.littleshop.application.usecase.supplier.CreateSupplierService;
import org.abrohamovich.littleshop.application.usecase.supplier.DeleteSupplierService;
import org.abrohamovich.littleshop.application.usecase.supplier.GetSupplierService;
import org.abrohamovich.littleshop.application.usecase.supplier.UpdateSupplierService;
import org.abrohamovich.littleshop.application.usecase.user.CreateUserService;
import org.abrohamovich.littleshop.application.usecase.user.DeleteUserService;
import org.abrohamovich.littleshop.application.usecase.user.GetUserService;
import org.abrohamovich.littleshop.application.usecase.user.UpdateUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CreateCategoryUseCase createCategoryUseCase(CategoryRepositoryPort categoryRepositoryPort) {
        return new CreateCategoryService(categoryRepositoryPort);
    }

    @Bean
    public GetCategoryUseCase getCategoryUseCase(CategoryRepositoryPort categoryRepositoryPort) {
        return new GetCategoryService(categoryRepositoryPort);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase(CategoryRepositoryPort categoryRepositoryPort) {
        return new UpdateCategoryService(categoryRepositoryPort);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase(CategoryRepositoryPort categoryRepositoryPort) {
        return new DeleteCategoryService(categoryRepositoryPort);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new CreateCustomerService(customerRepositoryPort);
    }

    @Bean
    public GetCustomerUseCase getCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new GetCustomerService(customerRepositoryPort);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new UpdateCustomerService(customerRepositoryPort);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(CustomerRepositoryPort customerRepositoryPort) {
        return new DeleteCustomerService(customerRepositoryPort);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepositoryPort userRepositoryPort) {
        return new CreateUserService(userRepositoryPort);
    }

    @Bean
    public GetUserUseCase getUserUseCase(UserRepositoryPort userRepositoryPort) {
        return new GetUserService(userRepositoryPort);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UserRepositoryPort userRepositoryPort) {
        return new UpdateUserService(userRepositoryPort);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepositoryPort userRepositoryPort) {
        return new DeleteUserService(userRepositoryPort);
    }

    @Bean
    public CreateSupplierUseCase createSupplierUseCase(SupplierRepositoryPort supplierRepositoryPort) {
        return new CreateSupplierService(supplierRepositoryPort);
    }

    @Bean
    public GetSupplierUseCase getSupplierUseCase(SupplierRepositoryPort supplierRepositoryPort) {
        return new GetSupplierService(supplierRepositoryPort);
    }

    @Bean
    public UpdateSupplierUseCase updateSupplierUseCase(SupplierRepositoryPort supplierRepositoryPort) {
        return new UpdateSupplierService(supplierRepositoryPort);
    }

    @Bean
    public DeleteSupplierUseCase deleteSupplierUseCase(SupplierRepositoryPort supplierRepositoryPort) {
        return new DeleteSupplierService(supplierRepositoryPort);
    }

    @Bean
    public CreateOfferUseCase createOfferUseCase(OfferRepositoryPort offerRepositoryPort, CategoryRepositoryPort categoryRepositoryPort,
                                                 SupplierRepositoryPort supplierRepositoryPort) {
        return new CreateOfferService(offerRepositoryPort,  categoryRepositoryPort, supplierRepositoryPort);
    }

    @Bean
    public GetOfferUseCase getOfferUseCase(OfferRepositoryPort offerRepositoryPort) {
        return new GetOfferService(offerRepositoryPort);
    }

    @Bean
    public UpdateOfferUseCase updateOfferUseCase(OfferRepositoryPort offerRepositoryPort, CategoryRepositoryPort categoryRepositoryPort,
                                                 SupplierRepositoryPort supplierRepositoryPort) {
        return new UpdateOfferService(offerRepositoryPort,  categoryRepositoryPort, supplierRepositoryPort);
    }

    @Bean
    public DeleteOfferUseCase deleteOfferUseCase(OfferRepositoryPort offerRepositoryPort) {
        return new DeleteOfferService(offerRepositoryPort);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepositoryPort orderRepositoryPort, OfferRepositoryPort offerRepositoryPort,
                                                 CustomerRepositoryPort customerRepositoryPort, UserRepositoryPort userRepositoryPort) {
        return new CreateOrderService(orderRepositoryPort, offerRepositoryPort, customerRepositoryPort, userRepositoryPort);
    }

    @Bean
    public AddOrderItemToOrderUseCase addOrderItemToOrderUseCase(OrderRepositoryPort orderRepositoryPort, OfferRepositoryPort offerRepositoryPort) {
        return new AddOrderItemToOrderService(orderRepositoryPort, offerRepositoryPort);
    }

    @Bean
    public ChangeOrderStatusUseCase changeOrderStatusUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new ChangeOrderStatusService(orderRepositoryPort);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new GetOrderService(orderRepositoryPort);
    }

    @Bean
    public DeleteOrderUseCase deleteOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new DeleteOrderService(orderRepositoryPort);
    }

    @Bean
    public RemoveOrderItemFromOrderUseCase removeOrderItemFromOrderUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new RemoveOrderItemFromOrderService(orderRepositoryPort);
    }

    @Bean
    public UpdateOrderItemQuantityUseCase updateOrderItemQuantityUseCase(OrderRepositoryPort orderRepositoryPort) {
        return new UpdateOrderItemQuantityService(orderRepositoryPort);
    }

    @Bean
    public UpdateOrderUseCase updateOrderUseCase(OrderRepositoryPort orderRepositoryPort,
                                                 CustomerRepositoryPort customerRepositoryPort) {
        return new UpdateOrderService(orderRepositoryPort, customerRepositoryPort);
    }
}
