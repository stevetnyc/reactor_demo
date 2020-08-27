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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public class DemoPOJO {

    @JsonSerialize(using = ToStringSerializer.class)
    private final long id;

    private final String value;


    Long getId() {
        return this.id;
    }

}
