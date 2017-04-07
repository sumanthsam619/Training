/* My custom exception class. */
class ACustomException extends Exception
{
  public ACustomException(String message)
  {
    super(message);
  }
}
/*class to demonstrate custom exception.*/
class UseException
{
  public String getBar(int i)
  throws ACustomException
  {
    if (i == 0)
    {
      // throw our custom exception
      throw new ACustomException("Anything but zero ...");
    }
    else
    {
      return "Thanks";
    }
  }
}


/* A class to test (throw) the custom exception we've created.  */
public class CustomException
{
  public static void main(String[] args)
  {
    // create a new foo
    UseException foo = new UseException();
    
    try
    {
      // intentionally throw our custom exception by
      // calling getBar with a zero
      String bar = foo.getBar(0);
    }
    catch (ACustomException e)
    {
      // print the stack trace
      e.printStackTrace();
    }
  }
}