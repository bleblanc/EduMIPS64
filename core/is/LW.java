/*
 * LW.java
 *
 * 20th may 2006
 * Instruction LW of the MIPS64 Instruction Set
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

package edumips64.core.is;
import edumips64.core.*;
import edumips64.utils.*;


/** <pre>
 *  Format:        LW rt, offset(base)
 *  Description:   rt = memory[base+offset]
 *                 To load a word from memory as a signed value
  * </pre>
 * @author Trubia Massimo, Russo Daniele
 */
class LW extends Loading
{
    final String OPCODE_VALUE="100011";
    public LW()
    {
    	super.OPCODE_VALUE = OPCODE_VALUE;
        this.name="LW";
    }
    
    public  void MEM() throws IrregularStringOfBitsException,MemoryElementNotFoundException, AddressErrorException, IrregularWriteOperationException
    { 
        //restoring the address from the temporary register
        long address=TR[OFFSET_PLUS_BASE].getValue();
        //For the trace file
        Dinero din=Dinero.getInstance();
        din.Load(Converter.binToHex(Converter.positiveIntToBin(64,address)),4);
        MemoryElement memEl = memory.getCell((int)address);
        try
        {
            //reading from the memory element and saving values on LMD register
            TR[LMD_REGISTER].writeWord(memEl.readWord((int)(address%8)));
		if(enableForwarding)
		{
			doWB();
		}
        }
	catch(NotAlignException er)
	{
	    throw new AddressErrorException();
	}
    }        

}
