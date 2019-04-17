import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WordsTest {
    @Test
    public void test() {
        // 分词
        MyTokenizer myTokenizer = MyTokenizer.getMyTokenizerExample();
        // 同义替换器
        SynDic synDic = SynDic.getSymDicExample();
        String path = "test.txt";
        long begin = System.currentTimeMillis();
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line;
            int count = 0;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                count++;
                ArrayList resultList = new ArrayList();
                // 一次读入一行数据
                ArrayList<String> list = (ArrayList) myTokenizer.Analysis(line);
                for (String word : list) {
                    String replace = synDic.ReplaceWords(word);
                    resultList.add(replace);
                }
                System.out.println("原句分词：" + list);
                System.out.println("同义替换后：" + resultList);
                System.out.println("");
            }
            long end = System.currentTimeMillis();
            System.out.println("共 " + count + " 条数据，用时：" + (end - begin) / 1000 + " 秒");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
