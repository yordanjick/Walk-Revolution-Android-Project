package com.example.cse110_project;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class NumberFormatterTest {
    @Test
    public void normalTestLength(){
        assertEquals(NumberFormatter.formatDistance(121566),"121.57km");
    }
    @Test
    public void normalTestLength2(){
        assertEquals(NumberFormatter.formatDistance(121564),"121.56km");
    }
    @Test
    public void normalTestLength3(){
        assertEquals(NumberFormatter.formatDistance(121),"121.00m");
    }
    @Test
    public void cornerTestLength(){
        assertEquals(NumberFormatter.formatDistance(0),"0.00m");
    }
    @Test
    public void cornerTestLength2(){
        assertEquals(NumberFormatter.formatDistance(-12.0),"0.00m");
    }

    @Test
    public void normalTestStep(){
        assertEquals(NumberFormatter.formatStep(100),"100s");
    }
    @Test
    public void normalTestStep2(){
        assertEquals(NumberFormatter.formatStep(1250),"1.3ks");
    }
    @Test
    public void normalTestStep3(){
        assertEquals(NumberFormatter.formatStep(999000),"999.0ks");
    }
    @Test
    public void cornerTestStep(){
        assertEquals(NumberFormatter.formatStep(0),"0s");
    }
    @Test
    public void cornerTestStep2(){
        assertEquals(NumberFormatter.formatStep(-2),"0s");
    }
}
