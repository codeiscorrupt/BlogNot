package com.example.blog.service;

import com.example.blog.dao.entities.Profile;
import com.example.blog.dao.repositories.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService implements ProfileManager{

    private final ProfileRepo profileRepo;

    public ProfileService(ProfileRepo profileRepo){
        this.profileRepo = profileRepo;
    }

    @Override
    public List<Profile> getAllProfiles(){
        return profileRepo.findAll();
    }

    @Override
    public Profile getProfileByID(Long id){
        return profileRepo.findProfilesById(id);
    }

    @Override
    public Profile createProfile(Profile profile){
        return profileRepo.save(profile);
    }

    @Override
    public Profile updateProfile(Long id, Profile profile){
        Profile perfile = getProfileByID(id);
        perfile.setUser(profile.getUser());
        perfile.setAvatar(profile.getAvatar());
        perfile.setBio(profile.getBio());
        return profileRepo.save(perfile);
    }

    @Override
    public void deleteProfile(Long id){
      profileRepo.deleteById(id);
    }
}
