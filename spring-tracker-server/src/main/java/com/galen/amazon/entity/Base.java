package com.galen.amazon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 *
 * @author zhouyx
 */
@Data
public class Base implements Serializable {

    @JsonIgnore
    private Date createdOn;
    @JsonIgnore
    private Date modifiedOn;


    /**
     * 初始化创建信息
     */
    public void initCreated() {
        this.setCreatedOn(new Date());
        this.initModified();
    }

    /**
     * 初始化修改者信息
     */
    public void initModified() {
        this.setModifiedOn(new Date());
    }

}
