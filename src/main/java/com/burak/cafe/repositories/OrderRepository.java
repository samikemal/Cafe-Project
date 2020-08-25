package com.burak.cafe.repositories;

import com.burak.cafe.entity.OrderEntity;
import com.burak.cafe.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity,Integer> {

    @Query("from OrderEntity o where o.id=:orderID")
    OrderEntity findByID(@Param("orderID") int id);

    @Query("from OrderEntity o where o.userID=:userID")
    List<OrderEntity> findByUser(@Param("userID") int id);


/*
    @Query("from UserEnitiy o where o.name=:firstName")
    UserEntity findByName(@Param("name") String name);
  */


}
