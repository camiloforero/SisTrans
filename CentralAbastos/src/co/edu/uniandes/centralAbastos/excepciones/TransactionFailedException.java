package co.edu.uniandes.centralAbastos.excepciones;

public class TransactionFailedException extends Exception
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7003039007235626154L;
	
	
	public TransactionFailedException(String string, Throwable e) 
	{
		super(string, e);
	}
	

}
