package com.tenpo.challenge.repository;

import com.tenpo.challenge.model.Percentage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PercentageRepository extends JpaRepository<Percentage,Long> {

    @Query(value = "select p from Percentage as p where p.startDate IN (SELECT max(startDate) FROM Percentage )")
    Optional<Percentage> findDistinctByStartDate();

    Optional<Percentage> getPercentageById(Integer Id);

}
