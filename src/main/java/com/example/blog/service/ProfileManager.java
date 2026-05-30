package com.example.blog.service;

import com.example.blog.dao.entities.Profile;

import java.util.List;

public interface ProfileManager {
    List<Profile> getAllProfiles();
    Profile getProfileByID(Long id);
    Profile createProfile(Profile profile);
    Profile updateProfile(Long id, Profile profile);
    void deleteProfile(Long id);
}
