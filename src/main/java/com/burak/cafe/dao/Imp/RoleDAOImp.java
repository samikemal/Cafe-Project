package com.burak.cafe.dao.Imp;

import com.burak.cafe.dao.RoleDAO;
import com.burak.cafe.dto.RoleDto;
import com.burak.cafe.entity.RoleEntity;
import com.burak.cafe.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "com.burak.cafe.config")
public class RoleDAOImp implements RoleDAO {

    @Autowired
    RoleRepository roleRepository;


    @Override
    public RoleDto findOne(int userid) {

        RoleEntity roleEntity = roleRepository.findByUserId(userid);
        RoleDto roleDto = new RoleDto();

        roleDto.setId(roleEntity.getId());
        roleDto.setRole(roleEntity.getRole());

        return roleDto;
    }

    @Override
    public void deleteRole(int userid){
        RoleEntity roleEntity = roleRepository.findOne(userid);
        roleRepository.delete(roleEntity);
    }


}
