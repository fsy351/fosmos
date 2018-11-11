package com.fosmos.view;


import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class JsonResponseView implements ResponseView {

    private int code;
    private String message;
    private Object result;

    public static final int CODE_OK = 0;
    public static final int CODE_PARAMS_ERR = 100;
    public static final int CODE_INNER_ERR = 200;
    public static final int CODE_PERMISSON_ERR = 300;
    public static final int CODE_UNKNOWN_ERR = 400;

    public static final String MSG_OK = "OK";
    public static final String MSG_PARAMS_ERR = "PARAMS_ERR";
    public static final String MSG_FILE_EMPTY_ERR = "FILE_EMPTY_ERR";
    public static final String MSG_INNER_ERR = "INNER_ERR";
    public static final String MGS_PERMISSON_ERR = "PERMISSON_ERR";
    public static final String MSG_UNKNOWN_ERR = "UNKNOWN_ERR";

    public void setResult(Object result) {
        this.result = result;
    }

    public static JsonResponseView success(Object result){
        JsonResponseView view =  new JsonResponseView();
        view.setCode(CODE_OK);
        view.setMessage(MSG_OK);
        view.setResult(result);
        return view;
    }

    public static JsonResponseView fail(int errCode, String message){
        JsonResponseView view =  new JsonResponseView();
        view.setCode(errCode);
        view.setMessage(message);
        return view;
    }
}
