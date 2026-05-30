package com.example.blog.dao.repositories;

import com.example.blog.dao.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile, Long> {
    Profile findProfilesById(Long id);
}
