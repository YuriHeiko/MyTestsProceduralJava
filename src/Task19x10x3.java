import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
public class Task19x10x3 {
    public static List<PointEr> list = new ArrayList<>(); //для поиска закрывающего тега
    public static int count = 0; //для поиска закрывающего тега
    public static void main(String[] args) throws IOException{
        System.out.println("Test 1");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader file = new BufferedReader(new FileReader(reader.readLine()));
        reader.close();
        String findStrStart = "<" + args[0]; //открывающий тег
        String findStrEnd = "</" + args[0] + ">"; //закрывающий тег
        StringBuilder builder = new StringBuilder(); //для формирования строки из файла
        Map<Integer, Boolean> map = new TreeMap<>(); //для автосортировки позиций открывающих и закрывающих тегов
        while (file.ready())  //считывание файла
        {
            String line = file.readLine();
            builder.append(line);
        }
        file.close();
        String cut = builder.toString(); //для работы с текстом файла
//        builder.append(cut.replaceAll("\n", "")); //удаление каретки перевода строки
//        builder.delete(0, builder.length());
//        cut = builder.toString();
        int pos = 0;
        while (true) //отбираем начала
        {
            if (cut.contains(findStrStart))
            {
                map.put(cut.indexOf(findStrStart) + pos, true);
                pos += cut.indexOf(findStrStart) + findStrStart.length();
                cut = cut.substring(cut.indexOf(findStrStart) + findStrStart.length(), cut.length());
            }
            else break;
        }
        cut = builder.toString();
        pos = 0;
        while (true) //отбираем окончания
        {
            if (cut.contains(findStrEnd))
            {
                map.put(cut.indexOf(findStrEnd) + pos, false);
                pos += cut.indexOf(findStrEnd) + findStrEnd.length();
                cut = cut.substring(cut.indexOf(findStrEnd) + findStrEnd.length(), cut.length());
            }
            else break;
        }
        //вывод строк
        cut = builder.toString();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isStart)
            {
                count = 1;
                System.out.println(cut.substring(list.get(i).getPosition(), giveEnd(i+1) + findStrEnd.length()));
            }
        }
    }
    public static int giveEnd(int listPosition) {
        if (!list.get(listPosition).isStart) {
            count--;
            if (count == 0) return list.get(listPosition).getPosition();
            else return giveEnd(listPosition + 1);
        }
        else {
            count++;
            return giveEnd(listPosition + 1);
        }
    }
    public static class PointEr {
        int position;
        boolean isStart;
        public PointEr(int position, boolean isStart)
        {
            this.position = position;
            this.isStart = isStart;
        }
        public int getPosition()
        {
            return position;
        }
        public boolean isStart()
        {
            return isStart;
        }
    }
}
