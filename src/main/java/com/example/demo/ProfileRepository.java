package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

	Optional<Profile> findByName(String name);
}
