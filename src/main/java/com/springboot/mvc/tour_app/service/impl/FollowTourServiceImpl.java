package com.springboot.mvc.tour_app.service.impl;

import com.springboot.mvc.tour_app.entity.FollowTour;
import com.springboot.mvc.tour_app.entity.Tour;
import com.springboot.mvc.tour_app.entity.User;
import com.springboot.mvc.tour_app.exception.ResourceNotFoundException;
import com.springboot.mvc.tour_app.payload.FollowTourDto;
import com.springboot.mvc.tour_app.payload.FollowTourResponse;
import com.springboot.mvc.tour_app.payload.TourDto;
import com.springboot.mvc.tour_app.payload.UserDto;
import com.springboot.mvc.tour_app.repository.FollowTourRepository;
import com.springboot.mvc.tour_app.repository.TourRepository;
import com.springboot.mvc.tour_app.repository.UserRepository;
import com.springboot.mvc.tour_app.service.FollowService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FollowTourServiceImpl implements FollowService {

    private final FollowTourRepository followTourRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public FollowTourServiceImpl(FollowTourRepository followTourRepository, TourRepository tourRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.followTourRepository = followTourRepository;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FollowTourResponse followTour(Long userId, FollowTourDto followTourDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Tour tour = tourRepository.findById(followTourDto.getTourId()).orElseThrow(() -> new ResourceNotFoundException("Tour", "id", followTourDto.getTourId()));


        followTourDto.setUser(convertUserToUserDto(user));
        followTourDto.setTour(convertTourToTourDto(tour));

        FollowTour followTour = new FollowTour();
        followTour.setTour(tour);
        followTour.setUser(user);
        followTour.setAdded_at(new Date());

        followTourRepository.save(followTour);

        List<FollowTour> followTours = followTourRepository.findByUserId(userId);

        return getFollowTourResponse(user, followTours);
    }

    @Override
    public FollowTourResponse unfollowTour(Long userId, Long followTourId) {
        FollowTour followTour = followTourRepository.findById(followTourId).orElseThrow(() -> new ResourceNotFoundException("FollowTour", "id", followTourId));
        followTourRepository.delete(followTour);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<FollowTour> followTours = followTourRepository.findByUserId(userId);

        return getFollowTourResponse(user, followTours);
    }

    @Override
    public FollowTourResponse getFollowedTours(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<FollowTour> followTours = followTourRepository.findByUserId(userId);

        return getFollowTourResponse(user, followTours);
    }

    private FollowTourResponse getFollowTourResponse(User user, List<FollowTour> followTours) {
        FollowTourResponse followTourResponse = new FollowTourResponse();
        followTourResponse.setUser(convertUserToUserDto(user));
        if (followTours.isEmpty()) {
            return followTourResponse;
        }
        List<TourDto> tourDtos = (followTours.stream().map(this::getTourDtoFromFollowTour).toList());
        followTourResponse.setTours(tourDtos);
        return followTourResponse;
    }

    private TourDto getTourDtoFromFollowTour(FollowTour followTour) {
        return modelMapper.map(followTour.getTour(), TourDto.class);
    }

    // Convert the FollowTour entity to FollowTourResponse DTO
    private FollowTourResponse convertFollowTourToFollowTourResponse(FollowTour followTour) {
        return modelMapper.map(followTour, FollowTourResponse.class);
    }

    // Convert the FollowTourResponse DTO to FollowTour entity
    private FollowTour convertFollowTourResponseToFollowTour(FollowTourResponse followTourResponse) {
        return modelMapper.map(followTourResponse, FollowTour.class);
    }

    // Convert the FollowTour entity to FollowTourDto DTO
    private FollowTourDto convertFollowTourToFollowTourDto(FollowTour followTour) {
        return modelMapper.map(followTour, FollowTourDto.class);
    }

    // Convert the FollowTourDto DTO to FollowTour entity
    private FollowTour convertFollowTourDtoToFollowTour(FollowTourDto followTourDto) {
        // Khởi tạo ModelMapper
        ModelMapper modelMapper = new ModelMapper();

        // Định nghĩa ánh xạ tùy chỉnh để tránh xung đột
        modelMapper.typeMap(FollowTourDto.class, FollowTour.class).addMappings(mapper -> {
            // Bỏ qua việc ánh xạ trực tiếp từ FollowTourDto đến FollowTour.tour
            mapper.skip(FollowTour::setTour);
        });

        // Ánh xạ các thuộc tính không gây xung đột
        FollowTour followTour = modelMapper.map(followTourDto, FollowTour.class);

        // Tự động gán đối tượng Tour dựa trên tourId
        Tour tour = tourRepository.findById(followTourDto.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour", "id", followTourDto.getTourId()));
        followTour.setTour(tour);

        return followTour;
    }


    // Convert User entity to UserDto DTO
    private UserDto convertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    // Convert UserDto DTO to User entity
    private User convertUserDtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    // Convert Tour to TourDto
    private TourDto convertTourToTourDto(Tour post) {
        return modelMapper.map(post, TourDto.class);
    }

    // Convert TourDto to Tour
    private Tour convertTourDtoToTour(TourDto postDto) {
        return modelMapper.map(postDto, Tour.class);
    }
}
