package cn.dev.system.domain.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author YangXw
 * @date 2023/03/08 17:54
 * @description
 */
@Data
public class UserVO {
    private String userId;
    private String userName;
    private String nickName;
    private String phoneNumber;
    private String status;
    private String email;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String remark;
    private String roleName;
}
