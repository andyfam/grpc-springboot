package com.yufeng.springboot.service;

import com.yufeng.springboot.model.Group;
import com.yufeng.springboot.model.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Collection<Group> groups() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroup(Long id) {
        return groupRepository.findById(id);
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}
