package org.fasttrackit.onlineshopapi.persistance;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;




public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {






}
