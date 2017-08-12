package cse116_mips_datapath_and_control_unit_simulator;

import java.util.ArrayList;
import java.util.Scanner;

public class CSE116_MIPS_Datapath_and_Control_Unit_Simulator {

    static Registers reg = new Registers(); //32 Registers
    
    /*Opcode and Function*/
    static int opcode; 
    static int fn;
    
    /*Shift Amount and Immediate/Offset Value*/
    static int shamt = 0;
    static int imm;
    
    /*PC variables*/
    static int pc;
    static int pcStart;
    static int pcPlusFour;
    
    /*For Storing Instructions Binary Code*/
    static ArrayList<String> Code = new ArrayList();
    static ArrayList<String> Data = new ArrayList();
    static int type; //1 Rtype, 2 Itype, 3 Jtype
    
    
    static Memory memory = new Memory(); //Memory Array
    
    /*Control Unit Signals*/
    static int RegDest = 0;
    static int Branch = 0;
    static int MemRead = 0;
    static int MemToReg = 0;
    static int ALUOp = 0;
    static int MemWrite = 0;
    static int ALUSrc = 0;
    static int RegWrite = 0;
    static int ALUCtrl = 0;
    static int jump = 0;
    
    /*rd, rs, rt Inputs*/
    static int rd;
    static int rs;
    static int rt;
   
    static Scanner sc = new Scanner(System.in);
     
    public static void main(String[] args) {
        System.out.println("-- MIPS SIMULATOR PROJECT --\n");

        StartAddress();
        LoadDataInMem();
        
        System.out.println("\nProgram is building... ");
        
        readInstructions();
        execute();
        
        reg.regArr[0].setValue(0);
        
        printMem();
        printRegValues();
        printCode();
        printData();
        printDataPath();
        
        credits();
        System.exit(0);
    
    }
    
    static void StartAddress(){
        /*Starting Address for the program*/
        System.out.println("Enter the starting address: ");
        pc = sc.nextInt();
        pcStart = pc;
        pcPlusFour = pc;
    }
    
    static void readInstructions(){
        int flag = 1;
        while(flag == 1) {
            System.out.println("\nSelect your instruction **Enter -1 to terminate**");
            printIns(); //Printing Instructions
            
            /*Selecting Instruction*/
            int i = sc.nextInt();
            switch(i) {
                
                case 1: { //add
                    //add rd rs rt
                    setOpAndFn(0,32);
                    shamt = 0;
                    
                    setCtrlUnit(1,0,0,0,2,0,0,1,2,0);

                    readRd();
                    readRs();
                    readRt();
                    
                    store(1); //Store Instruction 32Bit-BinaryCode in ArrayList
                    storeCode(""+ "add" + " " + reg.regArr[rd].getName() + " " + reg.regArr[rs].getName() + " " + reg.regArr[rt].getName());
                    
                    /*Incrementing PC*/
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 2: { //addi
                    //addi rt rs imm
                    setOpAndFn(8,0);
                    shamt = 0;
                    
                    setCtrlUnit(0,0,0,0,0,0,1,1,2,0);
                     
                    readRt();
                    readRs();
                    readImm();
                    
                    store(2);
                    storeCode(""+ "addi" + " " + reg.regArr[rt].getName() + " " + reg.regArr[rs].getName() + " " + imm);
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 3: { //lw
                    //lw rt imm(rs)
                    setOpAndFn(35,0);
                    shamt = 0;
                    
                    setCtrlUnit(0,0,1,1,0,0,1,1,2,0);
                    
                    readRt();
                    readOffset();
                    readRs();
                    
                    store(2);
                    storeCode(""+ "lw" + " " + reg.regArr[rt].getName() + " " + imm + " ( " + reg.regArr[rs].getName() + " )");
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 4: { //sw
                    //sw rt imm(rs)
                    setOpAndFn(43,0);
                    shamt = 0;
                    
                    setCtrlUnit(0,0,0,0,2,1,1,0,2,0);

                    readRt();
                    readOffset();
                    readRs();
                    
                    store(2);
                    storeCode(""+ "sw" + " " + reg.regArr[rt].getName() + " " + imm + " ( " + reg.regArr[rs].getName() + " )");
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 5: { //sll
                    //sll rd rt shamt	
                    setOpAndFn(0,0);
                    
                    setCtrlUnit(1,0,0,0,2,0,0,1,0,0);
                    
                    readRd();
                    readRt();
                    readShamt();
                    
                    store(1);
                    storeCode(""+ "sll" + " " + reg.regArr[rd].getName() + " " + reg.regArr[rt].getName() + " " + shamt);
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 6: { //and
                    //and rd rs rt
                    setOpAndFn(0,36);
                    shamt = 0;

                    setCtrlUnit(1,0,0,0,2,0,0,1,0,0);
                    
                    readRd();
                    readRs();
                    readRt();
                    
                    store(1);
                    storeCode(""+ "and" + " " + reg.regArr[rd].getName() + " " + reg.regArr[rs].getName() + " " + reg.regArr[rt].getName());
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 7: { //andi
                    //andi rt rs imm
                    setOpAndFn(12,0);
                    shamt = 0;
                    
                    setCtrlUnit(0,0,0,0,0,0,1,1,1,0);
                   
                    readRt();
                    readRs();
                    readImm();
                    
                    store(2);
                    storeCode(""+ "andi" + " " + reg.regArr[rt].getName() + " " + reg.regArr[rs].getName() + " " + imm);
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 8: { //or
                    //or rd rs rt
                    setOpAndFn(0,37);
                    shamt = 0;
                    
                    setCtrlUnit(1,0,0,0,2,0,0,1,1,0);
                    
                    readRd();
                    readRs();
                    readRt();

                    store(1);
                    storeCode(""+ "or" + " " + reg.regArr[rd].getName() + " " + reg.regArr[rs].getName() + " " + reg.regArr[rt].getName());
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }
                case 9: { //ori
                    //ori rt rs imm
                    setOpAndFn(13,0);
                    shamt = 0;
                    
                    setCtrlUnit(0,0,0,0,0,0,1,1,1,0);
                    
                    readRt();
                    readRs();
                    readImm();

                    store(2);
                    storeCode(""+ "ori" + " " + reg.regArr[rt].getName() + " " + reg.regArr[rs].getName() + " " + imm);
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }

                case 10: { //nor
                    //nor rd rs rt
                    setOpAndFn(0,39);
                    shamt = 0;
                    
                    setCtrlUnit(1,0,0,0,2,0,0,1,0,0);
                    
                    readRd();
                    readRs();
                    readRt();

                    store(1);
                    storeCode(""+ "nor" + " " + reg.regArr[rd].getName() + " " + reg.regArr[rs].getName() + " " + reg.regArr[rt].getName());
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }

                case 11: { //beq
                    //beq rs rt offset
                    setOpAndFn(4,0);
                    shamt = 0;

                    setCtrlUnit(0,1,0,0,1,0,0,0,6,0);
                    
                    readRs();
                    readRt();
                    readOffset();

                    store(2);
                    storeCode(""+ "beq" + " " + reg.regArr[rs].getName() + " " + reg.regArr[rt].getName() + " " + imm);
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }

                case 12: { //j
                    //j target
                    setOpAndFn(2,0);
                    shamt = 0;
                    
                    setCtrlUnit(0,0,0,0,0,0,0,0,0,1);
                    
                    readAddress();
                    
                    store(3);
                    storeCode(""+ "j" + " " + imm);
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }

                case 13: { //jal
                    //jal target
                    setOpAndFn(3,0);
                    shamt = 0;
                    
                    setCtrlUnit(0,0,0,0,0,0,0,0,0,1);
                    
                    readAddress();
                    
                    store(3);
                    storeCode(""+ "jal" + " " + imm);
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    break;
                }

                case 14: { //jr
                    //jr ra
                    setOpAndFn(0,8);
                    shamt = 0;

                    setCtrlUnit(1,0,0,0,2,0,0,1,2,1);
                    
                    /*setting value for ra as rs*/
                    rd=0;
                    rt=0;
                    rs=31;
                    
                    store(1);
                    storeCode(""+ "jr" + " " + reg.regArr[rs].getName());
                    
                    pc+=4;
                    pcPlusFour+=4;
                    
                    
                    break;
                }

                case 15: { //slt
                    //slt rd rs rt
                    setOpAndFn(0,42);
                    shamt = 0;
                    
                    setCtrlUnit(1,0,0,0,2,0,0,1,7,0);
                    
                    readRd();
                    readRs();
                    readRt();
                    
                    store(1);
                    storeCode(""+ "slt" + " " + reg.regArr[rd].getName() + " " + reg.regArr[rs].getName() + " " + reg.regArr[rt].getName());
                    
                    pc+=4;
                    pcPlusFour+=4;                    
                   
                    break;
                }

                case -1: { //terminate
                    System.out.println("\nFetching in progress...");
                    flag = -1;//break out of reading instructions
                    
                    break;
                }
                default: {
                   System.out.println("Error.. Cannot find Instruction!");
                   System.out.println("Please select a valid instruction"); 
                   break;
                }
            } 
        }
    }
    
    static void LoadDataInMem(){
        /*Loading Data in the memory (Creating Array)*/
        System.out.println("\nDo you want to load any data in the memory? ");
        System.out.println("[1] Yes\n[2] No");
        int flag1 = sc.nextInt();
        
        while(flag1 == 1) {
            int flag2 = 1;
            
            System.out.println("\nSelect the register that holds the array base address: "); //Arr[]
            printReg(); //Printing Registers
            int r = sc.nextInt();
            
            System.out.println("\nBase address: ");
            int d = sc.nextInt();
            reg.regArr[r].setValue(d);
            
            while (flag2 == 1){
                System.out.println("\nAfter how many elements from the base address you want to add the value? "); //Arr[t*4]
                int t = sc.nextInt();

                System.out.println("\nEnter the value you wish to store in your memory: "); //Arr[t] = w;
                int w = sc.nextInt();

                memory.mem[d + (t*4)].setValue(w);

                System.out.println("\nDo you want to add any more data for this register that holds the array base address? ");
                System.out.println("[1] Yes\n[2] No");
                flag2 = sc.nextInt();
            }
            
            System.out.println("\nDo you want to add any more data?");
            System.out.println("[1] Yes\n[2] No");
            flag1 = sc.nextInt();
        }
    }
    
    static void printReg() {
            System.out.println("[0] $zero"
                            + "\n[2] $v0"
                            + "\n[3] $v1"
                            + "\n[4] $a0"
                            + "\n[5] $a1"
                            + "\n[6] $a2"
                            + "\n[7] $a3"
                            + "\n[8] $t0"
                            + "\n[9] $t1"
                            + "\n[10] $t2"
                            + "\n[11] $t3"
                            + "\n[12] $t4"
                            + "\n[13] $t5"
                            + "\n[14] $t6"
                            + "\n[15] $t7"
                            + "\n[16] $s0"
                            + "\n[17] $s1"
                            + "\n[18] $s2"
                            + "\n[19] $s3"
                            + "\n[20] $s4"
                            + "\n[21] $s5"
                            + "\n[22] $s6"
                            + "\n[23] $s7"
                            + "\n[24] $t8"
                            + "\n[25] $t9"
                            + "\n[29] $sp"
                            + "\n[31] $ra");
        }
    
    static void printIns(){
        System.out.println("[1] add"
                        + "\n[2] addi"
                        + "\n[3] lw"
                        + "\n[4] sw"
                        + "\n[5] sll"
                        + "\n[6] and"
                        + "\n[7] andi"
                        + "\n[8] or"
                        + "\n[9] ori"
                        + "\n[10] nor"
                        + "\n[11] beq"
                        + "\n[12] j"
                        + "\n[13] jal"
                        + "\n[14] jr"
                        + "\n[15] slt");
        }

    static void setOpAndFn(int op, int func) {
        opcode = op;
        fn = func;
    }
    
    static void setCtrlUnit(int RegD, int Br, int MemRd, int Mem2Reg, int ALUO, int MemWr, int ALUS, int RegWr, int ALUC, int J) {
                        RegDest = RegD;
                        Branch = Br;
                        MemRead = MemRd;
                        MemToReg = Mem2Reg;
                        ALUOp = ALUO;
                        MemWrite = MemWr;
                        ALUSrc = ALUS;
                        RegWrite = RegWr;
                        ALUCtrl = ALUC;
                        jump = J;
        }

    static void readRd() {
        System.out.println("\nSelect rd");
        printReg();
        rd = sc.nextInt();   
        
        while(rd < 0 || rd > 31){
            System.out.println("Please choose a valid register: ");
            printReg();
            rd = sc.nextInt();
        }
    }

    static void readRs() {
        System.out.println("\nSelect rs");
        printReg();
        rs = sc.nextInt();
        
        while(rs < 0 || rs > 31){
            System.out.println("\nPlease choose a valid register: ");
            printReg();
            rs = sc.nextInt();
        }
    }

    static void readRt() {
        System.out.println("\nSelect rt");
        printReg();
        rt = sc.nextInt(); 
        
        while(rt < 0 || rt > 31){
            System.out.println("\nPlease choose a valid register: ");
            printReg();
            rt = sc.nextInt();
        }
    }

    static void readImm() {
        System.out.println("\nEnter immediate value: ");
        imm = sc.nextInt();
        
        while(imm > 65535) {
            System.out.println("\nPlease enter an integer less than 65,535");
            imm = sc.nextInt();
        }
     }

    static void readOffset() {
        System.out.println("\nEnter offset: ");
        imm = sc.nextInt();
        
        while(imm > 65535) {
            System.out.println("Please enter an integer less than 65,535");
            imm = sc.nextInt();
        }
    }

    static void readShamt() {
        System.out.println("\nEnter shifting value: ");
        shamt = sc.nextInt();

        while(shamt > 31) {
            System.out.println("Please enter an integer less than 65,535");
            shamt = sc.nextInt();
        }
        }

    static void readAddress() {
        System.out.println("\nEnter address value: ");
        imm = sc.nextInt();

        while(imm > pcStart && imm < pc) {
            System.out.println("You are jumping out of range");
            imm = sc.nextInt();
        }    
    }
    
    static void printData(){
        System.out.println("\nMachine Code is:");
        for(int i = 0; i < Data.size(); i++) {
            System.out.println(Data.get(i));
        }
    }

    static void store(int flag){
        switch (flag) {
            case 1:
                String Type_R = String.format("%06d",DecToBin(opcode))
                              + String.format("%05d",DecToBin(rs))   
                              + String.format("%05d",DecToBin(rt))
                              + String.format("%05d",DecToBin(rd))
                              + String.format("%05d",DecToBin(shamt))
                              + String.format("%06d",DecToBin(fn));
                Data.add(Type_R);
                break;
            case 2:
                String Type_I = String.format("%06d",DecToBin(opcode))
                              + String.format("%05d",DecToBin(rs))   
                              + String.format("%05d",DecToBin(rt))
                              + DecToBinStr(imm);
                Data.add(Type_I);
                break;
            case 3:
                String Type_J = String.format("%06d",DecToBin(opcode))
                              + String.format("%026d",DecToBin(imm));
                Data.add(Type_J);
                break;
            default:
                break;
        }
        
    }
    
    static void execute() {
        /*((pc-Data.size()*4)) - pcStart) = 0 at first loop *First Instruction to be excuted**/
        int j = ((pcPlusFour-(Data.size()*4)) - pcStart) / 4;
        while (j<Data.size()){    
            int immDec; //Immediate in DEC
            
            //try{
                String OP = Data.get(j).substring(0,6); //Opcode in Binary

                switch(OP) { //Check Instruction Type
                    case "000000": //R - Type

                        switch(Data.get(j).substring(26, 32)) { //Function in Binary

                            case "000000": //sll
                                //rd = rt << shamt
                                reg.regArr[BinToDec(Data.get(j).substring(16, 21))].setValue(reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue() << BinToDec(Data.get(j).substring(21, 26)));
                                j++; //next instruction
                                break;

                            case "001000": //jr
                                // jr rs *$ra*
                                // sets PC to the value in $ra which is return address
                                pc = reg.regArr[31].getValue();
                                j = (pc - pcStart) / 4; 
                                break;

                            case "100000": //add
                                //rd = rs + rt
                                reg.regArr[BinToDec(Data.get(j).substring(16, 21))].setValue(reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() + reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue());
                                j++;
                                break;

                            case "100100": //and    
                                //rd = rs & rt
                                reg.regArr[BinToDec(Data.get(j).substring(16, 21))].setValue(reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() & reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue());
                                j++;
                                break;

                            case "100101": //or
                                //rd = rs | rt
                                reg.regArr[BinToDec(Data.get(j).substring(16, 21))].setValue(reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() | reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue());
                                j++;
                                break;

                            case "100111": //nor
                                //rd = ~ ( rs | rt )
                                reg.regArr[BinToDec(Data.get(j).substring(16, 21))].setValue( ~ (reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() | reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue()));
                                j++;
                                break;

                            case "101010": //slt
                                //rd = rs < rt ? 1 : 0;
                                if(reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() < reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue()) 
                                    reg.regArr[BinToDec(Data.get(j).substring(16, 21))].setValue(1);
                                else 
                                    reg.regArr[BinToDec(Data.get(j).substring(16, 21))].setValue(0);
                                j++;
                                break; 
                            }
                        break;
                    case "000010": //j
                        //j imm*4
                        immDec = BinToDec(Data.get(j).substring(6)); // 26bit target
                        pc = immDec * 4;
                        j = (pc - pcStart) / 4;
                        break;

                    case "000011": //jal
                        //jal imm*4  
                        //ra = pc
                        reg.regArr[31].setValue(pcPlusFour - ((Data.size() - 1 - j) * 4));
                        immDec = BinToDec(Data.get(j).substring(6));
                        pc = immDec * 4;
                        j = (pc - pcStart) / 4;
                        break;

                    case "000100": //beq
                        // pc = rs == rt ? pc + offset * 4 : pc;
                            immDec = BinToDecStr(Data.get(j).substring(16));
                        if((reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue()) == (reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue())) {
                            pc = pcPlusFour - ((Data.size() - 1 - j) * 4) + (immDec * 4);
                            j = (pc - pcStart) / 4;
                        }
                        else 
                            j++;
                        break;

                        case "001000"://addi
                        // rt = rs + imm
                        immDec = BinToDecStr(Data.get(j).substring(16));
                        reg.regArr[BinToDec(Data.get(j).substring(11, 16))].setValue(reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() + immDec);
                        j++;
                        break;

                    case "001100"://andi
                        // rt = rs & imm
                        immDec = BinToDecStr(Data.get(j).substring(16));
                        reg.regArr[BinToDec(Data.get(j).substring(11, 16))].setValue(reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() & immDec);
                        j++;
                        break;

                    case "001101": //ori
                        // rt = rs | imm
                        immDec = BinToDecStr(Data.get(j).substring(16));
                        reg.regArr[BinToDec(Data.get(j).substring(11, 16))].setValue(reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue() | immDec);
                        j++;
                        break;

                    case "100011"://lw
                        // rt = rs[offset/4]
                        immDec = BinToDec(Data.get(j).substring(16));
                        int element1 = immDec; //array index
                        element1 = element1 + reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue(); //index in MemArray (adding index to assigned address in register)
                        reg.regArr[BinToDec(Data.get(j).substring(11, 16))].setValue(memory.mem[element1].getValue());//loading value from memory into register
                        j++;
                        break;

                    case "101011"://sw
                        // rs[offset/4] = rt
                        immDec = BinToDec(Data.get(j).substring(16));
                        int element2 = immDec;
                        element2 = element2 + reg.regArr[BinToDec(Data.get(j).substring(6, 11))].getValue(); 
                        memory.mem[element2].setValue(reg.regArr[BinToDec(Data.get(j).substring(11, 16))].getValue());//storing value from register into memory
                        j++;
                        break;

                    default:
                        System.out.println("Error...cannot find Instruction!");
                        break;
                }
            //}
            //catch(ArrayIndexOutOfBoundsException ex){
            //    System.out.println("The program is terminated.. ");
            //   break;
            //}
        }
    }

    static void printDataPath() {
    
        int opcodeDec = opcode;
        int fnDec = fn;
        int shamtDec = shamt;
        int immDec = imm;
        int rsDec = rs;
        int rtDec = rt;
        int rdDec = rd;
        
        /*Converting from Decimal to Binary*/
        RegDest= DecToBin(RegDest);
        Branch= DecToBin(Branch);
        MemRead= DecToBin(MemRead);
        MemToReg= DecToBin(MemToReg);
        ALUOp= DecToBin(ALUOp);
        MemWrite= DecToBin(MemWrite);
        ALUSrc= DecToBin(ALUSrc);
        RegWrite= DecToBin(RegWrite);
        ALUCtrl= DecToBin(ALUCtrl);
        
        opcode =  DecToBin(opcode);
        shamt =  DecToBin(shamt);
        fn =  DecToBin(fn);
        String ImmStr = DecToBinStr(imm);
        
        rs = DecToBin(rs);
        rt = DecToBin(rt);
        rd = DecToBin(rd);
        
        String least16Bits = (Data.get(Data.size() - 1)).substring(16);
        if(least16Bits.charAt(0) == '0'){
            while(least16Bits.length()<32)
                least16Bits = "0" + least16Bits;
        }
        else if(least16Bits.charAt(0) == '1'){
            while(least16Bits.length()<32)
                least16Bits = "1" + least16Bits;
        }
        String shifted = least16Bits.substring(2) + "00";
        
        int targetAddress = BinToDec(shifted.substring(2));
        if(shifted.charAt(0) == '0') {
            targetAddress += pcPlusFour;
        }
        else if(shifted.charAt(0) == '1'){
            targetAddress = (targetAddress * -1) + pcPlusFour;
        }
        int ALUOut = 0;
        if(opcodeDec == 0){
            switch(fnDec){
                case 32://add
                    ALUOut = (reg.regArr[rtDec].getValue() + reg.regArr[rsDec].getValue());
                    break;
                case 0://sll
                    ALUOut = (reg.regArr[rtDec].getValue() << shamt); 
                    break;
                case 36://and
                    ALUOut = (reg.regArr[rtDec].getValue() & reg.regArr[rsDec].getValue());
                    break;
                case 37://or
                    ALUOut = (reg.regArr[rtDec].getValue() | reg.regArr[rsDec].getValue());
                    break;
                case 39://nor
                    ALUOut = ( ~ (reg.regArr[rtDec].getValue() | reg.regArr[rsDec].getValue()));
                    break;
                case 42://slt
                    ALUOut = (reg.regArr[rtDec].getValue() - reg.regArr[rsDec].getValue());
                    break;
                case 8://jr
                    ALUOut = (reg.regArr[rtDec].getValue()) + pcPlusFour;
                    break;
                default: 
                    break;
                    
            }
        }
        
        System.out.println();
        
        if(opcodeDec == 0)/*R-Type*/ {
            System.out.println("• Datapath "
                        + "\no PC starting address: " + pcStart
                        + "\no PC+4 adder output: " + pcPlusFour
                        + "\no Instruction memory output: " + String.format("%06d", opcode) + " " + String.format("%05d", rs) + " " + String.format("%05d", rt) + " " + String.format("%05d", rd) + " " + String.format("%05d", shamt) + " " + String.format("%06d", fn)
                        + "\no opcode field of instruction: " + String.format("%06d", opcode)
                        + "\no rs field of instruction: " + String.format("%05d", rs)
                        + "\no rt field of instruction: " + String.format("%05d", rt)
                        + "\no rd field of instruction: " + String.format("%05d", rd)
                        + "\no RegDestination Mux output: " + String.format("%05d", rd)
                        + "\no Sign-extender output: " + least16Bits
                        + "\no Shift-2 output: " + shifted
                        + "\no Target address adder: " + targetAddress
                        + "\no Function code of instruction: " + String.format("%06d", fn)
                        + "\no Read data 1: " + reg.regArr[rsDec].getValue()
                        + "\no Read data 2: " + reg.regArr[rtDec].getValue()
                        + "\no ALU second input: " + reg.regArr[rtDec].getValue()
                        + "\no ALU output: " + ALUOut
                        + "\no Zero flag: " + 0
                        + "\no Data memory output: " + "N/A"
                        + "\no MemtoReg Mux output: " + ALUOut
                        + "\no AND gate output: " + 0
                        + "\n\n• Control signals"
                        + "\no RegDest: " + RegDest
                        + "\no Jump: " + jump
                        + "\no Branch: " + Branch
                        + "\no MemRead: " + MemRead
                        + "\no MemtoReg: " + MemToReg
                        + "\no ALUOp: " + String.format("%02d", ALUOp)
                        + "\no MemWrite: " + MemWrite
                        + "\no ALUSrc: " + ALUSrc
                        + "\no RegWrite: " + RegWrite
                        + "\no ALU control output: " + String.format("%04d", ALUCtrl));
        }
        else if(opcodeDec == 2 || opcodeDec == 3) /*J-TYPE*/ { 
            
        System.out.println("• Datapath: "
                        + "\no PC starting address: " + pcStart
                        + "\no PC+4 adder output: " + pcPlusFour
                        + "\no Instruction memory output: " + String.format("%06d", opcode) + " " + String.format("%026d", imm)
                        + "\no opcode field of instruction: " + String.format("%06d", opcode)
                        + "\no offset field of instruction: " + String.format("%026d", imm)
                        + "\no Sign-extender output: " + least16Bits
                        + "\no Shift-2 output: " + shifted
                        + "\no Target address adder: " + targetAddress
                        + "\no Function code of instruction: " + String.format("%06d", fn)
                        + "\no Zero flag: " + 0
                        + "\no Data memory output: " + "N/A"
                        + "\no AND gate output: " + 0
                        + "\n\n• Control signals"
                        + "\no RegDest: " + RegDest
                        + "\no Jump: " + jump
                        + "\no Branch: " + Branch
                        + "\no MemRead: " + MemRead
                        + "\no MemtoReg: " + MemToReg
                        + "\no ALUOp: " + String.format("%02d", ALUOp)
                        + "\no MemWrite: " + MemWrite
                        + "\no ALUSrc: " + ALUSrc
                        + "\no RegWrite: " + RegWrite
                        + "\no ALU control output: " + String.format("%04d", ALUCtrl));
        }
        else /*I-TYPE*/ {
            
            String s = null; //data mem op 
            String r = null; //memtoreg mux op
            String l = null; //aluop
            int p = 0; //zero and and gate
            int targetAddress2 = 0;
            
            if(opcodeDec == 35) { //lw
                s = Integer.toString(memory.mem[(reg.regArr[rsDec].getValue() + (immDec / 4))].getValue());
                r = s;
                l = Integer.toString(reg.regArr[rsDec].getValue() + (immDec / 4));
                p = 0;
                targetAddress2 = targetAddress;
            }
            else if(opcodeDec == 43) { //sw 
                s= "N/A";
                r = Integer.toString((reg.regArr[rsDec].getValue() + (immDec / 4)));
                l = r;
                p = 0;
                targetAddress2 = targetAddress;
            }
            else if(opcodeDec == 4) { //beq
                s= "N/A";
                p = 1;
                l = Integer.toString((reg.regArr[rsDec].getValue() - reg.regArr[rtDec].getValue()));
                if(shifted.charAt(0) == '1')
                    targetAddress2 = BinToDecStr(shifted) + pcPlusFour;
                else targetAddress2 = BinToDec(shifted) + pcPlusFour;
                
                r = l;
            }
            else {
                
                s = "N/A";
                p = 0;
                targetAddress2 = targetAddress;
                
                if(opcodeDec == 13){ //ori
                    r = Integer.toString(reg.regArr[rsDec].getValue() | immDec );
                    l = Integer.toString(reg.regArr[rsDec].getValue() | immDec );
                }
                else if (opcodeDec == 8){ //addi
                    r = Integer.toString(reg.regArr[rsDec].getValue() + immDec);
                    l = Integer.toString(reg.regArr[rsDec].getValue() + immDec);
                }
                else if (opcodeDec == 12){ //andi
                    r = Integer.toString(reg.regArr[rsDec].getValue() & immDec);
                    l = Integer.toString(reg.regArr[rsDec].getValue() & immDec);
                }
            }
            
            System.out.println("• Datapath: "
                        + "\no PC starting address: " + pcStart
                        + "\no PC+4 adder output: " + pcPlusFour
                        + "\no Instruction memory output: " + String.format("%06d", opcode) + " " + String.format("%05d", rs) + " " + String.format("%05d", rt) + " " + ImmStr
                        + "\no opcode field of instruction: " + String.format("%06d", opcode)
                        + "\no rs field of instruction: " + String.format("%05d", rs)
                        + "\no rt field of instruction: " + String.format("%05d", rt)
                        + "\no Immediate/offset value field of instruction: " + ImmStr
                        + "\no RegDestination Mux output: " + String.format("%05d", rt)
                        + "\no Sign-extender output: " + least16Bits
                        + "\no Shift-2 output: " + shifted
                        + "\no Target address adder: " + targetAddress2
                        + "\no Read data 1: " + reg.regArr[rsDec].getValue()
                        + "\no Read data 2: " + reg.regArr[rtDec].getValue()
                        + "\no ALU second input: " + least16Bits
                        + "\no ALU output: " + l
                        + "\no Zero flag: " + p
                        + "\no Data memory output: " + s
                        + "\no MemtoReg Mux output: " + r
                        + "\no AND gate output: " + p
                        + "\n\n• Control signals"
                        + "\no RegDest: " + RegDest
                        + "\no Jump: " + jump
                        + "\no Branch: " + Branch
                        + "\no MemRead: " + MemRead
                        + "\no MemtoReg: " + MemToReg
                        + "\no ALUOp: " + String.format("%02d", ALUOp)
                        + "\no MemWrite: " + MemWrite
                        + "\no ALUSrc: " + ALUSrc
                        + "\no RegWrite: " + RegWrite
                        + "\no ALU control output: " + String.format("%04d", ALUCtrl));
        } 
    }
  
    static String DecToBinStr(int d){
            String x = "";
            
            if(d<0){
                String xy = Integer.toString(d , 2);
                x = "" + twosComplement(xy);
                
            }
            else{
                x = Integer.toString(d , 2);
                while(x.length()<16)
                    x = '0' + x;
            }
            return x;
    }
	
    static int DecToBin(int d){
        return Integer.parseInt(Integer.toString(d,2));
    }
    
    static int BinToDec(String b){
        int x = 0;
        int i = 0;
//        if(b.charAt(0) == '1'){
//           String y = twosComplement(b);
//           x = -1 * Integer.parseInt(y,2);
//        }
//        else{
            x = Integer.parseInt(b , 2);
//        }
        return x;
   }
    
    static int BinToDecStr(String b){
        int x = 0, i = 0;
        if(b.charAt(0) == '1'){
           String y = twosComplement(b);
           x = -1 * Integer.parseInt(y,2);
        }
        else{
            x = Integer.parseInt(b , 2);
        }
        return x;
   }
    
    static void printRegValues() {
        System.out.println("\nDo you want to print the values in all the registers? ");
        System.out.println("[1] Yes\n[2] No ");
        int k = sc.nextInt();
        if(k == 1){
            System.out.println("\n• Values in Registers ");
            for(int i = 0 ; i < 32 ; i++)
                System.out.println("o " + reg.regArr[i].getName() + " = " + reg.regArr[i].getValue());
            
        }
    }
 
    static void printMem() {
        System.out.println("\nDo you want to print a value stored in the memory? ");
        System.out.println("[1] Yes\n[2] No ");
        int k = sc.nextInt();
        while(k == 1){
            System.out.println("\nSelect the register that holds the array base address: ");
            printReg();
            int x = sc.nextInt();
            System.out.println("\nEnter the index to be printed: ");
            int y = sc.nextInt();
            System.out.println("\nValue assigned: " + memory.mem[reg.regArr[x].getValue() + y*4 ].getValue());
            
            System.out.println("\nDo you want to print any other values stored? ");
            System.out.println("[1] Yes\n[2] No");
            k = sc.nextInt();
        }
    }

    static void storeCode(String str) {
        Code.add(str);
    }
    
    static void printCode(){
        System.out.println("\nMIPS Code:");
        for(int i = 0; i < Code.size(); i++) {
            System.out.println(Code.get(i));
        }
    }
	
    static String twosComplement(String bin) {
        String twos = "", ones = "", two = "", temp = "";
        
        if(bin.charAt(0) == '-')
            bin = bin.substring(1);
        
        for (int i = 0; i < bin.length(); i++) {
            ones += flip(bin.charAt(i));
        }
        int number0 = Integer.parseInt(ones, 2);
        StringBuilder builder = new StringBuilder(ones);
        boolean b = false;
        
        for (int i = ones.length() - 1; i >= 0; i--) {
            if (ones.charAt(i) == '1') {
                builder.setCharAt(i, '0');
            } else {
                builder.setCharAt(i, '1');
                b = true;
                break;
            }
        }
        temp = ones;
        if (!b)
            builder.append("1", 0, (ones.length() -1) );
        
        twos = builder.toString();
        
        two = twos;
        while(two.length() < 16)
            two = '1' + two;

        return two;
    }

    static char flip(char c) { // flips '0' into '1' and '1' into '0'
        return (c == '0') ? '1' : '0';
    }

    static void credits() {
    System.out.println();
    System.out.println("######## Credits ########\n## MIPS SIMULATOR v1.0 ##\n## Created by:\t       ##\n## Mohamed El Ghamry   ##\n## Mostafa Hazem       ##\n## ~(iONEX Group)~     ##\n#########################");
    }
    
}
