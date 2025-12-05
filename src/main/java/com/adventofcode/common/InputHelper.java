package com.adventofcode.common;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InputHelper {

    public List<String> read(String file) {
        log.info("");
        log.info("Reading file {}", file);
        try (Stream<String> stream = Files.lines(Paths.get(requireNonNull(getClass().getClassLoader().getResource(file)).toURI()))) {
            return stream.toList();
        } catch (Exception e) {
            log.warn("Error while reading file", e);
        }
        return emptyList();
    }

    public List<Integer> parseStringToIntList(String text) {
        return Arrays.stream(text.split(",")).map(Integer::parseInt).toList();
    }

    public List<BigInteger> parseStringToBigIntList(String text) {
        return Arrays.stream(text.split(",")).map(BigInteger::new).toList();
    }

    public List<Integer> parseStringListToIntList(List<String> textList) {
        return textList.stream().map(Integer::parseInt).toList();
    }

    public List<Byte> parseStringToSingleDigitIntList(String text) {
        return Arrays.stream(text.split("")).map(Byte::parseByte).toList();
    }

}
