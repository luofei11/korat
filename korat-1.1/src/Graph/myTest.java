package graph;
import java.util.Arrays;

import korat.config.ConfigLoader;
import korat.config.ConfigManager;
import korat.testing.impl.CannotFindClassUnderTest;
import korat.testing.impl.CannotFindFinitizationException;
import korat.testing.impl.CannotFindPredicateException;
import korat.testing.impl.CannotInvokeFinitizationException;
import korat.testing.impl.CannotInvokePredicateException;
import korat.testing.impl.KoratMethodException;
import korat.testing.impl.KoratTestException;
import korat.testing.impl.TestCradle;

public class myTest {
    
    
	public static void main(String[] args) {
		int i;
		String[] myArgs = {"--class","graph.graph","--args","1"};
		String[] parameters = args[3].split(",");
		for(i = 0; i < parameters.length; i++)
		{
			myArgs[3] = parameters[i];
			doTest(myArgs);
		}
	}
    
	public static void doTest(String[] args){
		TestCradle testCradle = TestCradle.getInstance();
        ConfigManager config = ConfigManager.getInstance();
        config.parseCmdLine(args);
        
        System.out.print("\nStart of Korat Execution for " + config.className
                         + " (" + config.predicate + ", ");
        System.out.println(Arrays.toString(config.args) + ")\n");
        
        try {
            
            long t1 = System.currentTimeMillis();
            testCradle.start(config.className, config.args);
            long t2 = System.currentTimeMillis();
            
            long dt1 = t2 - t1;
            System.out.println("\nEnd of Korat Execution");
            System.out.println("Overall time: " + dt1 / 1000.0 + " s.");
            
        }catch (CannotFindClassUnderTest e) {
            
            System.err.println("!!! Korat cannot find class under test:");
            System.err.println("        <class_name> = " + e.getFullClassName());
            System.err.println("    Use -"
                               + ConfigLoader.CLZ.getSwitches()
                               + " switch to provide full class name of the class under test.");
            
        }catch (KoratMethodException e) {
        	String ClsName = e.getCls().getName();
        	String MethodName = e.getMethodName();
            if(e.getClass() == CannotFindFinitizationException.class)
            {
                System.err.println("!!! Korat cannot find finitization method for the class under test:");
                System.err.println("        <class> = " + ClsName);
                System.err.println("        <finitization> = " + MethodName);
                System.err.println("    Use -"
                                   + ConfigLoader.FINITIZATION.getSwitches()
                                   + " switch to provide custom finitization method name.");
            }
            else if(e.getClass() == CannotFindPredicateException.class)
            {
                System.err.println("!!! Korat cannot find predicate method for the class under test:");
                System.err.println("        <class> = " + ClsName);
                System.err.println("        <predicate> = " + MethodName);
                System.err.println("    Use -"
                                   + ConfigLoader.PREDICATE.getSwitches()
                                   + " switch to provide custom predicate method name.");
            }
            else if(e.getClass() == CannotInvokeFinitizationException.class)
            {
                System.err.println("!!! Korat cannot invoke finitization method:");
                System.err.println("        <class> = " + ClsName);
                System.err.println("        <finitization> = " + MethodName);
            }
            else if(e.getClass() == CannotInvokePredicateException.class)
            {
                System.err.println("!!! Korat cannot invoke predicate method:");
                System.err.println("      <class> = " + ClsName);
                System.err.println("      <predicate> = " + MethodName);
            }
            System.err.println();
            System.err.println("    Stack trace:");
            e.printStackTrace(System.err);
        }catch(KoratTestException e){
        	System.err.println("!!! A korat exception occured:");
            System.err.println();
            System.err.println("    Stack trace:");
            e.printStackTrace(System.err);
        }
    }
}
