package vn.edu.tdtu.javatech.Lab6_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@ComponentScan
@Configuration
public class Application
{
    private static ApplicationContext applicationContext;
    public static void main( String[] args )
    {
        applicationContext = new AnnotationConfigApplicationContext(Application.class);
        TextEditor textEditor = (TextEditor) applicationContext.getBean("textEditor");
        textEditor.input("Spring is coming!!");
        try {
            textEditor.save("spring.txt");
        } catch (IOException e) {
            System.out.print(e.getStackTrace());
        }
    }
}
