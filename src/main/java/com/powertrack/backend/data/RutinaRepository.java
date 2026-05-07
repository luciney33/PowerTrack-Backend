package com.powertrack.backend.data;

import com.powertrack.backend.data.entity.RutinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutinaRepository extends JpaRepository<RutinaEntity, Long> {

    Optional<RutinaEntity> findFirstByTipo(Integer tipo);


}

