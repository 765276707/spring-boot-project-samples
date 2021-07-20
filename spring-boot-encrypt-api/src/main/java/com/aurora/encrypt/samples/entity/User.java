package com.aurora.encrypt.samples.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class User {

    private Integer id;

    private String username;

    private String password;

}
