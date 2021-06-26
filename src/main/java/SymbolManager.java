import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SymbolManager {
    private BufferedReader reader;
    private URLConnection conn;
    private URL url;
    private final WritableWorkbook workbook;
    private final WritableSheet sheet;
    private final String nasdaqList = "ftp://ftp.nasdaqtrader.com/symboldirectory/nasdaqlisted.txt";
    private final String otherListed = "ftp://ftp.nasdaqtrader.com/symboldirectory/otherlisted.txt";
    private int rowNumber;

    public SymbolManager() throws IOException, WriteException {
        rowNumber = 0;
        workbook = Workbook.createWorkbook(new File("output.xls"));
        sheet = workbook.createSheet("Stock Sheet", 0);
        getSymbolList(nasdaqList);
        getSymbolList(otherListed);
        workbook.write();
        workbook.close();

    }

    public void similaritySearch(String title) {
        String mostSimilar = "";
        double largest = 0;
        for (int i = 0; i < rowNumber; i++) {
            Cell cell = sheet.getCell(1, i);
            double sim = StringSimilarity.similarity(title, cell.getContents());
            if (sim > largest) {
                largest = sim;
                mostSimilar = cell.getContents();
            }
        }
        System.out.println("The title was: " + title);
        System.out.println("The most similar string is: " + mostSimilar);
    }

    private void getSymbolList(String source) throws IOException, WriteException {
        url = new URL(source);
        conn = url.openConnection();
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


        String line;
        int row = rowNumber, col = 0, lineNumber = 1;
        while ((line = reader.readLine()) != null) {
            if (lineNumber > 1) {
                String[] symbolAndName = line.split("\\|", 2);
                String[] t = symbolAndName[1].split(",", 2);
                if (t[0].contains("-")) {
                    t[0] = t[0].split("-", 2)[0];
                }
                if (t[0].contains("ETF")) {
                    t[0] = t[0].split("ETF", 2)[0];
                }

                Label label = new Label(0, row, symbolAndName[0]);
                Label label2 = new Label(1, row, t[0]);
                //only want the ones that the average retail trader would be able to make use of
                if (isAlpha(symbolAndName[0]) && (symbolAndName[1].contains("Common Stock") || symbolAndName[1].contains("ETF"))) {
                    row++;
                    sheet.addCell(label);
                    sheet.addCell(label2);
                }

            }
            lineNumber++;
        }
        rowNumber = row;
        reader.close();


    }

    //thanks SO
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
