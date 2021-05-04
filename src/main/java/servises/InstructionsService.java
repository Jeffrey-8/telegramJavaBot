package servises;

import models.Instruction;

import java.util.ArrayList;

public class InstructionsService {
    ArrayList<Instruction> instructionList = new ArrayList<>();
    public InstructionsService(){
        setupInstructionsList();
    }

    public String getInstructionMessage(){
        StringBuffer str = new StringBuffer();
        str.append("Выберите желаемую инструкцию:");
        str.append("\n");
        int count = 0;
        for (Instruction instr: instructionList) {
            str.append(count+1);
            str.append(". ");
            str.append(instr.getName());
            str.append("\n");
            count++;
        }
        return str.toString();
    }

    public String getInstructionPath(int index){
//        return instructionList.get(index-1).getFilepath();
//        FIXME: Вставить определение пути файлов на сервере и понять как отправлять локальные файлы
//         Пока пусть будет хлебопес
        return "https://e7.pngegg.com/pngimages/998/380/png-clipart-shiba-inu-dogecoin-gif-internet-meme-egypt-dog-dog-like-mammal-dog-breed.png";
    }

    private void setupInstructionsList(){
        Instruction inst1 = new Instruction();
        inst1.setName("Как подать заявление на отпуск");
        instructionList.add(inst1);
        Instruction inst2 = new Instruction();
        inst2.setName("Как подать завяление на больничный");
        instructionList.add(inst2);
    }
    public boolean isNumberCorrect(String string) {
        try {
            int i = Integer.parseInt(string);
            return (i > 0)&&(i<=instructionList.size());
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
