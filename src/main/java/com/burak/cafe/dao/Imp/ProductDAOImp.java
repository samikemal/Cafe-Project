package com.burak.cafe.dao.Imp;

import com.burak.cafe.dto.ProductDto;
import com.burak.cafe.entity.ProductEntity;
import com.burak.cafe.repositories.CategoryRepository;
import com.burak.cafe.repositories.ProductRepository;
import com.burak.cafe.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@ComponentScan(basePackages = "com.burak.cafe.repositories")
public class ProductDAOImp implements ProductDAO {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> findAll() {

        Iterator<ProductEntity> products = productRepository.findAll().iterator();
        List<ProductDto> productList = new ArrayList<ProductDto>();

        while (products.hasNext()){

            ProductEntity productEntity = products.next();
            ProductDto productDto = new ProductDto();

            productDto.setId(productEntity.getId());
            productDto.setProductName(productEntity.getProductName());
            productDto.setProductPrice(productEntity.getProductPrice());
            productDto.setProductCategory(productEntity.getProductCategory());

            productList.add(productDto);
        }

        return productList;
    }

    @Override
    public ProductDto findByID(int id) {
        ProductEntity productEntity = productRepository.findByID(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(productEntity.getId());
        productDto.setProductName(productEntity.getProductName());
        productDto.setProductPrice(productEntity.getProductPrice());
        productDto.setProductCategory(productEntity.getProductCategory());

        return productDto;
    }

    @Override
    public void save(ProductDto productDto) {
        ProductEntity productEntity = productRepository.findByID(productDto.getId());
        productEntity.setProductCategoryName(productDto.getProductCategoryName());
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductDto> listByCategory(int categoryid) {
        //Iterator, bir toplama, alma veya çıkarma öğeleri arasında gezinmenizi sağlar.
        // ListIterator, listenin iki yönlü geçişine ve öğelerin değiştirilmesine izin vermek için Iteratoru genişletir.
        Iterator<ProductEntity> productEntityList = productRepository.listByCategory(categoryid).iterator();
        List<ProductDto> productList = new ArrayList<ProductDto>();

        while (productEntityList.hasNext()){

            ProductEntity entity = productEntityList.next();
            ProductDto productDto = new ProductDto();

            productDto.setId(entity.getId());
            productDto.setProductCategory(entity.getProductCategory());
            productDto.setProductPrice(entity.getProductPrice());
            productDto.setProductName(entity.getProductName());

            productList.add(productDto);
        }

        return productList;
    }

}
