package com.tenpo.challenge.repository;

import com.tenpo.challenge.model.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationRepository extends CrudRepository<Operation,Long> {

    Optional<Operation> findById(Long id);

    Page<Operation> findAll(Pageable page);

}
