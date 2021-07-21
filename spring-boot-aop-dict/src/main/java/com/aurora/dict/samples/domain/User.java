package com.aurora.dict.samples.domain;

import com.aurora.dict.core.annotation.DictEntity;
import com.aurora.dict.core.annotation.DictField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DictEntity(analysisProperty = true)
public class User {

    private Integer id;

    private String username;

    private Integer gender;

    @DictField(keyFieldName = "gender", code = "user_gender", defaultValue = "保密")
    private String genderText;

    private Integer status;

    @DictField(keyFieldName = "status", code = "user_status", defaultValue = "未知状态")
    private String statusText;

}
