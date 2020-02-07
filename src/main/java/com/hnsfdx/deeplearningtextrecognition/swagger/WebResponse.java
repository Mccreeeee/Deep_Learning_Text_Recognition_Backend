package com.hnsfdx.deeplearningtextrecognition.swagger;

import com.hnsfdx.deeplearningtextrecognition.pojo.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Response对象", description = "Response返回的model示例")
public class WebResponse {
    @ApiModelProperty(name = "status", value = "请求成功，server端执行的状态", example = "success/fail")
    private String status;
    @ApiModelProperty(name = "reason", value = "请求成功，但是server端执行失败的原因（只在status = fail时有效）", example = "验证码校验有误，请重新输入！")
    private String reason;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
