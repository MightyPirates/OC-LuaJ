package lua;

import lua.io.Closure;
import lua.value.LString;
import lua.value.LValue;

public interface VM {

	// ================ interfaces for performing calls
	
	/** Prepare the VM stack for a new call with arguments to be pushed
	 */
	public void newCall();
	
	/** Push an argument or return value onto the stack 
	 */
	public void push( LValue value );

	/** Push an int argument or return value onto the stack 
	 */
	public void push( int value );

	/** Push a double argument or return value onto the stack 
	 */
	public void push( double value );

	/** Push a boolean argument or return value onto the stack 
	 */
	public void push( boolean value );

	/** Push a String argument or return value onto the stack 
	 */
	public void push( String value );

	/**
	 * Create a call frame for a call that has been set up on
	 * the stack. The first value on the stack must be a Closure,
	 * and subsequent values are arguments to the closure.
	 */ 
	public void prepStackCall();
	
	/**
	 * Execute bytecodes until the current call completes
	 * or the vm yields.
	 */
	public void execute();

	/**
	 * Put the closure on the stack with arguments, 
	 * then perform the call.   Leave return values 
	 * on the stack for later querying. 
	 * 
	 * @param c
	 * @param values
	 */
	public void doCall(Closure c, LValue[] values);	
	
	/**
	 * Set the number of results that are expected from the function being called.
	 * (This should be called before prepStackCall)
	 */
	public void setExpectedResultCount( int nresults );
	
	/**
	 * Returns the number of results that are expected by the calling function,
	 * or -1 if the calling function can accept a variable number of results.
	 */
	public int getExpectedResultCount();
	
	/**
	 * Adjust the stack to contain the expected number of results by adjusting
	 * the top.
	 */
	public void adjustResults();
	
	// ================ interfaces for getting arguments when called
	
	/**
	 * Get the number of argumnets supplied in the call.
	 */
	public int getArgCount();

	/**
	 * Get the index-th argument supplied, or NIL if fewer than index were supplied.
	 * @param index
	 * @return
	 */
	public LValue getArg(int index);

	/**
	 * Get the index-th argument as an int value, or 0 if fewer than index arguments were supplied.
	 * @param index
	 * @return
	 */
	public int getArgAsInt( int index );

	/**
	 * Get the index-th argument as a double value, or 0 if fewer than index arguments were supplied.
	 * @param index
	 * @return
	 */
	public double getArgAsDouble( int index );

	/**
	 * Get the index-th argument as a boolean value, or false if fewer than index arguments were supplied.
	 * @param index
	 * @return
	 */
	public boolean getArgAsBoolean( int index );

	/**
	 * Get the index-th argument as a String value, or "" if fewer than index arguments were supplied.
	 * @param index
	 * @return
	 */
	public String getArgAsString( int index );

	/**
	 * Get the index-th argument as an LString value, or "" if fewer than index arguments were supplied.
	 * @param index
	 * @return
	 */
	public LString getArgAsLuaString( int index );

	/** Set top to base in preparation for pushing return values.
	 * Can be used when returning no values.
	 * 
	 * Once this is called, calls to getArg() are undefined.
	 * 
	 * @see push() to push additional results once result is reset. 
	 */
	public void setResult();
	
	/** Convenience utility to set val to stack[base] and top to base + 1 
	 * in preparation for returning one value 
	 * 
	 * Once this is called, calls to getArg() are undefined.
	 * 
	 * @param val value to provide as the only result.
	 */
	public void setResult(LValue val);

}