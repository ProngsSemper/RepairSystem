package com.repairsys.util.textfilter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Prongs
 */
public class SensitiveWordFilter {
    @SuppressWarnings("rawtypes")
    private Map sensitiveWordMap;
    private static int minMatchTYpe = 1;
//	public static int maxMatchType = 2;

    public SensitiveWordFilter(String path) {
        sensitiveWordMap = new SensitiveWordInit(path).initKeyWord();
    }

    /**
     * 判断是否含有敏感词
     *
     * @param txt 输入的文本内容
     */
    public boolean isContainSensitiveWord(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.checkSensitiveWord(txt, i, matchType);
            if (matchFlag > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获得敏感词
     *
     * @param txt
     * @param matchType
     * @return
     */
    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<>();

        for (int i = 0; i < txt.length(); i++) {
            int length = checkSensitiveWord(txt, i, matchType);
            if (length > 0) {
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;
            }
        }

        return sensitiveWordList;
    }

    /**
     * 将敏感词替换成其他字符
     *
     * @param txt
     * @param matchType
     * @param replaceChar
     * @return
     */
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word;
        String replaceString;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 为上面的方法所用，获取替换的字符
     *
     * @param replaceChar
     * @param length
     * @return
     */
    public String getReplaceChars(String replaceChar, int length) {
        StringBuilder resultReplace = new StringBuilder(replaceChar);
        for (int i = 1; i < length; i++) {
            resultReplace.append(replaceChar);
        }

        return resultReplace.toString();
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：
     *
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    @SuppressWarnings({"rawtypes"})
    private int checkSensitiveWord(String txt, int beginIndex, int matchType) {
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            if (nowMap != null) {
                //存在，则判断是否为最后一个
                //找到相应key，匹配标识+1
                matchFlag++;
                if ("1".equals(nowMap.get("isEnd"))) {
                    //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if (SensitiveWordFilter.minMatchTYpe == matchType) {
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        if (matchFlag < 2 && !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }
//以下为用法样例
	public static void main(String[] args) {
//		SensitiveWordFilter filter = new SensitiveWordFilter();
//		System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
//		String string = "<script>";
//		System.out.println("待检测语句字数：" + string.length());
//		long beginTime = System.currentTimeMillis();
//		Set<String> set = filter.getSensitiveWord(string, 1);
//		long endTime = System.currentTimeMillis();
//		boolean b = filter.isContainSensitiveWord(string,1);
//		System.out.println("是否包含敏感词："+b);
//		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
//		System.out.println("总共消耗时间为：" + (endTime - beginTime));
	}
}
