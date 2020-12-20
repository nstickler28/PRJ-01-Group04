package model;

import numeric.Binary;
import numeric.Hex;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CPURegisters {
	/**
	 * CPU registers
	 */
	private boolean statusBitN;
	private boolean statusBitZ;
	private boolean statusBitV;
	private boolean statusBitC;
	private Hex accumulator = new Hex(0);
	private Hex indexRegister = new Hex(0);
	private Hex programCounter = new Hex(0);
	private Hex stackPoint = new Hex(0xfbcf);
	private int instructionRegister;
	private Binary instructionSpecifier = new Binary(0);
	private Hex operandSpecifier = new Hex(0);


	/**
	 * property change support
	 */
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public boolean isStatusBitN() {
		return statusBitN;
	}

	public void setStatusBitN(boolean statusBitN) {
		boolean oldValue = this.statusBitN;
		this.statusBitN = statusBitN;
		pcs.firePropertyChange("statusBitN", oldValue, this.statusBitN);
	}

	public boolean isStatusBitZ() {
		return statusBitZ;
	}

	public void setStatusBitZ(boolean statusBitZ) {
		boolean oldValue = this.statusBitZ;
		this.statusBitZ = statusBitZ;
		pcs.firePropertyChange("statusBitZ", oldValue, this.statusBitZ);
	}

	public boolean isStatusBitV() {
		return statusBitV;
	}

	public void setStatusBitV(boolean statusBitV) {
		boolean oldValue = this.statusBitV;
		this.statusBitV = statusBitV;
		pcs.firePropertyChange("statusBitV", oldValue, this.statusBitV);
	}

	public boolean isStatusBitC() {
		return statusBitC;
	}

	public void setStatusBitC(boolean statusBitC) {
		boolean oldValue = this.statusBitC;
		this.statusBitC = statusBitC;
		pcs.firePropertyChange("statusBitC", oldValue, this.statusBitC);
	}

	public int getAccumulator() {
		return accumulator.getValue();
	}

	public void setAccumulator(int accumulator) {
		int oldValue = this.accumulator.getValue();
		this.accumulator.setValue(accumulator);
		pcs.firePropertyChange("accumulator", oldValue, this.accumulator.getValue());
	}

	public int getIndexRegister() {
		return indexRegister.getValue();
	}

	public void setIndexRegister(int indexRegister) {
		int oldValue = this.indexRegister.getValue();
		this.indexRegister.setValue(indexRegister);
		pcs.firePropertyChange("indexRegister", oldValue, this.indexRegister.getValue());
	}

	public int getProgramCounter() {
		return programCounter.getValue();
	}

	public void setProgramCounter(int programCounter) {
		int oldValue = this.programCounter.getValue();
		this.programCounter.setValue(programCounter);
		pcs.firePropertyChange("programCounter", oldValue, this.programCounter.getValue());
	}

	public int getStackPoint() {
		return stackPoint.getValue();
	}

	public void setStackPoint(int stackPoint) {
		int oldValue = this.stackPoint.getValue();
		this.stackPoint.setValue(stackPoint);
		pcs.firePropertyChange("stackPoint", oldValue, this.stackPoint.getValue());
	}

	public int getInstructionRegister() {
		return instructionRegister;
	}

	public void setInstructionRegister(int instructionRegister) {
		int oldValue = this.instructionRegister;
		this.instructionRegister = instructionRegister;
		pcs.firePropertyChange("instructionRegister", oldValue, this.instructionRegister);
		setInstructionSpecifier((instructionRegister >> 16) & 0xff);
		setOperandSpecifier(instructionRegister & 0xffff);
	}

	public int getInstructionSpecifier() {
		return instructionSpecifier.getValue();
	}

	public void setInstructionSpecifier(int instructionSpecifier) {
		int oldValue = this.instructionSpecifier.getValue();
		this.instructionSpecifier.setValue(instructionSpecifier);
		pcs.firePropertyChange("instructionSpecifier", oldValue, this.instructionSpecifier.getValue());
	}

	public int getOperandSpecifier() {
		return operandSpecifier.getValue();
	}

	public void setOperandSpecifier(int operandSpecifier) {
		int oldValue = this.operandSpecifier.getValue();
		this.operandSpecifier.setValue(operandSpecifier);
		pcs.firePropertyChange("operandSpecifier", oldValue, this.operandSpecifier.getValue());
	}
}
