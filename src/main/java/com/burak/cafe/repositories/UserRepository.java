package com.burak.cafe.repositories;

import com.burak.cafe.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);

    @Query("from UserEntity p where p.id=:id")
    UserEntity findOne(@Param("id") int userid);


    //UserEntity findByName(String firstName);
}



