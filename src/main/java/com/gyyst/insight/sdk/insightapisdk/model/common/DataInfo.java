package com.gyyst.insight.sdk.insightapisdk.model.common;

import com.gyyst.insight.sdk.insightapisdk.model.enums.DataType;
import lombok.Data;

import java.util.List;

/**
 * @author gyyst
 * @Description API模型定义
 * @Create by 2023/9/13 21:42
 */
@Data
public class DataInfo<T> {

    protected String name;

    protected String description;

    protected DataType dataType;

    protected Boolean isNeed;

    protected List<T> children;

}