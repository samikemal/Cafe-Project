package com.burak.cafe.dao;

import com.burak.cafe.dto.RoleDto;

public interface RoleDAO {

    void deleteRole(int userid);
    RoleDto findOne(int userid);

}
