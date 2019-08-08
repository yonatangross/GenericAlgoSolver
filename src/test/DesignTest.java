package test;

import java.lang.reflect.Field;

public class DesignTest {


    Class<?> serverInteface;
    Class<?> serverClass;
    Class<?> clientHandlerInterface;
    Class<?> clientHandlerClass;
    Class<?> cacheManagerInterface;
    Class<?> cacheManagerClass;
    Class<?> solverInterface;
    Class<?> solverClass;


    public void setServerInteface(Class<?> serverInteface) {
        this.serverInteface = serverInteface;
    }

    public void setServerClass(Class<?> serverClass) {
        this.serverClass = serverClass;
    }

    public void setClientHandlerInterface(Class<?> clientHandlerInterface) {
        this.clientHandlerInterface = clientHandlerInterface;
    }

    public void setClientHandlerClass(Class<?> clientHandlerClass) {
        this.clientHandlerClass = clientHandlerClass;
    }

    public void setCacheManagerInterface(Class<?> cacheManagerInterface) {
        this.cacheManagerInterface = cacheManagerInterface;
    }

    public void setCacheManagerClass(Class<?> cacheManagerClass) {
        this.cacheManagerClass = cacheManagerClass;
    }

    public void setSolverInterface(Class<?> solverInterface) {
        this.solverInterface = solverInterface;
    }

    public void setSolverClass(Class<?> solverClass) {
        this.solverClass = solverClass;
    }


    private void testImp(Class<?> inter, Class<?> imp) {
        if (!inter.isInterface())
            System.out.println("Your " + inter + " is not an interface (-5)");
        if (!(imp.getInterfaces().length == 1 && imp.getInterfaces()[0].equals(inter)))
            System.out.println("Your " + imp + " does not implement " + inter + " or it has more than one interface (-5)");

    }

    public void testDesign() {
        testImp(serverInteface, serverClass);
        testImp(clientHandlerInterface, clientHandlerClass);
        testImp(cacheManagerInterface, cacheManagerClass);
        //testImp(solverInterface,solverClass);
        int i = 0;
        boolean ar[] = {false, false};
        for (Field f : clientHandlerClass.getDeclaredFields()) {
            if (f.getType().equals(solverInterface) || f.getType().equals(cacheManagerInterface)) {
                ar[i] = true;
                i++;
            }
        }

        if (!ar[0] || !ar[1])
            System.out.println("Your ClientHandler does not contain a Solver or a CacheManager (-10)");

    }

}
