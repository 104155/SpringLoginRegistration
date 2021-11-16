package com.companyName.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.companyName.model.User;

//@Repository("userRepository")//String Mapping für variablenbezeichner wenn UserRepo als Eigenschaft benützt wird
@Repository//ohne klammern da variablenname falsch geschrieben werden kann
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmailIgnoreCase(String email);
}