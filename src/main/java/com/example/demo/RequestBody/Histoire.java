package com.example.demo.RequestBody;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Histoire {
    @Min(1)
    int rows = 10;
    String operation = "all";
}
