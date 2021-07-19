import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void writeDir(StringBuilder sb, String name){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        sb.append(timeStamp).append(" Каталог ").append(name).append(" создан \n");
    }

    public static void writeFile(StringBuilder sb, String name){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        sb.append(timeStamp).append(" Файл ").append(name).append(" создан \n");
    }

    public static void createDirectory(){
        StringBuilder sb = new StringBuilder();

        File srcDir = new File("Games//src");
        File resDir = new File("Games//res");
        File savegamesDir = new File("Games//savegames");
        File tempDir = new File("Games//temp");
        File mainDir = new File("Games//src//main");
        File testDir = new File("Games//src//test");
        File drawablesDir = new File("Games//res//drawables");
        File vectorsDir = new File("Games//res//vectors");
        File iconsDir = new File("Games//res//icons");
        File mainFile = new File("Games//src//main//Main.java");
        File utilsFile = new File("Games//src//main//Utils.java");
        File tempFile = new File("Games//temp//temp.txt");

        if (srcDir.mkdir()) writeDir(sb, srcDir.getName());
        if (resDir.mkdir()) writeDir(sb, resDir.getName());
        if (savegamesDir.mkdir()) writeDir(sb, savegamesDir.getName());
        if (tempDir.mkdir()) writeDir(sb, tempDir.getName());
        if (mainDir.mkdir()) writeDir(sb, mainDir.getName());
        if (testDir.mkdir()) writeDir(sb, testDir.getName());
        if (drawablesDir.mkdir()) writeDir(sb, drawablesDir.getName());
        if (vectorsDir.mkdir()) writeDir(sb, vectorsDir.getName());
        if (iconsDir.mkdir()) writeDir(sb, iconsDir.getName());

        try (FileWriter writer = new FileWriter(tempFile,true)){
            if (mainFile.createNewFile()) writeFile(sb, mainFile.getName());
            if (utilsFile.createNewFile()) writeFile(sb, utilsFile.getName());
            if (tempFile.createNewFile()) writeFile(sb, tempFile.getName());
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path) {
        final String zipName = "zip.zip";

        File dir = new File(path);
        if (! dir.isDirectory()) return;

        try (ZipOutputStream zipOut = new ZipOutputStream(new
                FileOutputStream(path + "//" + zipName))) {

            for (File item : dir.listFiles()) {

                if (item.isDirectory()) continue;
                if (zipName.equals(item.getName())) continue;

                try (FileInputStream fileInputStream = new FileInputStream(item.getAbsolutePath())) {
                    ZipEntry entry = new ZipEntry(item.getName());
                    zipOut.putNextEntry(entry);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    zipOut.write(buffer);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            zipOut.closeEntry();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        for (File item : dir.listFiles()) {
            if (zipName.equals(item.getName())) continue;
            if (!item.delete()) System.out.println("Не могу удалить "+item.getName());
        }
    }

    public static void saveGames(List<GameProgress> gpList, String path){
        int i = 0;
        for(GameProgress gameProgress: gpList){
            gameProgress.saveGame(path + "//save"+ (i++) +".dat");
        }
    }

    public static void main(String[] args){
        createDirectory();

        List<GameProgress> gpList = Arrays.asList(new GameProgress(100,1,2,45),
                new GameProgress(80,2,3,56),
                new GameProgress(50,6,5,11222)
                );

        saveGames(gpList, "Games//savegames");
        zipFiles("Games//savegames");
    }
}
