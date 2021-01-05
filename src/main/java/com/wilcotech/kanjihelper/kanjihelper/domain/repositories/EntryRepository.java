package com.wilcotech.kanjihelper.kanjihelper.domain.repositories;

import com.wilcotech.kanjihelper.kanjihelper.domain.entities.EntryEntity;
import com.wilcotech.kanjihelper.kanjihelper.domain.entities.GroupEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
public interface EntryRepository extends JpaRepository<EntryEntity,Long> {

    //@Query("select * from Entry a where a.group = :group")
    List<EntryEntity> findAllByGroupEntity(GroupEntity groupEntity, Pageable pageable);
    List<EntryEntity> findAllByGroupEntity(GroupEntity groupEntity);
}
