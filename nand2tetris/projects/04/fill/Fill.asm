// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

@24576
D=A
@max
M=D         // max=24576
@16384
D=A
@min        // max=16384
M=D
// @pixel
// M=D         // pixel=16384

(LOOP)
    @i
    M=0         // M=0
    @KBD
    D=M         // D=KBD (RAM[24576])
    @BLACKEN
    D;JNE       // blacken screen if KBD!=0
    @WHITEN
    D;JEQ       // whiten screen if KBD==0

(BLACKEN)
    @min
    D=M         // D=min
    @i
    D=D+M       // D=min+i
    @max
    D=M-D       // D=max-(min+i)
    @LOOP
    D;JEQ       // jump back to loop if all pixels blackened
    
    
    @min
    D=M         // D=min
    @i
    A=D+M       // A=min+i
    M=-1        // RAM[(min+i)] = -1
    @i          
    M=M+1       // i+=1

    @BLACKEN
    0;JMP


(WHITEN)
    @min
    D=M         // D=min
    @i
    D=D+M       // D=min+i
    @max
    D=M-D       // D=max-(min+i)
    @LOOP
    D;JEQ       // jump back to loop if all pixels blackened

    @min
    D=M         // D=min
    @i
    A=D+M       // A=min+i
    M=0         // RAM[(min+i)] = 0
    @i          
    M=M+1       // i+=1

    @WHITEN
    0;JMP



