package com.qingtai.vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

	// UserDetails findOne(String username);

}
