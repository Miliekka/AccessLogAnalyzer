package org.example;

import com.google.gson.Gson;
//import sun.jvm.hotspot.gc.shared.CollectedHeap;
//import sun.jvm.hotspot.types.CIntegerField;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App 
{
    private static String path = "C:\\Users\\Kamilla\\Pictures\\резюме\\Java проекты\\access.log";
    private static String outputPath = "C:\\Users\\Kamilla\\Pictures\\резюме\\Java проекты\\result.json";
    private static DateTimeFormatter formatter  = DateTimeFormatter.ofPattern( "dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
    private static String dateTimeRegex = ".+\\[([^\\]]{20}\\s\\+[0-9]{4})\\].+";

    public static void main( String[] args ) throws IOException {
        Pattern dateTimePattern = Pattern.compile(dateTimeRegex);
        HashMap<Long,Integer> countPerSecond = new HashMap<>();

        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;
        int requestsCount = 0;


        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line: lines){
            Matcher matcher = dateTimePattern.matcher(line);
            if (!matcher.find()){
                continue;
            }
            String dateTime = matcher.group(1);//хотим получить первое знач в круглых скобках,фрагмент строки
            long time = getTimeStamp(dateTime);

            if (!countPerSecond.containsKey(time)){
                countPerSecond.put(time,0);//текущая секнуда , 0 штук
            }
            countPerSecond.put(time,countPerSecond.get(time)+1);
            minTime = Math.min(time, minTime);
            maxTime = Math.max(time, maxTime);
            requestsCount++;
        }
        int maxRequestsPerSecond = Collections.max(countPerSecond.values());
        double averageRequestsPerSecond  =(double) requestsCount / (maxTime - minTime);

        //1 января 1970 года
        Statistics statistics = new Statistics(maxRequestsPerSecond,averageRequestsPerSecond);
        Gson gson = new Gson();
        String json = gson.toJson(statistics);

        FileWriter writer = new FileWriter(outputPath);
        writer.write(json);
        writer.flush();//сбрасываем чтобы можно было писать порциями данные, а не по символьно
        writer.close();
        //System.out.println(json);//ключ:значение


    }
    public static long getTimeStamp(String dateTime){
        LocalDateTime time = LocalDateTime.parse(dateTime, formatter );
        return time.toEpochSecond(ZoneOffset.UTC);
    }
}
