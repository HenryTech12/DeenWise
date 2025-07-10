package com.deenwise.demo.repo;

import com.deenwise.demo.model.UserModel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long>
{
    @Cacheable("fetchUserData")
    Optional<UserModel> findByEmail(String email);
}