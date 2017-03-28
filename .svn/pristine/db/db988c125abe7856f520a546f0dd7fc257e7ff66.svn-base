package com.thinkeract.tka.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ���ɷֱ����ļ�*
 * Created by ymh on 2016/3/3
 */
public class DimensTools {
    /**
     * Դ�ļ�
     */
    static String oldFilePath = "dimens_nodpi_H.xml";
    /**
     * Դ�ļ�
     */
    static String oldFilePathW = "dimens_nodpi_W.xml";
    /**
     * �������ļ�·��
     */
    static String filePathH = "dimens_H.xml";
    /**
     * �������ļ�·��
     */
    static String filePathW = "dimens_W.xml";


    static String filePathSize = "size.xml";
    static final int DESIGN_HEIGHT = 1280;
    static final int DESIGN_WIDTH = 720;
    /**
     * ��С����
     */
    static float changes = 1.5f;

    public static void main(String[] args) {
        float fh = DESIGN_HEIGHT / 2392f;
        float fw = DESIGN_WIDTH / 1440f;
        //����1-1920x1080
        String allPxH = getAllPx();
        DeleteFolder(oldFilePath);
        writeFile(oldFilePath, allPxH);
        String st = convertStreamToString(oldFilePath, fh, fw);
        DeleteFolder(filePathSize);
        writeFile(filePathSize, st);
    }

    /**
     * ��ȡ�ļ� �������ź��ַ���
     */
    public static String convertStreamToString(String filepath, float fh, float fw) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(filepath));
            String line = null;
            System.out.println("q1");
            String endmark = "px</dimen>";
            String smark = "=\"h";
            String startmark = ">";
            while ((line = bf.readLine()) != null) {
                if (line.contains(endmark)) {
                    int end = line.lastIndexOf(endmark);
                    int start = line.indexOf(startmark);
                    String stpx = line.substring(start + 1, end);
                    int px = Integer.parseInt(stpx);
                    int newpx = (int) (Math.round((float) px / (line.contains(smark) ? fh : fw)));
                    if (newpx == 0) newpx = 1;
                    String newline = line.replace(px + "px", newpx + "px");
                    sb.append(newline + "\r\n");
                } else {
                    sb.append(line + "\r\n");
                }
            }
            bf.close();
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * ����·��ɾ��ָ����Ŀ¼���ļ������۴������
     *
     * @param sPath Ҫɾ����Ŀ¼���ļ�
     * @return ɾ���ɹ����� true�����򷵻� false��
     */
    public static boolean DeleteFolder(String sPath) {
        File file = new File(sPath);
        // // �ж�Ŀ¼���ļ��Ƿ����
        if (!file.exists()) { // �����ڷ��� false
            return true;
        } else {
            // �ж��Ƿ�Ϊ�ļ�
            if (file.isFile()) { // Ϊ�ļ�ʱ����ɾ���ļ�����
                return deleteFile(sPath);
            } else { // ΪĿ¼ʱ����ɾ��Ŀ¼����
                // return deleteDirectory(sPath);
            }
        }
        return false;
    }

    /**
     * ��Ϊ���ļ�
     */
    public static void writeFile(String filepath, String st) {
        try {
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(st);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ȫpx�ļ�
     */
    public static String getAllPx() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\r\n");
            sb.append("<resources>" + "\r\n");
            for (int i = 1; i <= DESIGN_WIDTH; i++) {
                System.out.println("i=" + i);
                sb.append("<dimen name=\"w" + i + "\">" + i + "px</dimen>"
                        + "\r\n");
            }
            for (int i = 1; i <= DESIGN_HEIGHT; i++) {
                System.out.println("i=" + i);
                sb.append("<dimen name=\"h" + i + "\">" + i + "px</dimen>"
                        + "\r\n");
            }

            sb.append("</resources>" + "\r\n");
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * ����ȫpx�ļ�
     */
    public static String getAllPxW() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\r\n");
            sb.append("<resources>" + "\r\n");
            for (int i = 1; i <= DESIGN_WIDTH; i++) {
                System.out.println("i=" + i);
                sb.append("<dimen name=\"w" + i + "\">" + i + "px</dimen>"
                        + "\r\n");
            }
            sb.append("</resources>" + "\r\n");
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * ɾ�������ļ�
     *
     * @param sPath ��ɾ���ļ����ļ���
     * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
