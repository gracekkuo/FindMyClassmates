package com.example.findmyclassmates;
import org.junit.Test;
import static org.junit.Assert.*;
import com.example.findmyclassmates.activities.SignupActivity;
import com.example.findmyclassmates.activities.Checker;


public class SignupWhiteBoxTest {
    private final Checker checker = new Checker ();

    @Test
    public void email_test (){
        String badEmail = "helloworld";
        assertEquals(false, checker.isValidEmail(badEmail));
        String badEmailType = "helloworld@gmail.com";
        assertEquals(false, checker.isUSCValidEmail(badEmailType));
        String goodEmail = "helloworld@usc.edu";
        assertEquals(true, checker.isValidEmail(goodEmail));
    }
    @Test
    public void password_test (){
        String password = "helloworld";
        String reenter = "lskd";
        assertEquals(false, checker.isValidPasswordReenter(password, reenter));
        reenter = "helloworld";
        assertEquals(true, checker.isValidPasswordReenter(password, reenter));

    }

    @Test
    public void student_id_test(){
        String studentId = "102938091283098123";
        assertEquals(false, checker.isValidStudentId(studentId));
        studentId = "1234567890";
        assertEquals(true, checker.isValidStudentId(studentId));
    }
}
