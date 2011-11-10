package traffic.endtoend;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import traffic.TrafficModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class MyGuiceBerryRule implements MethodRule
{
   @Override
   public Statement apply(final Statement base, final FrameworkMethod method, final Object target)
   {
      return new Statement()
      {
         @Override
         public void evaluate() throws Throwable
         {
            final Injector injector = Guice.createInjector(new TrafficModule());

            injector.injectMembers(target);

            base.evaluate();
         }
      };

   }
}