package com.twitter.apis.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.twitter.apis.DTO.CustomTweetDTO;
import com.twitter.apis.DTO.TweetDTO;
import com.twitter.apis.DTO.UserProfileDTO;
import com.twitter.apis.components.TweetDTOToCustomTweetDTOConverter;
import com.twitter.apis.exceptions.ProfileNotFoundException;
import com.twitter.apis.model.UserEntity;
import com.twitter.apis.repository.TweetRepository;
import com.twitter.apis.repository.UserRepository;

@Service
public class UserProfileService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final TweetDTOToCustomTweetDTOConverter tweetDTOToCustomTweetDTOConverter;

    @Autowired
    public UserProfileService(UserRepository userRepository, TweetRepository tweetRepository,
            TweetDTOToCustomTweetDTOConverter tweetDTOToCustomTweetDTOConverter) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.tweetDTOToCustomTweetDTOConverter = tweetDTOToCustomTweetDTOConverter;
    }

    public ResponseEntity<UserProfileDTO> getUserProfile(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            UserProfileDTO userProfileDTO = new UserProfileDTO(user.getUsername(), user.getProfilePicture(),
                    user.getBio(), user.getNumberOfFollowers(), user.getNumberOfTweets());

            List<TweetDTO> tweets = tweetRepository.getAllTweetsByUserIdList(id);
            List<TweetDTO> filteredTweets = tweets.stream()
                    .filter(tweet -> tweet.getText() != null && tweet.getTimestamp() != null)
                    .collect(Collectors.toList());

            List<CustomTweetDTO> customTweets = filteredTweets.stream()
                    .map(tweetDTOToCustomTweetDTOConverter::convert)
                    .collect(Collectors.toList());

            userProfileDTO.setFilteredTweets(customTweets);

            return ResponseEntity.ok(userProfileDTO);
        } else {
            throw new ProfileNotFoundException("User not found");
        }
    }

    public ResponseEntity<UserProfileDTO> updateUserProfile(Long id, UserProfileDTO userProfileDTO) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isPresent()) {
            UserEntity existingUser = userEntityOptional.get();

            if (userProfileDTO.getUsername() != null) {
                existingUser.setUsername(userProfileDTO.getUsername());
            }
            if (userProfileDTO.getProfilePicture() != null) {
                existingUser.setProfilePicture(userProfileDTO.getProfilePicture());
            }
            if (userProfileDTO.getBio() != null) {
                existingUser.setBio(userProfileDTO.getBio());
            }

            UserEntity updatedUser = userRepository.save(existingUser);

            UserProfileDTO updatedUserProfileDTO = new UserProfileDTO();
            updatedUserProfileDTO.setUsername(updatedUser.getUsername());
            updatedUserProfileDTO.setProfilePicture(updatedUser.getProfilePicture());
            updatedUserProfileDTO.setBio(updatedUser.getBio());
            updatedUserProfileDTO.setNumberOfFollowers(updatedUser.getNumberOfFollowers());
            updatedUserProfileDTO.setNumberOfTweets(updatedUser.getNumberOfTweets());

            return ResponseEntity.ok(updatedUserProfileDTO);
        } else {
            throw new ProfileNotFoundException("User not found");
        }
    }

}
