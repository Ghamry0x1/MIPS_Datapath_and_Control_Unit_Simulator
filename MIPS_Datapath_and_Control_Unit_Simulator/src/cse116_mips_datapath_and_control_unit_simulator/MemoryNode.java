package cse116_mips_datapath_and_control_unit_simulator;

public class MemoryNode {
    
    protected int value;
    
    public MemoryNode() {
        this.value = 0;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
}
