package org.deenwise.app.apis.repository;

import org.deenwise.app.apis.model.LectureModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<LectureModel,Long> {


}
