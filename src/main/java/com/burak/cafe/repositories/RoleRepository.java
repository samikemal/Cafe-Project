package com.burak.cafe.repositories;

import com.burak.cafe.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);

    @Query("from RoleEntity p where p.id=:user_id")
    RoleEntity findByUserId(@Param("user_id") int userid);

    @Query("from RoleEntity  p where p.id=:id")
    RoleEntity findOne(@Param("id") int userid);
}
