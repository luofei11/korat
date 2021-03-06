package korat;
import java.util.Arrays;

import korat.config.ConfigLoader;
import korat.config.ConfigManager;
import korat.testing.impl.CannotFindClassUnderTest;
import korat.testing.impl.CannotFindFinitizationException;
import korat.testing.impl.CannotFindPredicateException;
import korat.testing.impl.CannotInvokeFinitizationException;
import korat.testing.impl.CannotInvokePredicateException;
import korat.testing.impl.KoratTestException;
import korat.testing.impl.TestCradle;

public class MyTest {
	
	
	public static void main(String[] args) {
		int i;
		String[] MyArgs = {"--class","korat.examples.Graph.Graph","--args","1"};
		String[] Parameters = args[3].split(",");
		for(i = 0; i < Parameters.length - 1; i++)
		{
			MyArgs[3] = Parameters[i];
			DoTest(MyArgs);
		}
	}
	
	public static void DoTest(String[] args){
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

        } catch (CannotFindClassUnderTest e) {
            
            System.err.println("!!! Korat cannot find class under test:");
            System.err.println("        <class_name> = " + e.getFullClassName());
            System.err.println("    Use -"
                    + ConfigLoader.CLZ.getSwitches()
                    + " switch to provide full class name of the class under test.");
            
        } catch (CannotFindFinitizationException e) {
            
            System.err.println("!!! Korat cannot find finitization method for the class under test:");
            System.err.println("        <class> = " + e.getCls().getName());
            System.err.println("        <finitization> = " + e.getMethodName());
            System.err.println("    Use -"
                    + ConfigLoader.FINITIZATION.getSwitches()
                    + " switch to provide custom finitization method name.");
            
        } catch (CannotFindPredicateException e) {
            
            System.err.println("!!! Korat cannot find predicate method for the class under test:");
            System.err.println("        <class> = " + e.getCls().getName());
            System.err.println("        <predicate> = " + e.getMethodName());
            System.err.println("    Use -"
                    + ConfigLoader.PREDICATE.getSwitches()
                    + " switch to provide custom predicate method name.");
            
        } catch (CannotInvokeFinitizationException e) {
            
            System.err.println("!!! Korat cannot invoke finitization method:");
            System.err.println("        <class> = " + e.getCls().getName());
            System.err.println("        <finitization> = " + e.getMethodName());
            System.err.println();
            System.err.println("    Stack trace:");
            e.printStackTrace(System.err);
            
        } catch (CannotInvokePredicateException e) {
            
            System.err.println("!!! Korat cannot invoke predicate method:");
            System.err.println("      <class> = " + e.getCls().getName());
            System.err.println("      <predicate> = " + e.getMethodName());
            System.err.println();
            System.err.println("    Stack trace:");
            e.printStackTrace(System.err);
            
        } catch (KoratTestException e) {
            
            System.err.println("!!! A korat exception occured:");
            System.err.println();
            System.err.println("    Stack trace:");
            e.printStackTrace(System.err);
            
        }

    }
}
