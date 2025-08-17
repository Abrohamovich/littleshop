package org.abrohamovich.littleshop.adapter.web.offer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.PageResponse;
import org.abrohamovich.littleshop.application.dto.offer.OfferCreateCommand;
import org.abrohamovich.littleshop.application.dto.offer.OfferResponse;
import org.abrohamovich.littleshop.application.dto.offer.OfferUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.offer.CreateOfferUseCase;
import org.abrohamovich.littleshop.application.port.in.offer.DeleteOfferUseCase;
import org.abrohamovich.littleshop.application.port.in.offer.GetOfferUseCase;
import org.abrohamovich.littleshop.application.port.in.offer.UpdateOfferUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/offers")
@RequiredArgsConstructor
public class OfferRestController {
    private final CreateOfferUseCase createOfferUseCase;
    private final GetOfferUseCase getOfferUseCase;
    private final UpdateOfferUseCase updateOfferUseCase;
    private final DeleteOfferUseCase deleteOfferUseCase;
    private final OfferWebMapper offerWebMapper;

    @PostMapping
    public ResponseEntity<OfferWebResponse> create(@Valid @RequestBody OfferCreateWebRequest offerCreateWebRequest) {
        OfferCreateCommand offerCreateCommand = offerWebMapper.toCreateCommand(offerCreateWebRequest);
        OfferResponse offerResponse = createOfferUseCase.save(offerCreateCommand);
        return new ResponseEntity<>(offerWebMapper.toWebResponse(offerResponse), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse<OfferWebResponse>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) Double priceGreaterEqual,
            @RequestParam(required = false) Double priceLessEqual
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OfferResponse> offersPage;

        if (name != null && !name.isBlank()) {
            offersPage = getOfferUseCase.findByNameLike(name, pageable);
        } else if (categoryId != null && categoryId > 0) {
            offersPage = getOfferUseCase.findByCategoryId(categoryId, pageable);
        } else if (supplierId != null && supplierId > 0) {
            offersPage = getOfferUseCase.findBySupplierId(supplierId, pageable);
        } else if (priceGreaterEqual != null && priceLessEqual != null
                && priceGreaterEqual > 0 && priceLessEqual > 0) {
            offersPage = getOfferUseCase.findByPriceIsGreaterThanEqualAndPriceLessThanEqual(priceGreaterEqual, priceLessEqual, pageable);
        } else if (priceGreaterEqual != null && priceGreaterEqual > 0) {
            offersPage = getOfferUseCase.findByPriceIsGreaterThanEqual(priceGreaterEqual, pageable);
        } else if (priceLessEqual != null && priceLessEqual > 0) {
            offersPage = getOfferUseCase.findByPriceIsLessThanEqual(priceLessEqual, pageable);
        } else {
            offersPage = getOfferUseCase.findAll(pageable);
        }

        Page<OfferWebResponse> webResponsePage = offersPage.map(offerWebMapper::toWebResponse);
        return new ResponseEntity<>(PageResponse.fromSpringPage(webResponsePage), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferWebResponse> update(@PathVariable Long id, @Valid @RequestBody OfferUpdateWebRequest offerUpdateWebRequest) {
        OfferUpdateCommand offerUpdateCommand = offerWebMapper.toUpdateCommand(offerUpdateWebRequest);
        OfferResponse offerResponse = updateOfferUseCase.update(id, offerUpdateCommand);
        return new ResponseEntity<>(offerWebMapper.toWebResponse(offerResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteOfferUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
