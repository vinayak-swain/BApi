package com.bajaj.bfhl.service;

import com.bajaj.bfhl.config.UserConfig;
import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BfhlServiceImplTest {

    @Mock
    private UserConfig userConfig;

    private BfhlServiceImpl bfhlService;

    @BeforeEach
    void setUp() {
        when(userConfig.getUserId()).thenReturn("vinayak_swain_18092004");
        when(userConfig.getEmail()).thenReturn("vinayakswain230934@acropolis.in");
        when(userConfig.getRollNumber()).thenReturn("0827AL231145");
        bfhlService = new BfhlServiceImpl(userConfig);
    }

    @Test
    void testExampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals("vinayak_swain_18092004", response.getUserId()),
                () -> assertEquals(List.of("1"), response.getOddNumbers()),
                () -> assertEquals(List.of("334", "4"), response.getEvenNumbers()),
                () -> assertEquals(List.of("A", "R"), response.getAlphabets()),
                () -> assertEquals(List.of("$"), response.getSpecialCharacters()),
                () -> assertEquals("339", response.getSum()),
                () -> assertEquals("Ra", response.getConcatString())
        );
    }

    @Test
    void testExampleB() {
        BfhlRequest request = new BfhlRequest(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(List.of("5"), response.getOddNumbers()),
                () -> assertEquals(List.of("2", "4", "92"), response.getEvenNumbers()),
                () -> assertEquals(List.of("A", "Y", "B"), response.getAlphabets()),
                () -> assertEquals(List.of("&", "-", "*"), response.getSpecialCharacters()),
                () -> assertEquals("103", response.getSum()),
                () -> assertEquals("ByA", response.getConcatString())
        );
    }

    @Test
    void testExampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(Collections.emptyList(), response.getOddNumbers()),
                () -> assertEquals(Collections.emptyList(), response.getEvenNumbers()),
                () -> assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets()),
                () -> assertEquals(Collections.emptyList(), response.getSpecialCharacters()),
                () -> assertEquals("0", response.getSum()),
                () -> assertEquals("EoDdCbAa", response.getConcatString())
        );
    }

    @Test
    void testAllNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "3", "100"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(List.of("3"), response.getOddNumbers()),
                () -> assertEquals(List.of("2", "100"), response.getEvenNumbers()),
                () -> assertEquals(Collections.emptyList(), response.getAlphabets()),
                () -> assertEquals(Collections.emptyList(), response.getSpecialCharacters()),
                () -> assertEquals("105", response.getSum()),
                () -> assertEquals("", response.getConcatString())
        );
    }

    @Test
    void testAllSpecial() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("$", "@", "#"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(Collections.emptyList(), response.getOddNumbers()),
                () -> assertEquals(Collections.emptyList(), response.getEvenNumbers()),
                () -> assertEquals(Collections.emptyList(), response.getAlphabets()),
                () -> assertEquals(List.of("$", "@", "#"), response.getSpecialCharacters()),
                () -> assertEquals("0", response.getSum()),
                () -> assertEquals("", response.getConcatString())
        );
    }

    @Test
    void testSingleAlpha() {
        BfhlRequest request = new BfhlRequest(List.of("z"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(List.of("Z"), response.getAlphabets()),
                () -> assertEquals("Z", response.getConcatString())
        );
    }

    @Test
    void testMixedMultiChar() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a1", "2b", "$$"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals(List.of("a1", "2b", "$$"), response.getSpecialCharacters()),
                () -> assertEquals("0", response.getSum()),
                () -> assertEquals(Collections.emptyList(), response.getAlphabets()),
                () -> assertEquals("", response.getConcatString())
        );
    }

    @Test
    void testIsSuccessTrue() {
        BfhlRequest request = new BfhlRequest(List.of("1"));
        BfhlResponse response = bfhlService.processData(request);

        assertAll(
                () -> assertTrue(response.isSuccess()),
                () -> assertEquals("vinayak_swain_18092004", response.getUserId())
        );
    }
}
