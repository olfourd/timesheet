package com.olfourd.timesheet.dao;

import com.olfourd.timesheet.dao.model.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<Track, String> {
}
