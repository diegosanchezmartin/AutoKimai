package com.firstProject.scheduleX.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {
    @Test
    void testExample () {
        assertEquals("Hello",new Example().hello());
    }

    @Test
    void sumaExample () { assertEquals( 3, new Example().suma(1,2));}



}