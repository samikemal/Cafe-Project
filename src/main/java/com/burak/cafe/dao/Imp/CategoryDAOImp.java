package com.burak.cafe.dao.Imp;

import com.burak.cafe.dto.CategoryDto;
import com.burak.cafe.entity.CategoryEntity;
import com.burak.cafe.repositories.CategoryRepository;
import com.burak.cafe.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Component
public class CategoryDAOImp implements CategoryDAO {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        //Iterator, bir toplama, alma veya çıkarma öğeleri arasında gezinmenizi sağlar.
        // ListIterator, listenin iki yönlü geçişine ve öğelerin değiştirilmesine izin vermek için Iteratoru genişletir.
        Iterator<CategoryEntity> categories = categoryRepository.findAll().iterator();
        List<CategoryDto> categoryList = new ArrayList<CategoryDto>();

        while(categories.hasNext()){//Döngü hasNext () öğesi true döndürdüğü sürece yinelemeli.

            CategoryEntity categoryEntity = categories.next();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryEntity.getId());
            categoryDto.setCategory(categoryEntity.getCategory());
            categoryList.add(categoryDto);
        }

        return categoryList;
    }
}
