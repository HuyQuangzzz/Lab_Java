package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //load the spring configuration file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //retrieve bean from spring container
        Product propertyProduct = context.getBean("myPropertyProduct", Product.class);
        Product alphaPropertyProduct = context.getBean("myPropertyProduct", Product.class);

        Product constructorArgProduct = context.getBean("myConstructorArgProduct", Product.class);
        Product alphaConstructorArgProduct = context.getBean("myConstructorArgProduct", Product.class);

        Product singletonProduct = context.getBean("mySingletonProduct", Product.class);
        Product alphaSingletonProduct = context.getBean("mySingletonProduct", Product.class);

        //call methods on the bean
        System.out.println(propertyProduct.getId());
        System.out.println(propertyProduct.getName());
        System.out.println(propertyProduct.getPrice());
        System.out.println(propertyProduct.getDescription());
        System.out.println();

        System.out.println(constructorArgProduct.getId());
        System.out.println(constructorArgProduct.getName());
        System.out.println(constructorArgProduct.getPrice());
        System.out.println(constructorArgProduct.getDescription());
        System.out.println();

        System.out.println(singletonProduct.getId());
        System.out.println(singletonProduct.getName());
        System.out.println(singletonProduct.getPrice());
        System.out.println(singletonProduct.getDescription());
        System.out.println();

        boolean result1 = propertyProduct == alphaPropertyProduct;
        boolean result2 = constructorArgProduct == alphaConstructorArgProduct;
        boolean result3 = singletonProduct == alphaSingletonProduct;

        System.out.println("First bean: Pointing to the same instance " + result1);
        System.out.println("Second bean: Pointing to the same instance " + result2);
        System.out.println("Third bean: Pointing to the same instance " + result3);

        //close the context
        context.close();
    }
}
