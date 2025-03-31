package com.lucky.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class demo {
    /**
     * author：
     * date：2025/3/31
     * description: 统计CSV文件中图片链接的像素大小
     */
    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("请提供CSV文件路径作为参数");
//            return;
//        }
//
//        String csvFilePath = args[0];
        List<String> imageUrls = readImageUrlsFromCSV("");

        for (String imageUrl : imageUrls) {
            try {
                BufferedImage image = ImageIO.read(new URL(imageUrl));
                int width = image.getWidth();
                int height = image.getHeight();
                System.out.println("图片URL: " + imageUrl);
                System.out.println("宽度: " + width + " 像素");
                System.out.println("高度: " + height + " 像素");
                System.out.println("------------------------");
            } catch (IOException e) {
                System.out.println("无法处理图片: " + imageUrl);
                System.out.println("错误信息: " + e.getMessage());
                System.out.println("------------------------");
            }
        }
    }

    private static List<String> readImageUrlsFromCSV(String csvFilePath) {
        List<String> imageUrls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 假设CSV文件中每行包含一个图片URL
                String[] values = line.split(",");
                for (String value : values) {
                    value = value.trim();
                    if (value.startsWith("http") && (value.endsWith(".jpg") ||
                            value.endsWith(".jpeg") || value.endsWith(".png") ||
                            value.endsWith(".gif"))) {
                        imageUrls.add(value);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("读取CSV文件时发生错误: " + e.getMessage());
        }
        return imageUrls;
    }
}
