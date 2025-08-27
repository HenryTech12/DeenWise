package org.deenwise.app.apis.repository;

import org.deenwise.app.apis.model.AssessmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<AssessmentModel,Long> {
}
