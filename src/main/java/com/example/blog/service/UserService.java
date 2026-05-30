package com.example.blog.service;

import com.example.blog.dao.entities.Comment;
import com.example.blog.dao.entities.Post;
import com.example.blog.dao.entities.User;
import com.example.blog.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserManager, UserDetailsService {

    private final UserRepository userRepository;
    private final PostService postService;
    private final ProfileService profileService;

    public UserService(UserRepository userRepository, PostService postService, ProfileService profileService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.profileService = profileService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @Override
    public User createUser(User user) {

        System.out.print(user.getUsername());

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updated) {
        User existing = getUserById(id);

        existing.setProfile(updated.getProfile());
        existing.setUsername(updated.getUsername());
        existing.setEmail(updated.getEmail());
        existing.setPassword(updated.getPassword());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        for (Post post : getUserById(id).getPosts()){
            postService.deletePost(post.getId());
        }
        profileService.deleteProfile(getUserById(id).getProfile().getId());
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Reuse your existing logic to find the user
        User user = this.userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Since your User class implements UserDetails, return it directly
        return user;
    }
}
