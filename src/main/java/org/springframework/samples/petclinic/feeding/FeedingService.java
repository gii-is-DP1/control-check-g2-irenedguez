package org.springframework.samples.petclinic.feeding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class FeedingService {
    private FeedingRepository feedingRepository;

    @Autowired
    public FeedingService(FeedingRepository feedingRepository) {
        this.feedingRepository = feedingRepository;
    }

    public List<Feeding> getAll() {
        return this.feedingRepository.findAll();
    }

    public List<FeedingType> findAllFeedingTypes() {
        return this.feedingRepository.findAllFeedingTypes();
    }

    public List<FeedingType> getAllFeedingTypes() {
        return null;
    }

    public FeedingType getFeedingType(String typeName) {
        return this.feedingRepository.findFeedingTypeByName(typeName);
    }

    public Feeding save(Feeding p) throws UnfeasibleFeedingException {
        return this.feedingRepository.save(p);
    }

}
