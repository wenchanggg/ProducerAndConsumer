import org.ansj.recognition.impl.StopRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.*;

/**
 * 使用ansj分词器实现中文分词
 */
public class MyTokenizer {
    private static MyTokenizer myTokenizer = null;
    // 停用词表
    private static HashSet<String> stopWords;
    // 词性表
    private static HashSet<String> FilterClass;
    // 分词过滤器
    private static StopRecognition stopRecognition;

    private MyTokenizer() {
        // 初始化
        this.stopWords = (HashSet<String>) InitStopWords();
        this.FilterClass = (HashSet<String>) InitFilterClass();
        this.stopRecognition = MyClassFilter();
    }

    /**
     * 加载停用词表
     *
     * @return
     */
    private Set<String> InitStopWords() {
        String path = "chinese_stopword.txt";
        return new FileIOImpl().readFile(path);
    }

    /**
     * 加载词性表
     *
     * @return
     */
    private Set<String> InitFilterClass() {
        String path = "filter_class.txt";
        return new FileIOImpl().readFile(path);
    }

    /**
     * 构造过滤器
     *
     * @return
     */
    private StopRecognition MyClassFilter() {
        StopRecognition Filter = new StopRecognition();
        // 过滤词性
        Iterator<String> it = FilterClass.iterator();
        while (it.hasNext()) {
            Filter.insertStopNatures(it.next());
        }
        // 过滤停用词
        Iterator<String> word = stopWords.iterator();
        while (word.hasNext()) {
            Filter.insertStopWords(word.next());
        }
        return Filter;
    }

    /**
     * 单例构造
     *
     * @return
     */
    public static MyTokenizer getMyTokenizerExample() {
        if (myTokenizer != null)
            return myTokenizer;
        else
            return new MyTokenizer();
    }

    /**
     * 分词
     *
     * @param str
     * @return
     */
    public List Analysis(String str) {
        ArrayList resultSet = new ArrayList();
        // 词性、停用词过滤器
        StopRecognition filter = MyClassFilter();
        // 分词处理 // 屏蔽词性
        String parse = ToAnalysis.parse(str).recognition(filter).toStringWithOutNature();
        String[] array = parse.split(",");
        for (String s : array) {
            resultSet.add(s);
        }
        return resultSet;
    }
}