package com.aurora.logger.samples.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String username;

    private Integer gender;

    private String genderText;

    private Integer status;

    private String statusText;

}
