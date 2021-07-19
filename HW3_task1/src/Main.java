import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

    public static void writeDir(StringBuilder sb, String name){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        sb.append(timeStamp).append(" Каталог ").append(name).append(" создан \n");
    }

    public static void writeFile(StringBuilder sb, String name){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        sb.append(timeStamp).append(" Файл ").append(name).append(" создан \n");
    }

    public static void main(String[] args){

        StringBuilder sb = new StringBuilder();

        File srcDir = new File("Games//src");
        File resDir = new File("Games//res");
        File savegamesDir = new File("Games//savegames");
        File tempDir = new File("Games//temp");
        File mainDir = new File("Games//src//main");
        File testDir = new File("Games//src//test");

        if (srcDir.mkdir()) writeDir(sb, srcDir.getName());
        if (resDir.mkdir()) writeDir(sb, resDir.getName());
        if (savegamesDir.mkdir()) writeDir(sb, savegamesDir.getName());
        if (tempDir.mkdir()) writeDir(sb, tempDir.getName());
        if (mainDir.mkdir()) writeDir(sb, mainDir.getName());
        if (testDir.mkdir()) writeDir(sb, testDir.getName());

        File mainFile = new File("Games//src//main//Main.java");
        File utilsFile = new File("Games//src//main//Utils.java");

        try {
            if (mainFile.createNewFile())
                writeFile(sb, mainFile.getName());
            if (utilsFile.createNewFile())
                writeFile(sb, utilsFile.getName());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        File drawablesDir = new File("Games//res//drawables");
        File vectorsDir = new File("Games//res//vectors");
        File iconsDir = new File("Games//res//icons");

        if (drawablesDir.mkdir()) writeDir(sb, drawablesDir.getName());
        if (vectorsDir.mkdir()) writeDir(sb, vectorsDir.getName());
        if (iconsDir.mkdir()) writeDir(sb, iconsDir.getName());

        File tempFile = new File("Games//temp//temp.txt");
        try {
            if (tempFile.createNewFile())
                writeFile(sb, tempFile.getName());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try (FileWriter writer = new FileWriter(tempFile,true)){
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
