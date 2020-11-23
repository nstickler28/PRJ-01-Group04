import javax.swing.*;

/**
 * 
 * @author Chun Kit Kwong
 * @author Nathan Stickler
 * @author Loren Mendoza
 * @author Wynn Siripanich
 * 
 * This class links the input and output device, 
 * reads a byte from the input device when you need to read,
 * and adds a byte to the output device when you output.
 *
 */

public class IO {
    int position = 0;   // input device cursor
    JTextArea input;    // input device
    JTextArea output;   // output device

    // set up input device
    public void setInputDevice(JTextArea inputDevice){
        input = inputDevice;
    }

    // set up output device
    public void setOutputDevice(JTextArea outputDevice){
        output = outputDevice;
    }

    // read one char from input device
    public byte readByteFromInput() {
        return (byte) input.getText().charAt(position++);
    }

    // send one char to output device
    public void putByte(byte content) {
        output.append(String.valueOf((char) content));
    }

    // reset input device cursor
    public void reset() {
        position = 0;
    }

	public void initialize(String text, JTextArea outputTextArea) {
		// TODO Auto-generated method stub
		
	}
}
