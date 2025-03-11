package org.huang.bigevent.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    private Integer code; //状态码.0表示成功，1表示失败
    private String message;
    private Object data;
}

//class test{
//    public static void main(String[] args) {
//        Result result = Result.builder().code(200).message("success").data("hello").build();
//        System.out.println(result);
//    }
//}
