package org.nikita111100.onlinetesting.services;

import org.nikita111100.onlinetesting.model.persistent.UserReviews;
import org.nikita111100.onlinetesting.repositories.UserReviewsRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserReviewsService {
    private final UserReviewsRepo userReviewsRepo;

    public UserReviewsService(UserReviewsRepo userReviewsRepo) {
        this.userReviewsRepo = userReviewsRepo;
    }

    @Transactional
    public void save(UserReviews userReview) {
        try {
            userReviewsRepo.save(userReview);
        } catch (Exception e) {
            throw e;
        }
    }
}
