package com.adventofcode.year2020.day4;

import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;
import java.util.regex.Matcher;
import jakarta.validation.Validation;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Passport {

    @Min(1920)
    @Max(2002)
    private final String byr;
    @Min(2010)
    @Max(2020)
    private final String iyr;
    @Min(2020)
    @Max(2030)
    private final String eyr;
    private final String hgt;
    @Pattern(regexp = "#([0-9a-f]{6})")
    private final String hcl;
    @Pattern(regexp = "amb|blu|brn|gry|grn|hzl|oth")
    private final String ecl;
    @Max(999999999)
    @Size(min = 9, max = 9)
    private final String pid;
    private final String cid; // Optional

    public Passport(String input) {
        byr = initField(input, "(byr:)(.+?) ");
        iyr = initField(input, "(iyr:)(.+?) ");
        eyr = initField(input, "(eyr:)(.+?) ");
        hgt = initField(input, "(hgt:)(.+?) ");
        hcl = initField(input, "(hcl:)(.+?) ");
        ecl = initField(input, "(ecl:)(.+?) ");
        pid = initField(input, "(pid:)(.+?) ");
        cid = initField(input, "(cid:)(.+?) ");
    }

    private String initField(String passportText, String patternString) {
        Matcher matcher = java.util.regex.Pattern.compile(patternString).matcher(passportText);
        return matcher.find() ? matcher.group(2) : null;
    }

    public boolean isValidPart1() {
        return nonNull(byr) && nonNull(iyr) && nonNull(eyr) && nonNull(hgt) && nonNull(hcl) && nonNull(ecl) && nonNull(pid);
    }

    public boolean isValidPart2() {
        return isEmpty(Validation.buildDefaultValidatorFactory().getValidator().validate(this)) && isHeightValid();
    }

    private boolean isHeightValid() {
        int height = getNumericHeight();
        if (hgt.endsWith("in")) {
            return height >= 59 && height <= 76;
        } else if (hgt.endsWith("cm")) {
            return height >= 150 && height <= 193;
        } else {
            return false;
        }
    }

    private int getNumericHeight() {
        try {
            return Integer.parseInt(hgt.substring(0, hgt.length() - 2));
        } catch (Exception e) {
            return 0;
        }
    }

}
