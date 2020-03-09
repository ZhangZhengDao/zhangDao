package cn.zhang.com.dto;

import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.exception.CustomizeException;
import lombok.Data;

import java.util.List;

@Data
public class  ResultDTO<T> {
    private Integer code;
    private String message;
    private T list;

    public static ResultDTO ResultDTO(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO okof() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("回复成功");
        return resultDTO;
    }


    public static ResultDTO denglu(CustomizeErrorCode noLogin) {
        return ResultDTO(noLogin.getCode(), noLogin.getMessage());
    }

    public static ResultDTO errof(CustomizeException e) {
        return ResultDTO(e.getCode(), e.getMessage());
    }

    public ResultDTO okofer(List<T> comment) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("回复成功");
        resultDTO.setList(comment);
        return resultDTO;
    }
}
