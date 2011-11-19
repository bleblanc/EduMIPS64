/*
 * ALUInstructions.java
 *
 * 5th may 2006
 * Subgroup of the MIPS64 Instruction Set
 * (c) 2006 EduMips64 project - Trubia Massimo, Russo Daniele
 *
 * This file is part of the EduMIPS64 project, and is released under the GNU
 * General Public License.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/** This is the base class for all the ALU instructions
 *
 * @author Trubia Massimo, Russo Daniele
 */
package edumips64.core.is;
import edumips64.core.*;
import edumips64.utils.*;

public abstract class ALUInstructions extends Instruction {
    protected static CPU cpu=CPU.getInstance();
    public void IF()
    {
        Dinero din=Dinero.getInstance();
        try
        {
            din.IF(Converter.binToHex(Converter.intToBin(64,cpu.getLastPC().getValue())));
        }
        catch(IrregularStringOfBitsException e)
        {
            e.printStackTrace();
        }
    }
    public abstract void ID() throws RAWException,IrregularWriteOperationException,IrregularStringOfBitsException;
    public abstract void EX() throws IrregularStringOfBitsException,IntegerOverflowException,TwosComplementSumException,IrregularWriteOperationException,DivisionByZeroException;
    public abstract void MEM() throws IrregularStringOfBitsException,MemoryElementNotFoundException;
    public abstract void WB() throws IrregularStringOfBitsException;
    public abstract void pack() throws IrregularStringOfBitsException;

 
}








