package com.burak.cafe.repositories;

import com.burak.cafe.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository <ProductEntity, Long>  {

    @Query("from ProductEntity p where p.productCategory=:productCategory")
    List<ProductEntity> listByCategory(@Param("productCategory") int categoryId);

    @Query("from ProductEntity p where p.id=:productID")
    ProductEntity findByID(@Param("productID") int id);
}
