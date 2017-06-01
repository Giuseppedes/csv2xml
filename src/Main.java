import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static String FILE = "file.csv";
    public static String OUTPUT_FILE = "output.xml";

    public static String CSV_SPLIT_BY = ";";

    public static void main(String[] args) {

        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {

            line = br.readLine()
                    .replaceAll(" ","")
                    .replaceAll("/","")
                    .replaceAll("'","")
                    .replaceAll(",","");
            // .replaceAll("(?<=\\[)(.*?)(?=\\])","");

            // use first row to set the name of each xml node
            String[] nodeNames = line.split(CSV_SPLIT_BY);

            for (int i=0; i<nodeNames.length; i++) {
                int end = nodeNames[i].indexOf("(");
                if (end==-1){
                    end = nodeNames[i].indexOf("[");
                }
                if (end!=-1){
                    nodeNames[i] = nodeNames[i].substring(0, end);
                }
            }
            PrintWriter writer = new PrintWriter(OUTPUT_FILE, "UTF-8");

            writer.println("<root>");


            while ((line = br.readLine()) != null) {

                String[] row = line.split(CSV_SPLIT_BY);

                writer.println("<row>");


                for (int i=0; i<row.length; i++) {
                    writer.print("<"+nodeNames[i]+">");
                    writer.print(row[i]);
                    writer.println("</"+nodeNames[i]+">");
                }

                writer.println("</row>");

            }

            writer.println("</root>");
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
