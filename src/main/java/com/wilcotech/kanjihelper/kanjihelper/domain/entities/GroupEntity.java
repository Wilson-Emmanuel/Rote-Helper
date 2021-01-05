package com.wilcotech.kanjihelper.kanjihelper.domain.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Wilson
 * on Thu, 10/12/2020.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="groups")
public class GroupEntity extends AbstractBaseEntity<Long>{
    @Column(name = "group_name",unique = true, nullable = false)
    private String groupName;

}
