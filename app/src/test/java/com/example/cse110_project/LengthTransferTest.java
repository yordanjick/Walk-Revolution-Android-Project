package com.example.cse110_project;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LengthTransferTest {
    @Test
    public void normalTest(){
        assertEquals(LengthTransfer.transfer(121566),"121.57k");
    }
    @Test
    public void normalTest2(){
        assertEquals(LengthTransfer.transfer(121564),"121.56k");
    }
    @Test
    public void normalTest3(){
        assertEquals(LengthTransfer.transfer(121),"121.00");
    }
    @Test
    public void cornerTest(){
        assertEquals(LengthTransfer.transfer(0),"0.00");
    }


}
