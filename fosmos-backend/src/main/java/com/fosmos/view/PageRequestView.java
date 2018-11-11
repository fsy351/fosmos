package com.fosmos.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class PageRequestView implements RequestView {

    private Integer cur;
    private Integer size = 10;
    private String order = null;
    private boolean asc = false;
    private Map<String,String> params;

}
