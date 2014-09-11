package org.eemtn.tnt.dao.user;

import org.eemtn.tnt.dao.Dao;
import org.eemtn.tnt.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDao extends Dao<User, Long>, UserDetailsService
{

	User findByName(String name);

}