package org.abrohamovich.littleshop.adapter.web.offer;

import org.abrohamovich.littleshop.application.dto.offer.OfferCreateCommand;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.dto.offer.OfferUpdateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfferWebMapper {
    OfferCreateCommand toCreateCommand(OfferCreateWebRequest request);
    OfferUpdateCommand toUpdateCommand(OfferUpdateWebRequest request);
    OfferWebResponse toWebResponse(OfferResponse response);
}
