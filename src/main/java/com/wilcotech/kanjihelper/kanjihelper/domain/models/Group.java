package com.wilcotech.kanjihelper.kanjihelper.domain.models;

import lombok.*;

/**
 * Created by Wilson
 * on Sun, 20/12/2020.
 */
@Data
@Builder
public class Group {
    private String groupName;
    private String date;

    @Override
    public String toString() {
        return this.groupName;
    }
}
