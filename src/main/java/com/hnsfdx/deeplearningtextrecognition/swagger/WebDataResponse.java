package com.hnsfdx.deeplearningtextrecognition.swagger;

import com.hnsfdx.deeplearningtextrecognition.pojo.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Response对象", description = "Response返回的model示例")
public class WebDataResponse {
    @ApiModelProperty(name = "status", value = "请求成功，server端执行的状态", example = "success/fail")
    private String status;
    @ApiModelProperty(name = "data", value = "请求成功，server端执行成功返回的数据")
    private User data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
