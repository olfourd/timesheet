package com.olfourd.timesheet.dao;

import com.olfourd.timesheet.dao.model.Assignee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssigneeRepository extends MongoRepository<Assignee, String> {
}
