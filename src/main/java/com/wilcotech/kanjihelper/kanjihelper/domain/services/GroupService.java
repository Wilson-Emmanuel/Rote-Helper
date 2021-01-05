package com.wilcotech.kanjihelper.kanjihelper.domain.services;

import com.wilcotech.kanjihelper.kanjihelper.domain.entities.GroupEntity;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.domain.repositories.GroupRepository;
import com.wilcotech.kanjihelper.kanjihelper.utilities.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    public List<Group> getGroups(){
        return groupRepository.findAll().stream()
                .map(this::entityToGroup)
                .collect(Collectors.toList());
    }
    public boolean existsByGroupName(String groupName){
        return groupRepository.existsByGroupName(groupName);
    }
    public Group save(Group group){
        GroupEntity groupEntity = GroupEntity.builder()
                .groupName(group.getGroupName())
                .build();
        return this.entityToGroup(groupRepository.save(groupEntity));
    }

    public Group entityToGroup(GroupEntity entity){
        return Group.builder()
                .date(DateTimeUtil.getDateTime(entity.getCreatedAt()))
                .groupName(entity.getGroupName())
                .build();
    }
}
