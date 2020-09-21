package com.olfourd.timesheet.dao;

import com.olfourd.timesheet.dao.model.Task;

import java.util.List;

public interface TaskRepository extends BaseRepository<Task, String> {

    List<Task> findAllByProjectId(String projectId);
}
