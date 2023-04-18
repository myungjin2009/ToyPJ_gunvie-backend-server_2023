package com.gunbro.gunvie.repository;

import com.gunbro.gunvie.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByLoginId(String loginId);

    User findByNameAndEmail(String name, String email);

    User findByNameAndLoginIdAndEmail(String name, String loginId, String email);
}
