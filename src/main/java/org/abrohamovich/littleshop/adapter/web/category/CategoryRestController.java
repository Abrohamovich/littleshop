package org.abrohamovich.littleshop.adapter.web.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.PageResponse;
import org.abrohamovich.littleshop.application.dto.category.CategoryCreateCommand;
import org.abrohamovich.littleshop.application.dto.category.CategoryResponse;
import org.abrohamovich.littleshop.application.dto.category.CategoryUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.category.CreateCategoryUseCase;
import org.abrohamovich.littleshop.application.port.in.category.DeleteCategoryUseCase;
import org.abrohamovich.littleshop.application.port.in.category.GetCategoryUseCase;
import org.abrohamovich.littleshop.application.port.in.category.UpdateCategoryUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final CategoryWebMapper categoryWebMapper;

    @PostMapping
    public ResponseEntity<CategoryWebResponse> create(@Valid @RequestBody CategoryCreateWebRequest categoryCreateWebRequest) {
        CategoryCreateCommand categoryCreateCommand = categoryWebMapper.toCreateCommand(categoryCreateWebRequest);
        CategoryResponse categoryResponse = createCategoryUseCase.save(categoryCreateCommand);
        return new ResponseEntity<>(categoryWebMapper.toWebResponse(categoryResponse), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryWebResponse> findById(@PathVariable Long id) {
        CategoryResponse categoryResponse = getCategoryUseCase.findById(id);
        return new ResponseEntity<>(categoryWebMapper.toWebResponse(categoryResponse), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryWebResponse>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryResponse> categoriesPage;

        if (name != null && !name.isBlank()) {
            categoriesPage = getCategoryUseCase.findByNameLike(name, pageable);
        } else if (description != null && !description.isBlank()) {
            categoriesPage = getCategoryUseCase.findByDescriptionLike(description, pageable);
        } else {
            categoriesPage = getCategoryUseCase.findAll(pageable);
        }

        Page<CategoryWebResponse> webResponsePage = categoriesPage.map(categoryWebMapper::toWebResponse);
        return ResponseEntity.ok(PageResponse.fromSpringPage(webResponsePage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryWebResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateWebRequest request) {
        CategoryUpdateCommand command = categoryWebMapper.toUpdateCommand(request);
        CategoryResponse response = updateCategoryUseCase.update(id, command);
        return new ResponseEntity<>(categoryWebMapper.toWebResponse(response), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCategoryUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
