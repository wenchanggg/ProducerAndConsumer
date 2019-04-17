import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 单例模式
 * 同义词词典
 */
public class SynDic {
    //同义词词典 synonymy dictionaries
    private static Map<String, HashSet> syn_words_dic;
    // 相关性词典 pertinence  dictionaries
    private static Map<String, HashSet> per_words_dic;
    private static SynDic symDic = null;

    private SynDic() {
        syn_words_dic = new HashMap<String, HashSet>();
        per_words_dic = new HashMap<String, HashSet>();
        LoadDic();
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static SynDic getSymDicExample() {
        if (symDic != null)
            return symDic;
        else return new SynDic();
    }

    /**
     * 加载词典
     */
    private void LoadDic() {
        // 词典路径
        String path = "SynonymSet.txt";
        Set<String> set = new HashSet<String>();
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line;
            //网友推荐更加简洁的写法
            int i = 0;
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                set.add(line);
//                System.out.println("第"+(++i)+"行："+ line);
                String[] items = line.split(" ");
                String index = items[0];
                String representation = items[1]; // 使用每个集的第一个元素作为代表
                HashSet words = new HashSet(); // 词集
                for (int indx = 1; indx < items.length; indx++) {
                    words.add(items[indx]);
                }
                // 构造词典
                if (index.charAt(index.length() - 1) == '=') {// " = "添加同义词
                    syn_words_dic.put(representation, words);
                } else if (index.charAt(index.length() - 1) == '#') { // " # " 添加相关词
                    per_words_dic.put(representation, words);
                }

//                List<String> list =  Arrays.asList(items);
//                System.out.println("第"+(++i)+"行："+ list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 构造构造词典
    }

    /**
     * 词语替换
     *
     * @param str
     */
    public String ReplaceWords(String str) {
        // 相似词
        Set resultSet1 = new HashSet();
        // 相关词
        Set resultSet2 = new HashSet();
        // 结果
        String result = str;
        // 相关词
//        Iterator ite = per_words_dic.entrySet().iterator();
//        while (ite.hasNext()){
//            Map.Entry entry = (Map.Entry) ite.next();
//            Set set = (Set) entry.getValue();
//            if(set.contains(str)){
//                resultSet2.addAll(set);
//                result = (String) entry.getKey();
//                break;
//            }
//        }
        // 同义词
        Iterator iterator = syn_words_dic.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Set set = (Set) entry.getValue();
            if (set.contains(str)) {
                resultSet1.addAll(set);
                result = (String) entry.getKey();  // 返回替代词
                break;
            }
        }
        // 没有可替换则输出自身
        return result;

        // 输出
//        if(!resultSet1.isEmpty()){
//            System.out.println(str);
//            System.out.print("同义词：");
//            Iterator resultIter = resultSet1.iterator();
//            while (resultIter.hasNext()){
//                System.out.print(resultIter.next()+",");
//            }
//        }
//        if (!resultSet2.isEmpty()){
//            System.out.println(str);
//            System.out.print("相关词：");
//            Iterator resultIter = resultSet2.iterator();
//            while (resultIter.hasNext()){
//                System.out.print(resultIter.next()+",");
//            }
//        }

    }

    public static void main(String[] args) {
        SynDic symDic = SynDic.getSymDicExample();
        String str = "人";
        symDic.ReplaceWords(str);
    }
}