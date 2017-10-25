package com.qf.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by ios on 17/10/17.
 */
public class User {

    private Integer id;
    @Length(min = 5,max = 10)
    private String name;
//    @Length(min=5 ,max = 20)
    @NotBlank
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
