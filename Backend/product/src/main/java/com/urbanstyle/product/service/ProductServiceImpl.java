package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.urbanstyle.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	private static final String ACTIVE ="ACTIVE";
	private ProductRepository productRepository;
	
	@Override
	public List<Product> getAllMainProductsOfUser(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		return productRepository.findByStatusAndVariantFalse(ACTIVE,pagable);
	}

	@Override
	public List<Product> getAllVariantProductsOfProductOfUser(long userId, long productId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());

		return productRepository.findByStatusAndParentProductId(ACTIVE,pagable);
	}

	@Override
	public List<Product> getAllProductOfCategory(long categoryId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productRepository.findByStatusAndCategoryCategoryId(ACTIVE,categoryId,pagable);
	}

}
