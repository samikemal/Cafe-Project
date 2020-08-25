package com.burak.cafe.dao;

import com.burak.cafe.dto.ProductDto;

import java.util.List;

public interface ProductDAO {

    List<ProductDto> findAll();
    ProductDto findByID(int id);
    void save(ProductDto product);
    List<ProductDto> listByCategory(int categoryid);
}
