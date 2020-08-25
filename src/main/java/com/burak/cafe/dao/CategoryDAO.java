package com.burak.cafe.dao;

import com.burak.cafe.dto.CategoryDto;

import java.util.List;

public interface CategoryDAO {

    List<CategoryDto> findAll();

}
