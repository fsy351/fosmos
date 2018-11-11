package com.fosmos.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    ModelMapper modelMapper;


}
