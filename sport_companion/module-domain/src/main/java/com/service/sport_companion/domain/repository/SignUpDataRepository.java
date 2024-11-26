package com.service.sport_companion.domain.repository;

import com.service.sport_companion.domain.entity.SignUpDataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpDataRepository extends CrudRepository<SignUpDataEntity, String> {

}
