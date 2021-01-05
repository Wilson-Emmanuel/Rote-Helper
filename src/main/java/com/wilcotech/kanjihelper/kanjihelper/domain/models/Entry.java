package com.wilcotech.kanjihelper.kanjihelper.domain.models;

import lombok.*;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
@Data
@Builder
public class Entry {
    private int sn;
    private String kanji;
    private String hiragana;
    private String romaji;
    private String english;
    private int number;
    private String date;
    private String group;
}
