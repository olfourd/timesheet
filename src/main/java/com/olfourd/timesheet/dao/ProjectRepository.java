package com.olfourd.timesheet.dao;

import com.olfourd.timesheet.dao.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
