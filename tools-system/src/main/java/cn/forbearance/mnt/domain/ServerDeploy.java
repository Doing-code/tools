package cn.forbearance.mnt.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author cristina
 */
@Getter
@Setter
@TableName("mnt_server")
public class ServerDeploy {

    @TableId(type = IdType.ASSIGN_ID, value = "server_id")
    @ApiModelProperty(value = "ID", hidden = true)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "服务器名称")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "端口")
    private Integer port;

    @ApiModelProperty(value = "ACL in Redis >= 6.0")
    private String aclUsername;

    @TableField("`separator`")
    @ApiModelProperty(value = "分隔符 in Redis")
    private String separator;

    @TableField("`type`")
    @ApiModelProperty(value = "1：Redis、2：MySQL、3：Zookeeper、4：Kafka")
    private int type;

    @ApiModelProperty(value = "'创建者'")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "'更新者'")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "'创建时间'")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime = LocalDateTime.now();

    @ApiModelProperty(value = "'更新时间'")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public void copy(ServerDeploy source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServerDeploy that = (ServerDeploy) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
