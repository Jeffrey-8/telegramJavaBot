package test;

//import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class mainTest {
    public static void main(String[] args){
        String testString="Фонин Роман";
        System.out.println(testString);

//        Message message=new Message();

        String[] names=LNAndFMOrOnlyLN(testString);

        for (String nams:names) {
        System.out.println(nams);
        }

    }

    public static String[] LNAndFMOrOnlyLN(String message) {
        String msg = message;//.getText();
        String[] result = msg.split(" ");
        return result;
    }
}
