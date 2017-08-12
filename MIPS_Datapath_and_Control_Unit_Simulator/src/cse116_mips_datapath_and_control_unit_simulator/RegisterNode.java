package cse116_mips_datapath_and_control_unit_simulator;

public class RegisterNode {
    
    protected String name;
    protected int value;
    
    public RegisterNode() {
        this.name = null;
        this.value = 0;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public String getName() {
        return name;
    }
    
    public int getValue() {
        return value;
    }
    
}
