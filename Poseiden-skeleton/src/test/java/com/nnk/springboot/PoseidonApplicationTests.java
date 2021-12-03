package com.nnk.springboot;

import com.nnk.springboot.controllers.LoginController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PoseidonApplicationTests {

    @Autowired
    private LoginController controller;

    @Test
    void contextLoads(){
        Assertions.assertNotNull(controller);
    }
}
