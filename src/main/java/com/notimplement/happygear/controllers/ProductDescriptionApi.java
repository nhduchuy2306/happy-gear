package com.notimplement.happygear.controllers;

import com.notimplement.happygear.model.dto.ProductDescriptionDto;
import com.notimplement.happygear.service.ProductDescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/descriptions")
@RequiredArgsConstructor
public class ProductDescriptionApi {

	private final ProductDescriptionService productDescriptionService;

	@PostMapping("")
	public ResponseEntity<?> createProductDescription(@Valid @RequestBody ProductDescriptionDto ProductDescription){
		return ResponseEntity.ok(productDescriptionService.create(ProductDescription));
	}
}
