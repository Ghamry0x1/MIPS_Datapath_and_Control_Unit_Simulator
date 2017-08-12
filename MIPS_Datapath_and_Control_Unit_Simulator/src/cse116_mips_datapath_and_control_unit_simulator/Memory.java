package cse116_mips_datapath_and_control_unit_simulator;

public class Memory {
    
    protected MemoryNode[] mem;
    
    public Memory() {
        mem = new MemoryNode[1023];
        
        for(int i = 0; i<mem.length; i++) {
            mem[i] = new MemoryNode();
        }
    }
    
}
