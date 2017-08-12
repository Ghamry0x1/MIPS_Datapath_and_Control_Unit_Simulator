package cse116_mips_datapath_and_control_unit_simulator;

public class Registers extends RegisterNode{
    
    protected RegisterNode[] regArr;
    
    public Registers() {
        regArr = new RegisterNode[32];
        
        for(int i = 0; i<regArr.length; i++) {
            regArr[i] = new RegisterNode();
        }
        
        regArr[0].setName("$zero");
        regArr[1].setName("$at");
        regArr[2].setName("$v0");
        regArr[3].setName("$v1");
        regArr[4].setName("$a0");
        regArr[5].setName("$a1");
        regArr[6].setName("$a2");
        regArr[7].setName("$a3");
        regArr[8].setName("$t0");
        regArr[9].setName("$t1");
        regArr[10].setName("$t2");
        regArr[11].setName("$t3");
        regArr[12].setName("$t4");
        regArr[13].setName("$t5");
        regArr[14].setName("$t6");
        regArr[15].setName("$t7");
        regArr[16].setName("$s0");
        regArr[17].setName("$s1");
        regArr[18].setName("$s2");
        regArr[19].setName("$s3");
        regArr[20].setName("$s4");
        regArr[21].setName("$s5");
        regArr[22].setName("$s6");
        regArr[23].setName("$s7");
        regArr[24].setName("$t8");
        regArr[25].setName("$t9");
        regArr[26].setName("$k0");
        regArr[27].setName("$k1");
        regArr[28].setName("$gp");
        regArr[29].setName("$sp");
        regArr[30].setName("$fp");
        regArr[31].setName("$ra");
        
        for(int i = 0; i < regArr.length; i++) {
            regArr[i].setValue(0);
        }
    }
    
}
