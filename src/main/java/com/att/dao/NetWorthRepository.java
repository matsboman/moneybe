package com.att.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.att.model.NetWorth;

public interface NetWorthRepository extends CrudRepository<NetWorth, Integer> {

	Optional<NetWorth> findByNetWorthID(Integer id);
}
