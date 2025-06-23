package com.deenwise.demo.repo;

import com.deenwise.demo.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminModel,Long>
{
}
