package com.olfourd.timesheet.dao;

import com.olfourd.timesheet.exception.EntityNotFoundException;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BaseRepository<T, ID> extends MongoRepository<T, ID> {

    default T findByIdMandatory(ID id) {
        return findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("%s. Not founded entity by ID: %s", getClass().getName(), id)));
    }
}
