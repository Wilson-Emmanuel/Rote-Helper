package com.wilcotech.kanjihelper.kanjihelper.domain.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Wilson
 * on Wed, 09/12/2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "entries")
public class EntryEntity extends AbstractBaseEntity<Long> {



    @Column(name = "kanji")
    private String kanji;

    @Column(name = "hiragana")
    private String hiragana;

    @Column(name = "romaji")
    private String romaji;

    @Column(name = "english",nullable = false)
    private String english;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private GroupEntity groupEntity;

}
