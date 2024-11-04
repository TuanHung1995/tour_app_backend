package com.springboot.mvc.tour_app.controller;

import com.springboot.mvc.tour_app.entity.User;
import com.springboot.mvc.tour_app.payload.FollowTourDto;
import com.springboot.mvc.tour_app.payload.FollowTourResponse;
import com.springboot.mvc.tour_app.repository.UserRepository;
import com.springboot.mvc.tour_app.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowTourController {

    private final FollowService followService;
    private final UserRepository userRepository;

    public FollowTourController(FollowService followService, UserRepository userRepository) {
        this.followService = followService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<FollowTourResponse> getFollowedTours(){
        return ResponseEntity.ok(followService.getFollowedTours(getCurrentUser().getId()));
    }

    @PostMapping
    public ResponseEntity<FollowTourResponse> followTour(@RequestBody FollowTourDto followTourDto){
        return ResponseEntity.ok(followService.followTour(getCurrentUser().getId(), followTourDto));
    }

    @DeleteMapping("/{followTourId}")
    public ResponseEntity<FollowTourResponse> unfollowTour(@PathVariable("followTourId") Long followTourId){
        return ResponseEntity.ok(followService.unfollowTour(getCurrentUser().getId(), followTourId));
    }

    // Lấy thông tin người dùng hiện tại
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentUsername = userDetails.getUsername();
            // Lấy thông tin người dùng từ database
            return userRepository.findByEmail(currentUsername);
        }

        // Return null if no user is logged in
        return null;
    }

}
