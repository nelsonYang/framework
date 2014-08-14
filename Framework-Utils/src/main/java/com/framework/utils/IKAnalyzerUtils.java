package com.framework.utils;


import com.frameworkLog.factory.LogFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 *
 * @author nelson
 */
public class IKAnalyzerUtils {

    private IKAnalyzerUtils() {
    }
    private static final Logger logger = LogFactory.getInstance().getLogger(IKAnalyzerUtils.class);

    public static List<String> analyze(String content) {
        List<String> resultList = null;
        try {
            //创建分词对象
            resultList = new ArrayList<String>(1);
            resultList.add(content);
            IKAnalyzer analyer = new IKAnalyzer(true);
            analyer.setUseSmart(true);
            StringReader reader = new StringReader(content);
            //分词  
            TokenStream tokenStream = analyer.tokenStream("", reader);
            CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
            //遍历分词数据  
            while (tokenStream.incrementToken()) {
                if (!term.toString().isEmpty()) {
                    resultList.add(term.toString());
                }
            }
            reader.close();

        } catch (IOException ex) {
            logger.error("分词出错", ex);
        }
        return resultList;
    }
}
