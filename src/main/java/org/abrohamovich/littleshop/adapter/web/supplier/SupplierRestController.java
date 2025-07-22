package org.abrohamovich.littleshop.adapter.web.supplier;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.PageResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierCreateCommand;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierResponse;
import org.abrohamovich.littleshop.application.dto.supplier.SupplierUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.supplier.CreateSupplierUseCase;
import org.abrohamovich.littleshop.application.port.in.supplier.DeleteSupplierUseCase;
import org.abrohamovich.littleshop.application.port.in.supplier.GetSupplierUseCase;
import org.abrohamovich.littleshop.application.port.in.supplier.UpdateSupplierUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierRestController {
    private final CreateSupplierUseCase createSupplierUseCase;
    private final GetSupplierUseCase getSupplierUseCase;
    private final UpdateSupplierUseCase updateSupplierUseCase;
    private final DeleteSupplierUseCase deleteSupplierUseCase;
    private final SupplierWebMapper supplierWebMapper;

    @PostMapping
    public ResponseEntity<SupplierWebResponse> create(@Valid @RequestBody SupplierCreateWebRequest supplierCreateWebRequest) {
        SupplierCreateCommand supplierCreateCommand = supplierWebMapper.toCreateCommand(supplierCreateWebRequest);
        SupplierResponse supplierResponse = createSupplierUseCase.save(supplierCreateCommand);
        return new ResponseEntity<>(supplierWebMapper.toWebResponse(supplierResponse), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierWebResponse> findById(@PathVariable Long id) {
        SupplierResponse supplierResponse = getSupplierUseCase.findById(id);
        return new ResponseEntity<>(supplierWebMapper.toWebResponse(supplierResponse), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<SupplierWebResponse>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SupplierResponse> suppliersPage;

        if (name != null && !name.isBlank()) {
            suppliersPage = getSupplierUseCase.findByNameLike(name, pageable);
        } else if (email != null && !email.isBlank()) {
            suppliersPage = getSupplierUseCase.findByEmailLike(email, pageable);
        } else if (phone != null && !phone.isBlank()) {
            suppliersPage = getSupplierUseCase.findByPhoneLike(phone, pageable);
        } else {
            suppliersPage = getSupplierUseCase.findAll(pageable);
        }

        Page<SupplierWebResponse> webResponsePage = suppliersPage.map(supplierWebMapper::toWebResponse);
        return new ResponseEntity<>(PageResponse.fromSpringPage(webResponsePage), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierWebResponse> update(@PathVariable Long id, @Valid @RequestBody SupplierUpdateWebRequest supplierUpdateWebRequest) {
        SupplierUpdateCommand supplierUpdateCommand = supplierWebMapper.toUpdateCommand(supplierUpdateWebRequest);
        SupplierResponse supplierResponse = updateSupplierUseCase.update(id, supplierUpdateCommand);
        return new ResponseEntity<>(supplierWebMapper.toWebResponse(supplierResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        deleteSupplierUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
