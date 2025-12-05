package com.adventofcode.year2022.day11;

import java.math.BigInteger;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {

    private BigInteger worryLevel;

    private Integer monkeyTarget;

}
