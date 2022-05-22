package com.example.demo.RequestBody;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
public class Pay {
    Boolean status = Boolean.FALSE;
    String comment;
    @NotNull
    String to;
    @Min(1)
    @Max(100000)
    int summa;

}
