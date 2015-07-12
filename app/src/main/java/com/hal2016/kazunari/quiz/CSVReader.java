package com.hal2016.kazunari.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hirosawak on 2015/06/23.
 */
public class CSVReader {
    // file名が入る。
    InputStream inputStream;

    // コンストラクタ
    public CSVReader(InputStream inputStream){
        this.inputStream = inputStream;
    }

    // CSVファイル読み込む。
    public List< String [] > read () {

        // listは文字列配列からなる。
        // 今回読み込むCSVファイルは、項目（行）により長さが異なることを配慮してArrayListにした。
        List<String[]> resultList = new ArrayList<String[]>();

        BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream) );

        try {
            String csvLine;
            while ( (csvLine = reader.readLine()) != null ) {
                String[] row = csvLine.split(",");
                resultList.add( row );
            }
        } catch ( IOException e ) {
            throw new RuntimeException("Error in reading CSV file: " + e );
        } finally {
            // close処理をする。
            try {
                inputStream.close();
            }
            catch ( IOException e ) {
                throw new RuntimeException("Error while closing input stream: " + e );
            }
        }

        return resultList;
    }
}