package com.wilcotech.kanjihelper.kanjihelper.domain.models;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 21/12/2020.
 */
@Data
@Builder
public class Settings {
    private boolean isFinite;
    private int repeatCount;
    private int waitTime;
    private boolean kanji;
    private boolean romaji;
    private boolean hiragana;
    private boolean english;
    private boolean random;
    private boolean animate;
}
