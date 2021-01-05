package com.wilcotech.kanjihelper.kanjihelper.domain.repositories;

import com.wilcotech.kanjihelper.kanjihelper.domain.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {
    Optional<GroupEntity> findAllByGroupName(String groupName);
    GroupEntity findByGroupName(String groupName);
    boolean existsByGroupName(String groupName);
}
