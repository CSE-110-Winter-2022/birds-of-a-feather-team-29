package com.example.cse110_project;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestValidName {
    @Test
    public void testValidNameEmpty() {
        assertEquals(false, EnterNameActivity.isValidName(""));
    }

    @Test
    public void testValidNameSpecial() {
        assertEquals(false, EnterNameActivity.isValidName("sjfsdl?df dsfsd"));
    }

    @Test
    public void testValidNameCorrect() {
        assertEquals(true, EnterNameActivity.isValidName("Jason Li"));
        assertEquals(true, EnterNameActivity.isValidName("Aiko"));
    }
}
