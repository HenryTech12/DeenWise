package org.deenwise.app.apis.repository;

import org.deenwise.app.apis.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {

    Optional<UserModel> findByEmail(String email);
}
