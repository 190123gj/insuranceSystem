package com.born.insurance.webui.control;





/**

 * MenuException is an exception that extends the standard Exception.

 * This is thrown by the all the classes in the Menu package

 *

 */

public class MenuException extends Exception

{

    private Exception wrappedEx;

    /**

     * Constructor

     * @param str    a string that explains what the exception condition is

     */

    public MenuException (String str, Exception e) {

        super(str);

        wrappedEx = e;

    }



	/** Default constructor. Takes no arguments */

    public MenuException () {

        super();

    }



    public Exception getWrappedEx() {

        return wrappedEx;

    }

}