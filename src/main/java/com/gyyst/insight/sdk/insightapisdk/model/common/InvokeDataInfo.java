package com.gyyst.insight.sdk.insightapisdk.model.common;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.gyyst.insight.sdk.insightapisdk.model.enums.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gyyst
 * @Description API调用模型定义
 * @Create by 2023/9/13 21:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InvokeDataInfo extends DataInfo<InvokeDataInfo> {

    private Object value;

    public static void main(String[] args) {
        String requestBody = """
                {
                    "name": "echoBody",
                    "description": "放在requestBody中",
                    "dataType": "OBJECT",
                    "isNeed": true,
                    "value": null,
                    "children": [
                        {
                            "name": "echo",
                            "description": "echo",
                            "dataType": "ARRAY",
                            "isNeed": true,
                            "value": [
                                {
                                    "name": "echo3",
                                    "description": "echo",
                                    "dataType": "STRING",
                                    "isNeed": true,
                                    "value": "1",
                                    "children": null
                                },
                                {
                                    "name": "echo4",
                                    "description": "echo",
                                    "dataType": "STRING",
                                    "isNeed": true,
                                    "value": "2",
                                    "children": null
                                }
                            ],
                            "children": null
                        },
                        {
                            "name": "echo2",
                            "description": "echo",
                            "dataType": "ARRAY",
                            "isNeed": true,
                            "value": [
                                {
                                    "name": "echo3",
                                    "description": "echo",
                                    "dataType": "STRING",
                                    "isNeed": true,
                                    "value": "3",
                                    "children": null
                                },
                                {
                                    "name": "echo4",
                                    "description": "echo",
                                    "dataType": "STRING",
                                    "isNeed": true,
                                    "value": "4",
                                    "children": null
                                }
                            ],
                            "children": null
                        },
                        {
                            "name": "echo3",
                            "description": "echo",
                            "dataType": "STRING",
                            "isNeed": true,
                            "value": "3",
                            "children": null
                        },
                        {
                            "name": "echo4",
                            "description": "echo",
                            "dataType": "OBJECT",
                            "isNeed": true,
                            "value": null,
                            "children": [
                                {
                                    "name": "echo5",
                                    "description": "echo",
                                    "dataType": "ARRAY",
                                    "isNeed": true,
                                    "value": [
                                        {
                                            "name": "echo5",
                                            "description": "echo",
                                            "dataType": "NUMBER",
                                            "isNeed": true,
                                            "value": 5,
                                            "children": null
                                        }
                                    ],
                                    "children": null
                                }
                            ]
                        }
                    ]
                }
                """;
        JSON parse = JSONUtil.parse(requestBody);
        InvokeDataInfo dataInfo = parse.toBean(InvokeDataInfo.class);

        String jsonStr = dataInfo.toJson();
        System.out.println(jsonStr);
    }

    /**
     * 获取赋完值的JSON对象
     *
     * @return
     */
    public Map<String, Object> getMap() {
        Body body = new Body();
        visitDataInfo(body, this);
        return body;
    }

    private void visitDataInfo(Body parent, InvokeDataInfo node) {
        // 父节点是object，而子节点没有孩子，则挂载到父节点上
        if (node.getChildren() == null) {
            if (node.getDataType().equals(DataType.OBJECT) || node.getIsNeed() && node.getValue() == null) {
                throw new IllegalArgumentException("缺少参数:" + node.getName());
            }
            if (node.getDataType().equals(DataType.ARRAY)) {
                List<Object> o = (List) parent.computeIfAbsent(node.getName(), k -> new ArrayList<>());
                visitDataInfo(o, (JSONArray) node.getValue());
                return;
            }
            parent.put(node.getName(), node.getValue());
            return;
        }

        // 若子节点有孩子，则遍历子节点的孩子
        for (InvokeDataInfo child : node.getChildren()) {
            // 若子节点不是object类型
            if (!child.getDataType().equals(DataType.OBJECT) && !child.getDataType().equals(DataType.ARRAY)) {
                parent.put(child.getName(), child.getValue());
                continue;
            }
            if (child.getDataType().equals(DataType.OBJECT)) {
                // 若子节点是object类型，则递归构建子节点body
                Body body1 = (Body) parent.computeIfAbsent(child.getName(), k -> new Body());
                visitDataInfo(body1, child);
                continue;
            }
            // 若子节点是array类型，则递归构建子节点list
            List array = (List) parent.computeIfAbsent(child.getName(), k -> new ArrayList<>());
            visitDataInfo(array, (JSONArray) child.getValue());

        }

    }

    private void visitDataInfo(List<Object> parent, JSONArray jsonArray) {
        List<InvokeDataInfo> nodeList = jsonArray.toList(InvokeDataInfo.class);
        for (InvokeDataInfo node : nodeList) {
            if (node.getDataType().equals(DataType.OBJECT)) {
                Body body = new Body();
                visitDataInfo(body, node);
                parent.add(body);
            }
            if (node.getDataType().equals(DataType.ARRAY)) {
                visitDataInfo(parent, (JSONArray) node.getValue());
            }
            parent.add(node.getValue());
        }

    }

    public String toJson() {
        Body body = new Body();
        Body body1 = null;
        if (this.getDataType().equals(DataType.OBJECT)) {
            body1 = new Body();
            body.put(this.getName(), body1);
        }
        visitDataInfo(body1 == null ? body : body1, this);
        return JSONUtil.toJsonPrettyStr(body);
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @ToString
    @NoArgsConstructor
    public static class Body extends HashMap<String, Object> {

    }
}