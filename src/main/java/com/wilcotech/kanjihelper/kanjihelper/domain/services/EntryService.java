package com.wilcotech.kanjihelper.kanjihelper.domain.services;

import com.wilcotech.kanjihelper.kanjihelper.domain.entities.EntryEntity;
import com.wilcotech.kanjihelper.kanjihelper.domain.entities.GroupEntity;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Entry;
import com.wilcotech.kanjihelper.kanjihelper.domain.models.Group;
import com.wilcotech.kanjihelper.kanjihelper.domain.repositories.EntryRepository;
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
public class EntryService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private EntryRepository entryRepository;

    private int sn =1;
    public List<Entry> getEntries(Group group){
        GroupEntity groupEntity = groupRepository.findByGroupName(group.getGroupName());
        return entryRepository.findAllByGroupEntity(groupEntity).stream()
                .map(entity -> this.entityToEntry(entity,groupEntity))
                .collect(Collectors.toList());
    }

    public Entry save(Entry entry,Group group){
        GroupEntity groupEntity = groupRepository.findByGroupName(group.getGroupName());
        EntryEntity entryEntity = EntryEntity.builder()
                .groupEntity(groupEntity)
                .kanji(entry.getKanji())
                .english(entry.getEnglish())
                .hiragana(entry.getHiragana())
                .romaji(entry.getRomaji())
                .build();
        return this.entityToEntry(entryRepository.save(entryEntity),groupEntity);
    }

    public Entry entityToEntry(EntryEntity entity,GroupEntity groupEntity){
        return Entry.builder()
                .sn(sn++)
                .date(DateTimeUtil.getDateTime(entity.getCreatedAt()))
                .group(groupEntity.getGroupName())
                .kanji(entity.getKanji() != null? entity.getKanji():"")
                .hiragana(entity.getHiragana() != null?entity.getHiragana():"")
                .romaji(entity.getRomaji()!=null?entity.getRomaji():"")
                .english(entity.getEnglish())
                .build();
    }
}
