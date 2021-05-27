// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.


// need to add R0, R1 times
// or vice-versa, quicker to add the larger number to each other a smaller number of times


    @i          // i=current iteration, initialize at 1
    M=0
    @R2
    M=0         // initialize output at 0

    @R0
    D=M         // D=R0
    @R1
    D=D-M       // D=R0-R1

    @BRANCH0
    D;JGE       // if (R0-R1>=0, goto BRANCH0)
    @BRANCH1
    D;JLT       // if (R0-R1<0, goto BRANCH1)


    // addby - what we are going to add by (larger of the two)
    // total - initialized at larger value
    // adds - total addition operations (smaller of two)
(BRANCH0)
    @R0
    D=M         // D=R0
    @addby
    M=D         // addby=R0
    @R1
    D=M         // D=R1
    @adds
    M=D         // adds=R1

    @LOOP
    0;JMP       // jump to the loop now that we know step and base total


(BRANCH1)
    @R1
    D=M         // D=R1
    @addby
    M=D         // addby=R1
    @R0
    D=M         // D=R0
    @adds
    M=D         // adds=R0

    @LOOP
    0;JMP       // jump to the loop now that we know step and base total

(LOOP)
    @adds
    D=M         // D=adds
    @i
    D=D-M       // D=adds-i
    @END
    D;JLE       // if (adds-i)<=0 goto END
    @addby
    D=M         // D=addby
    @R2
    M=D+M       // R2=R2+addby
    @i
    M=M+1       // i+=1
    @LOOP
    0;JMP       // goto LOOP
    


(END)
    @END
    0;JMP       // infinite loop



