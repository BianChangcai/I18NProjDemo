package com.xcmg.i18n.test;

import cn.hutool.core.io.file.FileNameUtil;

import java.io.*;

public class Des {

    public static void main(String[] args) {
        String dir = "D:\\desktop\\bug";
        String filePath = "D:\\desktop\\bug\\徐工4A架构制品模版（参考）.pptx";
        try {
            InputStream inputStream = new FileInputStream(filePath);
            String extName = FileNameUtil.extName(filePath);
            String tempFilePath = filePath.replace("." + extName, "-1.java");
            writeInputStreamToFile(inputStream, tempFilePath);

            try {
                // 创建ProcessBuilder实例，并设置要执行的命令
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "ren", "*.java", "*." + extName);

                // 设置工作目录
                processBuilder.directory(new File(dir));

                // 启动进程并等待其完成
                Process process = processBuilder.start();
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    System.out.println("文件后缀修改成功！");
                } else {
                    System.err.println("文件后缀修改失败，退出代码：" + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeInputStreamToFile(InputStream inputStream, String outputPath) throws IOException {
        // 确保输出路径的目录存在
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // 使用 try-with-resources 确保资源在完成后关闭
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } finally {
            // 关闭输入流
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
