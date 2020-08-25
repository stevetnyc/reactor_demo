/*
 *    ---------------------------------------------------
 *      Steve Thompson, 2020
 *
 *      A simple POJO to encapsulate objects
 *      for the RestController
 *
 *    ---------------------------------------------------
 */

package com.example.reactor_demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Demo_POJO {
    private long id;
    private String value;

    Long getId() {
        return this.id;
    }
}
