package org.huang.bigevent.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Avatar {
    private Integer userId;
    private String avatarName;
}
