package com.olfourd.timesheet.dao;

import com.olfourd.timesheet.dao.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
}
