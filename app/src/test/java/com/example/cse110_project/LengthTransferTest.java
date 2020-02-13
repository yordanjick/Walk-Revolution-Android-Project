package com.example.cse110_project;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class LengthTransferTest {
    public LengthTransfer lengthTransfer;
    @Before
    public void init(){
        lengthTransfer=new LengthTransfer();
    }

    @Test
    public void normalTest(){
        assertEquals(lengthTransfer.transfer(121566),"121.57k");
    }
    @Test
    public void normalTest2(){
        assertEquals(lengthTransfer.transfer(121564),"121.56k");
    }
    @Test
    public void normalTest3(){
        assertEquals(lengthTransfer.transfer(121),"121.00");
    }
    @Test
    public void cornerTest(){
        assertEquals(lengthTransfer.transfer(0),"0.00");
    }


}
