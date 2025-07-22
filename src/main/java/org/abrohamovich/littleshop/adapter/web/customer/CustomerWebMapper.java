package org.abrohamovich.littleshop.adapter.web.customer;

import org.abrohamovich.littleshop.application.dto.customer.CustomerCreateCommand;
import org.abrohamovich.littleshop.application.dto.customer.CustomerResponse;
import org.abrohamovich.littleshop.application.dto.customer.CustomerUpdateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerWebMapper {
    CustomerCreateCommand toCreateCommand(CustomerCreateWebRequest request);
    CustomerUpdateCommand toUpdateCommand(CustomerUpdateWebRequest request);
    CustomerWebResponse toWebResponse(CustomerResponse response);
}
