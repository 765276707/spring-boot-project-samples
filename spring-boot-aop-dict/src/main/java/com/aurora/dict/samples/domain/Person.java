package com.aurora.dict.samples.domain;

import com.aurora.dict.core.annotation.DictEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DictEntity
public class Person {

    private Integer id;

    private String personName;

    private User user;

}
